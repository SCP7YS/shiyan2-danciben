package com.example.vocabularynotebook;

public class MainMenuItem {
    private int imageID;
    private String content;

    MainMenuItem(int id, String con){
        imageID = id;
        content = con;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
