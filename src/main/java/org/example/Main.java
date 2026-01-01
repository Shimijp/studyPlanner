package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. יצירת קורסים לדוגמה (ידנית, כי עוד לא חיברנו את ה-JSON)
        Course javaCourse = new Course(20441, "Java", 6, new ArrayList<>(), Arrays.asList("A", "B"), false, false, false, false, false);
        Course dsCourse = new Course(20407, "Data Structures", 6, Arrays.asList(20441), Arrays.asList("A"), false, false, false, false, false);
        Course englishCourse = new Course(10001, "English A", 0, new ArrayList<>(), Arrays.asList("A", "B", "C"), false, false, false, false, true);

        // רשימת כל הקורסים הקיימים
        List<Course> allCourses = Arrays.asList(javaCourse, dsCourse, englishCourse);

        // 2. הגדרת התואר (Major)
        Major csMajor = new Major("Computer Science", 108, allCourses);

        // 3. יצירת תוכנית לסטודנט
        Plan myPlan = new Plan(csMajor, true);
         // נניח שהסטודנט צריך אנגלית

        // 4. יצירת סמסטר ראשון (סמסטר א' 2026)
        // שים לב: אנחנו מעבירים רק את הקורס Java
        List<Course> sem1Courses = new ArrayList<>();
        sem1Courses.add(javaCourse);
        sem1Courses.add(englishCourse); // מוסיפים גם אנגלית

        Semester sem1 = new Semester("A", "2026", sem1Courses, 0); // ה-0 מתעדכן אוטומטית בבנאי

        // הוספה לתוכנית
        myPlan.addSemester(sem1);

        // 5. בדיקות והדפסות
        System.out.println("Credits: " + myPlan.getMissingCredits());
        System.out.println("Is Semester 1 Legal? " + sem1.isLegalSemester());
        System.out.println("Is Plan Legal? " + myPlan.isLegalPlan()); // אמור להיות False כי חסר מבני נתונים ועוד אנגלית

        // בוא נוסיף סמסטר ב' עם מבני נתונים (אבל רגע! הוא מוצע רק בסמסטר A לפי ההגדרה שלי למעלה)
        List<Course> sem2Courses = new ArrayList<>();
        sem2Courses.add(dsCourse);
        Semester sem2 = new Semester("B", "2026", sem2Courses, 0);

        System.out.println("Is Semester 2 Legal? " + sem2.isLegalSemester()); // אמור להדפיס False כי מבני נתונים לא מוצע ב-B
    }
}