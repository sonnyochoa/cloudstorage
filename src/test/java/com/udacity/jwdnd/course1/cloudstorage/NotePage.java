package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class NotePage {
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "submit-note")
    private WebElement noteSubmitButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "update-button")
    private WebElement noteUpdateButton;

    @FindBy(id = "notes-table-body")
    private List<WebElement> noteList;

    @FindBy(className = "table-note-title")
    private List<WebElement> noteTitles;

    @FindBy(className = "table-note-description")
    private List<WebElement> noteDescriptions;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "confirm-delete-button")
    private WebElement confirmDeleteButton;

    public NotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void notes() {
        this.notesTabButton.click();
    }

    public void addNewNote(String title, String description) throws InterruptedException {
        this.addNoteButton.click();
        Thread.sleep(1000);
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.sendKeys(description);
        this.noteSubmitButton.click();
    }

    public void editNote(String title, String description) throws InterruptedException {
        this.editNoteButton.click();
        Thread.sleep(1000);
        this.noteTitleField.clear();
        this.noteTitleField.sendKeys(title);
        this.noteDescriptionField.clear();
        this.noteDescriptionField.sendKeys(description);
        this.noteUpdateButton.click();
    }

    public List<String> checkNotes() {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < noteTitles.size(); i++) {
            result.add(noteTitles.get(i).getText());
            result.add(noteDescriptions.get(i).getText());
        }

        return result;
    }

    public void deleteNote() throws InterruptedException {
        this.deleteNoteButton.click();
        Thread.sleep(1000);
        this.confirmDeleteButton.click();
    }
}
