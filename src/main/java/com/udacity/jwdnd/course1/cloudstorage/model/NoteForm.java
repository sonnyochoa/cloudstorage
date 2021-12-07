package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
