package Jocabook.controllers;

import Jocabook.view.InteractiveHtml;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controler {


    @GetMapping("/login")
    public String login() {
        return InteractiveHtml.login();
    }

    @GetMapping("/signup")
    public String signup() {
        return InteractiveHtml.signup();
    }


}