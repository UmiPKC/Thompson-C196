package com.example.thompsonc196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessID;
    private String assessTitle;
    private String assessStart;
    private String assessEnd;
    //private Enumerated type; Objective Assessment or Performance Assessment

    public Assessment(int assessID, String assessTitle, String assessStart, String assessEnd) {
        this.assessID = assessID;
        this.assessTitle = assessTitle;
        this.assessStart = assessStart;
        this.assessEnd = assessEnd;
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

    public String getAssessStart() {
        return assessStart;
    }

    public void setAssessStart(String assessStart) {
        this.assessStart = assessStart;
    }

    public String getAssessEnd() {
        return assessEnd;
    }

    public void setAssessEnd(String assessEnd) {
        this.assessEnd = assessEnd;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessID=" + assessID +
                ", assessTitle='" + assessTitle + '\'' +
                ", assessStart='" + assessStart + '\'' +
                ", assessEnd='" + assessEnd + '\'' +
                '}';
    }
}
