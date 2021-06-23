package com.adpanel.adpanel.controller;

import com.adpanel.adpanel.model.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "index";
    }
    @PostMapping("/new-client")
    public String newClient(@ModelAttribute Client client) {
        System.out.println(client.getId());
        System.out.println(client.getAddress());
        System.out.println(client.getEmail());
        System.out.println(client.getFullName());
        System.out.println(client.getPhone());
        return "redirect:/";
    }
}
