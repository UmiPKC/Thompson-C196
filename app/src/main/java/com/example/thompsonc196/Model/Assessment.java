package com.example.thompsonc196.Model;

public class Assessment {

    private String title;
    private String start;
    private String end;
    //private Enumerated type; Objective Assessment or Performance Assessment

    public Assessment(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
