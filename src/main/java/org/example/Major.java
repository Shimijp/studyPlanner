package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class Major {
    private String name;
    private int requiredCredits;
    private List<Course> allCourses;


    public Major(String name, int requiredCredits, List<Course> allCourses) {
        this.name = name;
        this.requiredCredits = requiredCredits;
        this.allCourses = allCourses;

    }

    public List<Course> getMandatoryCourseIds() {
        return allCourses.stream()
                .filter(Course::isMandatory)
                .collect(Collectors.toList());
    }
    public List<Course> englishCourses() {
        return allCourses.stream()
                .filter(Course::isEnglish)
                .collect(Collectors.toList());
    }
    public List<Course> getAllCourses() {
        return allCourses;
    }


    public boolean isCourseMandatory(Course course) {

        return course.isMandatory();
    }

    public int getRequiredCredits() {
        return requiredCredits;
    }

    public String getName() {
        return name;
    }
    String getCourseName(Integer id)
    {
        for (Course course : allCourses) {
            if (course.getId() == id) {
                return course.getName();
            }
        }
        return null;
    }

}