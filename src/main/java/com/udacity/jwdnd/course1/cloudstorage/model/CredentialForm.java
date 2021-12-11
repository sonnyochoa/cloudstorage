package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    private String credentialUrl;
    private String credentialUsername;
    private String credentialPassword;
    private Integer userId;

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public void setCredentialUsername(String credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public void setCredentialPassword(String credentialPassword) {
        this.credentialPassword = credentialPassword;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public String getCredentialUsername() {
        return credentialUsername;
    }

    public String getCredentialPassword() {
        return credentialPassword;
    }

    public Integer getUserId() {
        return userId;
    }
}
