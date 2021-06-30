package com.adpanel.adpanel.controller;

import com.adpanel.adpanel.logic.LinkGenerator;
import com.adpanel.adpanel.model.Client;
import com.adpanel.adpanel.model.Link;
import com.adpanel.adpanel.model.Role;
import com.adpanel.adpanel.model.User;
import com.adpanel.adpanel.service.ClientService;
import com.adpanel.adpanel.service.LinkService;
import com.adpanel.adpanel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.Option;
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
    private LinkService linkService;
    private LinkGenerator lg = new LinkGenerator();
    private Link generatedLink;

    private String selectedLink;
    private String editedClientLink;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('developers:read')")
    public String main(Model model) {
        List<Client> clients = clientService.getClients();
        String generatedLinkView;
        model.addAttribute("clients", clients);
        try {
            generatedLinkView = "https://ps-admin-panel.herokuapp.com/link/" + generatedLink.getLink();
        } catch (Exception e) {
            generatedLinkView = "Здесь сгенерируется ссылка";
        }
        model.addAttribute("generatedLink", generatedLinkView);
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
    @PreAuthorize("hasAuthority('developers:write')")
    public String linkGenerator() {
        generatedLink = new Link(lg.generateLink());
        linkService.addLink(generatedLink);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") long id, Model model) {
        Client client = clientService.getClient(id);
        editedClientLink = client.getLink().getLink();
        model.addAttribute("client", client);
        return "edit-form";
    }
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("client") Client client) {
        Client editedClient = new Client();
        editedClient.setId(client.getId());
        editedClient.setAddress(client.getAddress());
        editedClient.setEmail(client.getEmail());
        editedClient.setFullName(client.getFullName());
        editedClient.setPhone(client.getPhone());
        editedClient.setLink(linkService.getLink(editedClientLink));
        clientService.addClient(editedClient);
        return "redirect:/";
    }
    @PostMapping("/new-client")
    public String newClient(@ModelAttribute("client") Client client) {
        client.setLink(linkService.getLink(selectedLink));
        clientService.addClient(client);
        return "redirect:/"; //тут потрібно повернути статичну сторінку
    }
    @GetMapping("/new-user")
    @PreAuthorize("hasAuthority('developers:write')")
    public String newUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "form-add-user";
    }
    @PostMapping("/new-user")
    @PreAuthorize("hasAuthority('developers:write')")
    public String newUser(@ModelAttribute("newUser") User user, Model model) {
        Optional<User> userCheck = userService.getUser(user.getName());
        if(userCheck.isPresent()) {
            model.addAttribute("errorMessage", "User already exist!");
            return "error-page";
        } else {
            user.setRole(Role.USER);
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
