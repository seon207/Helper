package com.example.login;

public class WriteInfo {
    private String title;
    private String content;
    private String username;
    private String board;
    private String uid;

    public WriteInfo(String title, String content, String username,String board, String uid) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.board = board;
        this.uid = uid;
    }

    public WriteInfo(){
    }
    public String getBoard() {
        return board;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
