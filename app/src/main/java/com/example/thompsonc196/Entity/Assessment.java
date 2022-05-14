package com.example.thompsonc196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessID;
    private String assessTitle;
    private Date assessStart;
    private Date assessEnd;
    private String type; //either Objective or Performance
    private int courseID;

    //courseID will be set to 0 by default when creating the assessment; gets changed when creating/editing Courses
    public Assessment(int assessID, String assessTitle, Date assessStart, Date assessEnd, String type, int courseID) {
        this.assessID = assessID;
        this.assessTitle = assessTitle;
        this.assessStart = assessStart;
        this.assessEnd = assessEnd;
        this.type = type;
        this.courseID = courseID;
    }

    public int getAssessID() {
        return assessID;
    }

    public void setAssessID(int assessID) {
        this.assessID = assessID;
    }

    public String getAssessTitle() {
        return assessTitle;
    }

    public void setAssessTitle(String assessTitle) {
        this.assessTitle = assessTitle;
    }

    public Date getAssessStart() {
        return assessStart;
    }

    public void setAssessStart(Date assessStart) {
        this.assessStart = assessStart;
    }

    public Date getAssessEnd() {
        return assessEnd;
    }

    public void setAssessEnd(Date assessEnd) {
        this.assessEnd = assessEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    @Override
    public String toString() {
        return this.assessTitle;
    }

}
