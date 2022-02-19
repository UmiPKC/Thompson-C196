package com.example.thompsonc196.Model;

public class Course {

    private String title;
    private String start;
    private String end;
    //private Emumerator status; in progress/completed/dropped/plan to take
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private int termId; //presumably SQLite will generate term id
    //private ArrayList notes; Use a list to store notes?

    public Course(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
