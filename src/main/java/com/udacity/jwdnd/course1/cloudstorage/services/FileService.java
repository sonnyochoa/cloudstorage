package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("::: ::: ::: :: :: :==> Create FileService bean.");
    }

    public int addFile(File file) {
        return fileMapper.insert(file);
    }

    public File getFile(Integer fileId) {
        return fileMapper.get(fileId);
    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAll(userId);
    }

    public int deleteFile(File file) {
        return fileMapper.delete(file);
    }
}
