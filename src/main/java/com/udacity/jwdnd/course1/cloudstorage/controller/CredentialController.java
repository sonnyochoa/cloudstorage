package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String getCredentials(Model model, Authentication authentication) {
        System.out.println("........................................................................................");
        System.out.println("..........                            GET CREDS                               ..........");
        System.out.println("........................................................................................");

        User user = userService.getUser(authentication.getName());
        model.addAttribute("credentials", credentialService.getAllCredentials(user.getUserId()));

        return "fragments/credentials";
    }

    @PostMapping
    public String createCredential(CredentialForm credentialForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        System.out.println("........................................................................................");
        System.out.println("..........                            CREATE CRED                             ..........");
        System.out.println("........................................................................................");
//        this.ifError = null;
//        this.ifSuccess = null;
//        this.errorMessage = null;
//        this.successMessage = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credentialForm.setUserId(userId);

        int rowsAdded = credentialService.addCredential(credentialForm);

        return "redirect:/home";
    }

    @PutMapping
    public String updateCredential(@ModelAttribute("credential")Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);

        System.out.println("........................................................................................");
        System.out.println("..........        Credential ID: " + credential.getCredentialId());
        System.out.println("..........        Credential URL: " + credential.getUrl());
        System.out.println("..........        Credential Username: " + credential.getUsername());
        System.out.println("..........        Credential Password: " + credential.getPassword());
        System.out.println("........................................................................................");

        int rowsUpdated = credentialService.updateCredential(credential);

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteCredential(@ModelAttribute("credential")Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        int rowsDeleted = this.credentialService.deleteCredential(credential);

        return "redirect:/home";
    }
}
