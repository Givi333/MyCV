package com.example.mycv.db;

public class Education {
    private String educationLevel;
    private String university;
    private String faculty;

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getEducationYears() {
        return educationYears;
    }

    public void setEducationYears(String educationYears) {
        this.educationYears = educationYears;
    }

    private String educationYears;
}
