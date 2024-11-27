package com.kh.miniproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/navigate")
public class LoginTestController {
    @PostMapping("/home")
    public Map<String, String> loginNavigation () {
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/");
        return response;
    }
}
