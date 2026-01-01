package org.example;


import java.util.List;
import java.util.Objects;

public class Semester {
    private String semesterName;
    private String semesterYear;
    private  String semesterCode;
    private List<Course> courses;
    private boolean isLegalSemester;
    private int numCredits;

    public Semester(String semesterName, String semesterYear,
                    List<Course> courses, int numCredits) {
        this.semesterName = semesterName;
        this.semesterYear = semesterYear;
        this.semesterCode = semesterName + semesterYear;
        this.courses = courses;
        this.numCredits = numCredits;
        this.isLegalSemester = validateSemester();
        this.numCredits = calculateTotalCredits();
    }
    public boolean validateSemester() {
        if (this.courses == null) return true;
        for(Course course : courses) {

            if(!course.getSemesters().contains(this.semesterName)) {
                return false;
            }
        }
        return true;
    }
    public int calculateTotalCredits() {
        if (this.courses == null) return 0;
        int totalCredits = 0;
        for(Course course : courses) {
            totalCredits += course.getCredits();
        }
        return totalCredits;
    }
    public int getNumCredits() {
        return numCredits;
    }
    public void setSemesterCode(String semesterYear, String semesterName) {
        this.semesterCode = semesterName + semesterYear;
    }

    // Added getters and setters
    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
        // keep semesterCode in sync when name changes
        if (this.semesterYear != null) {
            this.semesterCode = this.semesterName + this.semesterYear;
        }
        // Re-evaluate dependent fields
        if (this.courses != null) {
            this.isLegalSemester = validateSemester();
            this.numCredits = calculateTotalCredits();
        }
    }

    public String getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(String semesterYear) {
        this.semesterYear = semesterYear;
        // keep semesterCode in sync when year changes
        if (this.semesterName != null) {
            this.semesterCode = this.semesterName + this.semesterYear;
        }
        // Re-evaluate dependent fields
        if (this.courses != null) {
            this.isLegalSemester = validateSemester();
            this.numCredits = calculateTotalCredits();
        }
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
        if (this.courses != null) {
            this.isLegalSemester = validateSemester();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        // Re-evaluate dependent fields
        if (this.courses != null) {
            this.isLegalSemester = validateSemester();
            this.numCredits = calculateTotalCredits();
        } else {
            this.isLegalSemester = false;
            this.numCredits = 0;
        }
    }

    public boolean isLegalSemester() {
        return isLegalSemester;
    }

    public void setLegalSemester(boolean isLegalSemester) {
        this.isLegalSemester = isLegalSemester;
    }

    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semester)) return false;
        Semester semester = (Semester) o;
        return isLegalSemester == semester.isLegalSemester &&
                numCredits == semester.numCredits &&
                Objects.equals(semesterName, semester.semesterName) &&
                Objects.equals(semesterYear, semester.semesterYear) &&
                Objects.equals(semesterCode, semester.semesterCode) &&
                Objects.equals(courses, semester.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterName, semesterYear, semesterCode, courses, isLegalSemester, numCredits);
    }
    public void addCourse(Course c) {
        if (this.courses == null) {
            this.courses = new java.util.ArrayList<>();
        }
        this.courses.add(c);
        // עדכון אוטומטי של הנקודות אחרי הוספה
        this.numCredits = calculateTotalCredits();
    }



}
