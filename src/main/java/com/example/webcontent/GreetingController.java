package com.example.webcontent;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required = false, defaultValue = "World") String name, Model model
    ){
        model.addAttribute("name", name);
        return "greeting";
    }


    @GetMapping()
    public String home(
            @RequestParam(name="text") String text,
            Model model
    ){
        model.addAttribute("text", text);
        return "home";
    }


}
