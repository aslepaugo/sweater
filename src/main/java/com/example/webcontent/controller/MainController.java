package com.example.webcontent.controller;


import com.example.webcontent.domain.Message;
import com.example.webcontent.domain.User;
import com.example.webcontent.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            @Valid  Message message,
            BindingResult bindingResult, //!!! Before Model!!!!
            Model model,
            @RequestParam("file")MultipartFile file
    ) throws IOException, NullPointerException {


        message.setAuthor(user);

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);

        } else {

            if (file != null && !file.getOriginalFilename().isEmpty()) {

                File uploadFolder = new File(uploadPath);

                if (!uploadFolder.exists()) {
                    if (!uploadFolder.mkdir()) {
                        throw new IOException();
                    }
                }

                String uidFile = UUID.randomUUID().toString();
                String resultFileName = uidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFileName));
                message.setFilename(resultFileName);
            }

            model.addAttribute("message", null);
            messagesRepo.save(message);
        }
        Iterable<Message> messages = messagesRepo.findAll();
        model.addAttribute("messages", messages);

        return "home";
    }



}
