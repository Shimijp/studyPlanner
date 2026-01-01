package org.example;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public class MainController {

    @FXML
    private javafx.scene.control.TextField searchField;
    @FXML
    private VBox coursesListContainer;

    @FXML
    private HBox semestersContainer;

    @FXML
    private Label totalCreditsLabel;

    private DataManager dataManager;
    private Major major;
    private Plan plan;

    @FXML
    public void initialize() {
        dataManager = new DataManager();
        dataManager.loadCourses("courses.json");

        major = new Major("Computer Science", 120, dataManager.getAllCourses());
        plan = new Plan(major, true);
        showAllCourses();
        updateTotalCreditsLabel();



        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        int startYear = 2022;
        String [] semesterNames = {"סתיו", "אביב", "קיץ"};

    }
    private void showAllCourse()
    {

    }
    /**
     * פונקציה זו יוצרת את הייצוג הגרפי של קורס בודד.
     * @param course אובייקט המידע (Data)
     * @return HBox רכיב גרפי המכיל את שם הקורס והנקודות
     */
    private HBox createCourseCard(Course course) {
        // 1. יצירת הקופסה הראשית (הכרטיס)
        HBox card = new HBox();

        // 2. הגדרת כיוון (מימין לשמאל לעברית)
        // API: NodeOrientation.RIGHT_TO_LEFT הופך את סדר הרכיבים
        card.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);


        card.setPadding(new Insets(10)); // רווח פנימי של 10 פיקסלים מכל צד
        card.setSpacing(10);             // רווח של 10 פיקסלים בין הטקסט לנקודות
        card.setAlignment(Pos.CENTER_LEFT); // יישור התוכן בתוך הכרטיס

        // 4. עיצוב הכרטיס (רקע, גבול, פינות מעוגלות)
        card.setStyle("-fx-background-color: white; " +
                "-fx-border-color: #e0e0e0; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 2); " +
                "-fx-cursor: hand;");
        card.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showCourseDetails(course);
            }

        });
        // 5. יצירת התווית של שם הקורס
        Label nameLabel = new Label(course.getName()); // יצירת Label עם הטקסט
        nameLabel.setWrapText(true);                   // API: מאפשר לטקסט לרדת שורה אם הוא ארוך מדי
        nameLabel.setPrefWidth(150);                   // רוחב מועדף לטקסט
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
        // 6. יצירת התווית של הנקודות
        Label creditsLabel = new Label( + course.getCredits() +  " נ\"ז" );
        creditsLabel.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 5; -fx-background-radius: 20; -fx-min-width: 25; -fx-alignment: center;");
        creditsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));


        // 7. הוספת התוויות לתוך הכרטיס
        // API: getChildren() מחזיר את הרשימה של הרכיבים בתוך הקופסה
        // addAll מוסיף כמה רכיבים בבת אחת
        card.getChildren().addAll(nameLabel, creditsLabel);

        return card;
    }
    private void showAllCourses() {
        List<Course> allCourses = major.getAllCourses();
        coursesListContainer.getChildren().clear(); // ניקוי התצוגה הקודמת

        for (Course course : allCourses) {
            HBox courseCard = createCourseCard(course);
            coursesListContainer.getChildren().add(courseCard);
        }
    }
    private void showCourseDetails(Course course)
    {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("פרטי קורס");
        alert.setHeaderText(course.getName());
        String semestersStr = String.join(", ", course.getSemesterFullName());


        StringBuilder content = new StringBuilder();
        content.append("מספר קורס: ").append(course.getId()).append("\n");
        content.append("נקודות זכות: ").append(course.getCredits()).append("\n");
        content.append("סמסטרים מוצעים: ").append(semestersStr).append("\n\n");
        if (course.isMandatory()) content.append("• קורס חובה\n");
        if (course.isAdvanced()) content.append("• מתקדם\n");
        if (course.isEnglish()) content.append("• נלמד באנגלית\n");
        if (course.isSemniarion()) content.append("• סמינריוני\n");

        if (course.getPrerequisites() != null && !course.getPrerequisites().isEmpty()) {
            content.append("\nדרישות קדם: ").append(this.getPrerequisiteNames(course.getPrerequisites()));
        } else {
            content.append("\nאין דרישות קדם.");
        }
        alert.setContentText(content.toString());
        alert.getDialogPane().setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
    public List<String> getPrerequisiteNames(List<Integer> prerequisiteIds) {

        List<String> names = new java.util.ArrayList<>();

        for (Integer id : prerequisiteIds) {
            Course course = dataManager.getCourseById(id);
            if (course != null) {

                names.add(course.getName());

            }
        }
        return names;
    }
    private void updateTotalCreditsLabel() {
        StringBuilder sb = new StringBuilder();
        Color color = plan.isLegalPlan() ? Color.GREEN : Color.RED;
        totalCreditsLabel.setTextFill(color);
        sb.append("סה\"כ נקודות בתכנית: ");
        sb.append(plan.getCurrentCredits());
        int requiredCredits = major.getRequiredCredits();
        sb.append(" / ").append(requiredCredits).append(" נ\"ז");
        if(plan.getCurrentCredits() >= requiredCredits) {
            sb.append(" ✔");
        } else {
            sb.append(" ✘");
            sb.append(" (נדרשות עוד ")
              .append(plan.getMissingCredits())
              .append(" נ\"ז)");

        }
        if(!plan.gotAllMandatoryCourses()) {
            sb.append("\nחסרים קורסי חובה! ✘");

        } else {
            sb.append("\nכל קורסי החובה קיימים ✔");

        }
        if(!plan.areAllSemestersLegal()) {
            sb.append("\nיש סמסטרים לא חוקיים! ✘");
        } else {
            sb.append("\nכל הסמסטרים חוקיים ✔");
        }
        if(!plan.isCompleteEnglishRequirement()) {
            sb.append("\nדרישת אנגלית לא הושלמה! ✘");
        } else {
            sb.append("\nדרישת אנגלית הושלמה ✔");
        }
        totalCreditsLabel.setText(sb.toString());
    }

}