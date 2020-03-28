package com.example.webcontent.controller;


import com.example.webcontent.domain.Message;
import com.example.webcontent.domain.User;
import com.example.webcontent.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messagesRepo;

    @GetMapping("/")
    public String greeting(
            Map<String, Object> model
    ){

        return "greeting";
    }


    @GetMapping("/home")
    public String home(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ){
        Iterable<Message> messages = messagesRepo.findAll();

        if (filter == null || filter.isEmpty()){
            messages = messagesRepo.findAll();
        } else{
            messages = messagesRepo.findByTag(filter);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "home";
    }

    @PostMapping("/home")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model){


        Message message = new Message(text, tag, user);
        messagesRepo.save(message);

        Iterable<Message> messages = messagesRepo.findAll();
        model.put("messages", messages);

        return "home";
    }



}
