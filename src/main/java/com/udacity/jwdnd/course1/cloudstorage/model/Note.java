package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public Integer getNoteId() {
        return this.noteId;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDescription() {
        return this.noteDescription;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
