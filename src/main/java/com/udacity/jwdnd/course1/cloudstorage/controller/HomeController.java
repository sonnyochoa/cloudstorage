package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
//    private final FileService fileService;
    private final NoteService noteService;
//    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService, /*FileService fileService,*/ NoteService noteService, /*CredentialService credentialService,*/ EncryptionService encryptionService) {
        this.userService = userService;
//        this.fileService = fileService;
        this.noteService = noteService;
//        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
//        model.addAttribute("file", fileService.getFilesFromUser(user.getUserId()));
        model.addAttribute("notes", noteService.getAllNotes(user.getUserId()));
//        model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}
