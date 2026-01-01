package org.example;
import java.util.ArrayList;
import java.util.List;

public class Plan {
    private Major major;
    private List<Semester> semesters;
    private int currentCredits;
    private boolean needEnglishCourses;


    public Plan(Major major, boolean needEnglishCourses) {
        this.major = major;
        this.semesters = new ArrayList<>();
        this.currentCredits = 0;
        this.needEnglishCourses = needEnglishCourses;
    }

    public void addSemester(Semester semester) {
        semesters.add(semester);
        recalculate();
    }


    public void recalculate() {
        this.currentCredits = 0;
        for (Semester s : semesters) {
            this.currentCredits += s.getNumCredits(); // משתמש בפונקציה שכבר כתבת ב-Semester
        }
    }

    public boolean gotAllMandatoryCourses() {
        for (Course course: major.getMandatoryCourseIds()) {
            boolean found = false;
            for (Semester s : semesters) {
                for (Course c : s.getCourses()) {
                    if (c.getId() == course.getId()) {
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
    public boolean areAllSemestersLegal() {
        for (Semester s : semesters) {
            if (!s.isLegalSemester()) {
                return false;
            }
        }
        return true;
    }
    public boolean isCompleteEnglishRequirement() {
        if (!needEnglishCourses) {
            return true;
        }
       int englishCount = 0;
        for (Semester s : semesters) {
            for (Course c : s.getCourses()) {
                if (c.isEnglish()) {
                    englishCount++;
                }
            }
        }
        return englishCount >= 2; // Assuming the requirement is at least 2 English courses
    }


    public boolean isLegalPlan() {
        boolean enoughCredits = isGraduationReady();
        boolean allSemestersLegal = areAllSemestersLegal();
        boolean gotAllMandatoryCourses = gotAllMandatoryCourses();
        boolean englishRequirementMet = isCompleteEnglishRequirement();
        return enoughCredits && allSemestersLegal && gotAllMandatoryCourses && englishRequirementMet;

    }



    public boolean isGraduationReady() {
        return currentCredits >= major.getRequiredCredits();
    }

    // בדיקה: כמה נקודות חסרות לי?
    public int getMissingCredits() {
        return Math.max(0, major.getRequiredCredits() - currentCredits);
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    // Getters / Setters
    public Major getMajor() { return major; }
    public List<Semester> getSemesters() { return semesters; }
}