package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {
    private final UserService userService;

    public FileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getFiles(Model model, Authentication authentication) {
        return "fragments/files";
    }
}
