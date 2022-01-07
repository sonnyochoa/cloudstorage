package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CredentialPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTabButton;

    @FindBy(id="add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id="credential-url")
    private WebElement credentialUrlField;

    @FindBy(id="credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id="credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id="submit-credential")
    private WebElement credentialSubmitButton;

    @FindBy(className = "table-credential-url")
    private List<WebElement> credentialUrls;

    @FindBy(className = "table-credential-username")
    private List<WebElement> credentialUsernames;

    @FindBy(className = "table-credential-password")
    private List<WebElement> credentialPasswords;

    @FindBy(id="edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(id="credential-update-button")
    private WebElement credentialUpdateButton;

    @FindBy(id="delete-credential-button")
    private WebElement deleteCredentialButton;

    @FindBy(id="confirm-credential-delete-button")
    private WebElement confirmDeleteButton;

    public CredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void credentials() {
        this.credentialsTabButton.click();
    }

    public void addNewCredential(String url, String username, String password) throws InterruptedException {
        this.addCredentialButton.click();
        Thread.sleep(1000);
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);
        this.credentialSubmitButton.click();
    }

    public void editCredential(String url, String username, String password) throws InterruptedException {
        this.editCredentialButton.click();
        Thread.sleep(1000);
        this.credentialUrlField.clear();
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.clear();
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.clear();
        this.credentialPasswordField.sendKeys(password);
        this.credentialUpdateButton.click();
    }

    public List<String> checkCredentials() {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < credentialUrls.size(); i++) {
            result.add(credentialUrls.get(i).getText());
            result.add(credentialUsernames.get(i).getText());
            result.add(credentialPasswords.get(i).getText());
        }

        return result;
    }

    public void deleteCredential() throws InterruptedException {
        this.deleteCredentialButton.click();
        Thread.sleep(1000);
        this.confirmDeleteButton.click();
    }
}
