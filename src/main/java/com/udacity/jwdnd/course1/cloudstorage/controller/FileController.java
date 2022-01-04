package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/files")
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getFiles(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));
        return "fragments/files";
    }

    @PostMapping
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {

        if(fileUpload.isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Please select a file to upload.");
            return "redirect:/home";
        }

        File file = new File();

        // User ID
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        // Set FILE values
        file.setFileName(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(String.valueOf(fileUpload.getSize()));
        file.setUserId(userId);
        file.setFileData(fileUpload.getBytes());

        fileService.addFile(file);
        model.addAttribute("success", true);
        model.addAttribute("message", "File has been uploaded.");

        return "redirect:/home";
    }

    @GetMapping("/file/{id}")
    public StreamingResponseBody getFile(HttpServletResponse response, @PathVariable Integer id) throws IOException {
        File file = fileService.getFile(id);
        response.setContentType(file.getContentType());

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + file.getFileName() + "\"");

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[10000];
            InputStream inputStream = new ByteArrayInputStream(file.getFileData());

            while ( (bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };
    }

    @DeleteMapping
    public String deleteFile(@ModelAttribute("file") File file, Authentication authentication, RedirectAttributes redirectAttributes) {
        int rowsDeleted = this.fileService.deleteFile(file);

        return "redirect:/home";
    }
}
