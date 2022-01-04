package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    private String ifError;
    private String ifSuccess;
    private String errorMessage;
    private String successMessage;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getCredentials(Model model, Authentication authentication) {

        User user = userService.getUser(authentication.getName());
        model.addAttribute("credentials", credentialService.getAllCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);

        return "fragments/credentials";
    }

    @PostMapping
    public String createCredential(CredentialForm credentialForm, Authentication authentication, RedirectAttributes redirectAttributes) {

        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;

        Credential credential = new Credential();

        // User ID
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        // Encrypted Password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        // Set Credential Values
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setUserId(userId);

        int rowsAdded = credentialService.addCredential(credential);

        if (rowsAdded < 0) {
            this.errorMessage = "There was an error adding your credentials. Please try again.";
        }

        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "Credentials have been added.");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", this.errorMessage);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateCredential(@ModelAttribute("credential")Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;

        // User ID
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        String key = null;

        Credential userCredential = credentialService.getCredential(userId);
        credential.setKey(userCredential.getKey());

        // Encrypted Password
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());

        // Update Credential Values
        credential.setUserId(userId);
        credential.setPassword(encryptedPassword);

        int rowsUpdated = credentialService.updateCredential(credential);
        if (rowsUpdated < 0) {
            this.errorMessage = "There was an error updating your credentials. Please try again.";
        }

        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "Credentials have been updated.");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", this.errorMessage);
        }

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteCredential(@ModelAttribute("credential")Credential credential, RedirectAttributes redirectAttributes) {
        int rowsDeleted = this.credentialService.deleteCredential(credential);

        return "redirect:/home";
    }
}
