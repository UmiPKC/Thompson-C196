package com.example.thompsonc196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="instructors")
public class Instructor {

    @PrimaryKey(autoGenerate = true)
    private int instructorID;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;

    public Instructor(int instructorID, String instructorName, String instructorPhone, String instructorEmail) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    @Override
    public String toString() {
        return this.instructorName;
    }
}
