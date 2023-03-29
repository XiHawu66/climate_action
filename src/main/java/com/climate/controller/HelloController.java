package com.climate.controller;

import com.climate.model.Hello;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
    @GetMapping("/hello")
    public Hello hello() {
        Hello hello = new Hello();
        hello.setGreeting("hello");
        return hello;
    }
}
