package com.adpanel.adpanel.controller;

import com.adpanel.adpanel.model.Client;
import com.adpanel.adpanel.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String main(Model model) {
        List<Client> clients = clientService.getClients();
        model.addAttribute("clients", clients);
        return "index";
    }
    @GetMapping("/link/{form-link}")
    public String link(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "form";
    }
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") long id, Model model) {
        Client client = clientService.getClient(id);
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
        clientService.addClient(editedClient);
        return "redirect:/";
    }
    @PostMapping("/new-client")
    public String newClient(@ModelAttribute("client") Client client) {
        clientService.addClient(client);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") long id) {
        clientService.deleteClient(id);
        return "redirect:/";
    }
}
