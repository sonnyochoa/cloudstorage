package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("::: ::: ::: :: :: :==> Create CredentialService bean.");
    }

    public int addCredential(Credential credential) {
        return credentialMapper.insert(credential);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.get(credentialId);
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return credentialMapper.getAll(userId);
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.update(credential);
    }

    public int deleteCredential(Credential credential) {
        return credentialMapper.delete(credential);
    }

}
