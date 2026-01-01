package org.example;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Course {
    private int id;
    private String name;
    private int credits;
    private List<Integer> prerequisites;
    private List<String> semesters;

    // --- הוספת ה-Annotations לכל הבוליאנים ---

    @JsonProperty("isMandatory")
    private boolean isMandatory;

    @JsonProperty("isAdvanced")
    private boolean isAdvanced;

    @JsonProperty("isWorkshop")
    private boolean isWorkshop;

    @JsonProperty("isSemniarion")
    private boolean isSemniarion;

    @JsonProperty("isEnglish")
    private boolean isEnglish;
    public Course() {}
    public Course(int id, String name, int credits, List<Integer> prerequisites,
                  List<String> semesters, boolean isMandatory,
                  boolean isAdvanced, boolean isWorkshop, boolean isSemniarion, boolean isEnglish) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.prerequisites = prerequisites;
        this.semesters = semesters;
        this.isMandatory = isMandatory;
        this.isAdvanced = isAdvanced;
        this.isWorkshop = isWorkshop;
        this.isSemniarion = isSemniarion;
        this.isEnglish = isEnglish;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Integer> getPrerequisites() {
        return prerequisites;
    }


    public void setPrerequisites(List<Integer> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public List<String> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<String> semesters) {
        this.semesters = semesters;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public void setAdvanced(boolean advanced) {
        isAdvanced = advanced;
    }

    public boolean isWorkshop() {
        return isWorkshop;
    }

    public void setWorkshop(boolean workshop) {
        isWorkshop = workshop;
    }

    public boolean isSemniarion() {
        return isSemniarion;
    }

    public void setSemniarion(boolean semniarion) {
        isSemniarion = semniarion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course courses = (Course) o;
        return id == courses.id && credits == courses.credits && isMandatory == courses.isMandatory && isAdvanced == courses.isAdvanced && isWorkshop == courses.isWorkshop && isSemniarion == courses.isSemniarion && Objects.equals(name, courses.name) && Objects.equals(prerequisites, courses.prerequisites) && Objects.equals(semesters, courses.semesters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, credits, prerequisites, semesters, isMandatory, isAdvanced, isWorkshop, isSemniarion);
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public void setEnglish(boolean english) {
        isEnglish = english;
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", prerequisites=" + prerequisites +
                ", semestersOffered=" + semesters +
                ", isElective=" + isMandatory +
                ", isAdvanced=" + isAdvanced +
                ", isLabCourse=" + isWorkshop +
                ", isSeminarCourse=" + isSemniarion +
                ", isEnglish=" + isEnglish +
                '}';

    }
    public List<String> getSemesterFullName(){

        return semesters.stream().map(code -> {
            switch (code) {
                case "A":
                    return "סתיו";
                case "B":
                    return "אביב";
                case "C":
                    return "קיץ";
                default:
                    return code; // במקרה של קוד לא מוכר, מחזיר את הקוד כפי שהוא
            }
        }).collect(Collectors.toList());
    }


}
