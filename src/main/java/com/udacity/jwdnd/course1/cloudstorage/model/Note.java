package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    /*
        noteid INT PRIMARY KEY auto_increment,
        notetitle VARCHAR(20),
        notedescription VARCHAR (1000),
        userid INT,
        foreign key (userid) references USERS(userid)
     */

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

//    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
//        this.noteId = noteId;
//        this.noteTitle = noteTitle;
//        this. noteDescription = noteDescription;
//        this.userId = userId;
//    }

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
