package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;

import javax.annotation.PostConstruct;
import java.util.List;

public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("::: ::: ::: :: :: :==> Create CredentialService bean.");
    }

    public int addCredential(CredentialForm credentialForm) {
        Credential newCredential = new Credential();
        newCredential.setUrl(credentialForm.getUrl());
        newCredential.setUsername(credentialForm.getUsername());
        newCredential.setPassword(credentialForm.getPassword());
        newCredential.setUserId(credentialForm.getUserId());
        return credentialMapper.insert(newCredential);
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return credentialMapper.getAllCredentials(userId);
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.update(credential);
    }

    public int deleteCredential(Credential credential) {
        return credentialMapper.delete(credential);
    }

}
