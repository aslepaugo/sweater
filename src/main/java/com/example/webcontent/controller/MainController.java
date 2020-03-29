package com.example.webcontent.controller;


import com.example.webcontent.domain.Message;
import com.example.webcontent.domain.User;
import com.example.webcontent.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messagesRepo;

    @Value("${upload.path}")
    private String uploadPath;

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
        Iterable<Message> messages;

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
            Map<String, Object> model,
            @RequestParam("file")MultipartFile file
    ) throws IOException {


        Message message = new Message(text, tag, user);

        if (file != null){

            File uploadFolder = new File(uploadPath);

            if (!uploadFolder.exists()){
                if (!uploadFolder.mkdir()){
                    throw new IOException();
                }
            }

            String uidFile = UUID.randomUUID().toString();
            String resultFileName = uidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFilename(resultFileName);
        }

        messagesRepo.save(message);

        Iterable<Message> messages = messagesRepo.findAll();
        model.put("messages", messages);

        return "home";
    }



}
