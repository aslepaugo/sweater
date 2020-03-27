package com.example.webcontent;


import com.example.webcontent.domain.Message;
import com.example.webcontent.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepo messagesRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required = false, defaultValue = "World") String name, Map<String, Object> model
    ){
        model.put("name", name);
        return "greeting";
    }


    @GetMapping()
    public String home(
            Map<String, Object> model
    ){
        Iterable<Message> messages = messagesRepo.findAll();
        model.put("messages", messages);
        return "home";
    }

    @PostMapping
    public String add(
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model){


        Message message = new Message(text, tag);
        messagesRepo.save(message);

        Iterable<Message> messages = messagesRepo.findAll();
        model.put("messages", messages);

        return "home";
    }

    @PostMapping("filter")
    public String filter(
        @RequestParam String filter,
        Map<String, Object> model
    ){

        Iterable<Message> messages;

        if (filter == null || filter.isEmpty()){
            messages = messagesRepo.findAll();
        } else{
            messages = messagesRepo.findByTag(filter);
        }

        model.put("messages", messages);
        return "home";
    }


}
