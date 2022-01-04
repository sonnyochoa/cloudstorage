package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    private String ifError;
    private String ifSuccess;
    private String errorMessage;
    private String successMessage;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getNotes(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("notes", noteService.getAllNotes(user.getUserId()));

        return "fragments/notes";
    }

    @PostMapping
    public String createNote(NoteForm noteForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        noteForm.setUserId(userId);

        int rowsAdded = noteService.addNote(noteForm);
        if (rowsAdded < 0) {
            this.errorMessage = "There was an error adding your note. Please try again.";
        }

        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "Note has been added.");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", this.errorMessage);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateNote(@ModelAttribute("note") Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        note.setUserId(userId);

        int rowsUpdated = this.noteService.updateNote(note);

        if (rowsUpdated < 0) {
            this.errorMessage = "There was an error updating your note. Please try again.";
        }

        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "Note has been updated.");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", this.errorMessage);
        }

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteNote(@ModelAttribute("note") Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;

        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        note.setUserId(userId);

        int rowsDeleted = this.noteService.deleteNote(note);

        if (rowsDeleted < 0) {
            this.errorMessage = "There was an error deleting your note. Please try again.";
        }

        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess", true);
            redirectAttributes.addFlashAttribute("successMessage", "Note has been deleted.");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", this.errorMessage);
        }
        return "redirect:/home";
    }
}
