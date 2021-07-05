package com.adpanel.adpanel.controller;

import com.adpanel.adpanel.logic.LinkGenerator;
import com.adpanel.adpanel.model.*;
import com.adpanel.adpanel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileDBService fileDBService;
    @Autowired
    private LinkService linkService;
    private LinkGenerator lg = new LinkGenerator();

    private Link generatedLink;
    private String selectedLink;
    private String editedClientLink;

    private String tempFileLink;
    private FileDB tempfileDB;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('developers:read')")
    public String main(Model model, Principal principal) {
        List<Client> clients = clientService.getClients();
        Optional<User> optionalUser = userService.getUser(principal.getName());
        String generatedLinkView;

        model.addAttribute("clients", clients);
        try {
            generatedLinkView = "https://ps-admin-panel.herokuapp.com/link/" + generatedLink.getLink();
        } catch (Exception e) {
            generatedLinkView = "Здесь сгенерируется ссылка";
        }
        model.addAttribute("generatedLink", generatedLinkView);
        optionalUser.ifPresent(user -> model.addAttribute("currentUser", user));
        return "index";
    }
    @GetMapping("/link/{form-link}")
    public String link(@PathVariable("form-link") String formLink, Model model) {
        Link link;
        try {
            link = linkService.getLink(formLink);
        } catch (Exception e) {
            link = null;
        }
        if(link == null) {
            model.addAttribute("errorMessage", "This link is invalid!");
            return "error-page";
        } else {
            selectedLink = formLink;
            model.addAttribute("client", new Client());
            return "form";
        }
    }
    @PostMapping("/link/generate")
    @PreAuthorize("hasAuthority('developers:generate:link')")
    public String linkGenerator() {
        generatedLink = new Link(lg.generateLink());
        linkService.addLink(generatedLink);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") long id, Model model) {
        Client client = clientService.getClient(id);
        editedClientLink = client.getLink().getLink();
        tempFileLink = client.getFileLink();
        tempfileDB = client.getFileDB();
        model.addAttribute("client", client);
        return "edit-form";
    }
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("client") Client client, @RequestParam String status) {
        Client editedClient = new Client();
        editedClient.setId(client.getId());
        editedClient.setAddress(client.getAddress());
        editedClient.setEmail(client.getEmail());
        editedClient.setFullName(client.getFullName());
        editedClient.setPhone(client.getPhone());
        editedClient.setLink(linkService.getLink(editedClientLink));
        editedClient.setFileLink(tempFileLink);
        editedClient.setFileDB(tempfileDB);
        switch (status) {
            case "ACCEPTED":
                editedClient.setStatus(Status.ACCEPTED);
                break;
            case "REJECTED":
                editedClient.setStatus(Status.REJECTED);
                break;
            case "PENDING":
                editedClient.setStatus(Status.PENDING);
                break;
        }
        clientService.addClient(editedClient);
        return "redirect:/";
    }
    @PostMapping("/new-client")
    public String newClient(@RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute("client") Client client) throws IOException {
        client.setLink(linkService.getLink(selectedLink));
        client.setStatus(Status.PENDING);

        System.out.println(file.isEmpty());
        System.out.println(file == null);

        if(!(file.isEmpty())) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB fileDB = new FileDB(fileName, file.getBytes());
            client.setFileDB(fileDB);

            fileDBService.store(fileDB);

            client.setFileLink(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(fileDBService.getLastFile().getId().toString())
                    .toUriString());
        }

        clientService.addClient(client);

        return "redirect:/thank-page.html"; //тут потрібно повернути статичну сторінку
    }
    @GetMapping("/new-user")
    @PreAuthorize("hasAuthority('developers:create:user')")
    public String newUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "form-add-user";
    }
    @PostMapping("/new-user")
    @PreAuthorize("hasAuthority('developers:create:user')")
    public String newUser(@RequestParam(required = false) Optional<String> isSubAdmin, @ModelAttribute("newUser") User user, Model model) {
        Optional<User> userCheck = userService.getUser(user.getName());
        if(userCheck.isPresent()) {
            model.addAttribute("errorMessage", "User already exist!");
            return "error-page";
        } else {
            if(isSubAdmin.isPresent()) {
                user.setRole(Role.SUB_ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            String encodePass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePass);
            userService.addUser(user);
        }
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") long id) {
        clientService.deleteClient(id);
        return "redirect:/";
    }
}
