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
    private int totalDegreeCredits = 0;

    @FXML
    public void initialize() {
        dataManager = new DataManager();
        dataManager.loadCourses("courses.json");
        filterAndDisplayCourses("");
        Major cs = new Major("Computer Science", 120, dataManager.getAllCourses());
        Plan plan = new Plan(cs, true);


        // 2. הוספת מאזין לחיפוש
        // בכל פעם שהטקסט משתנה (מקלדים או מוחקים), הפונקציה הזו רצה
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAndDisplayCourses(newValue);
        });
        displayCourses();
        int startYear = 2026;
        String [] semesterNames = {"סתיו", "אביב", "קיץ"};

        for(int year = startYear; year <= 2030; year++) {
                for(String sem : semesterNames) {
                    String title = sem + " " + year;
                    addSemesterColumn(title);

                }
            }



        totalCreditsLabel.setText("מערכת מוכנה לעבודה");
    }
    private void filterAndDisplayCourses(String query) {
        // מנקים את המסך
        coursesListContainer.getChildren().clear();

        List<Course> allCourses = dataManager.getAllCourses();
        if (allCourses == null) return;

        for (Course course : allCourses) {
            // אם שורת החיפוש ריקה -> תציג הכל
            // אחרת -> תבדוק אם השם של הקורס מכיל את הטקסט שחיפשנו
            if (query == null || query.isEmpty() || course.getName().contains(query)) {

                HBox courseCard = createCourseCard(course);
                coursesListContainer.getChildren().add(courseCard);
            }
        }
    }
    private void displayCourses() {
        List<Course> courses = dataManager.getAllCourses();
        for (Course course : courses) {
            HBox courseCard = createCourseCard(course);
            coursesListContainer.getChildren().add(courseCard);
        }
    }
    private HBox createCourseCard(Course course) {
        HBox courseCard = new HBox();
        courseCard.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
        courseCard.setPadding(new Insets(10));
        courseCard.setSpacing(10);
        courseCard.setAlignment(Pos.CENTER_LEFT);
        courseCard.setPrefWidth(240);
        courseCard.setMinWidth(240);

        // CSS Style - הוספתי אפקט שמשנה את הסמן ליד (Hand) כשעוברים מעל, שיידעו שזה לחיץ
        courseCard.setStyle("-fx-background-color: white; " +
                "-fx-border-color: #e0e0e0; " +
                "-fx-border-radius: 8; " +
                "-fx-background-radius: 8; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 2); " +
                "-fx-cursor: hand;");

        // --- NEW: Event Handler ---
        // ברגע שיש לחיצת עכבר (Mouse Clicked) על הכרטיס כולו
        courseCard.setOnMouseClicked(event -> {
            showCourseDetails(course); // קורא לפונקציה החדשה שנכתוב למטה
        });
        courseCard.setOnDragDetected(event -> {
            // אומרים למערכת: אנחנו מתחילים גרירה מסוג העתקה
            javafx.scene.input.Dragboard db = courseCard.startDragAndDrop(javafx.scene.input.TransferMode.COPY);

            // שמים את ה-ID של הקורס בתוך "המזוודה" (Clipboard)
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(String.valueOf(course.getId())); // מעבירים רק את ה-ID!
            db.setContent(content);

            event.consume();
        });

        Label nameLabel = new Label(course.getName());
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(170);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);

        Label creditsLabel = new Label(String.valueOf(course.getCredits()));
        creditsLabel.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 5; -fx-background-radius: 20; -fx-min-width: 25; -fx-alignment: center;");
        creditsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        courseCard.getChildren().addAll(creditsLabel, nameLabel);

        return courseCard;
    }
    /**
     * Opens a dialog window with full course details.
     * @param course The course to display
     */
    private void showCourseDetails(Course course) {
        // 1. Create an Alert of type INFORMATION
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("פרטי קורס");
        alert.setHeaderText(course.getName());

        // Formating the semesters list cleanly
        String semestersStr = String.join(", ", course.getSemesterFullName());

        // 2. Build the content string
        StringBuilder content = new StringBuilder();
        content.append("מספר קורס: ").append(course.getId()).append("\n");
        content.append("נקודות זכות: ").append(course.getCredits()).append("\n");
        content.append("סמסטרים מוצעים: ").append(semestersStr).append("\n\n");

        // Add boolean flags
        if (course.isMandatory()) content.append("• קורס חובה\n");
        if (course.isAdvanced()) content.append("• מתקדם\n");
        if (course.isEnglish()) content.append("• נלמד באנגלית\n");
        if (course.isSemniarion()) content.append("• סמינריוני\n");

        // Check prerequisites
        if (course.getPrerequisites() != null && !course.getPrerequisites().isEmpty()) {
            content.append("\nדרישות קדם: ").append(this.getPrerequisiteNames(course.getPrerequisites()));
        } else {
            content.append("\nאין דרישות קדם.");
        }

        alert.setContentText(content.toString());

        // 3. Fix direction to RTL (Right-to-Left) for Hebrew text
        alert.getDialogPane().setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);

        // 4. Show the alert and wait for user to close it
        alert.showAndWait();
    }
    private void addSemesterColumn(String title) {
        // 1. יצירת העמודה (רגיל)
        VBox semesterColumn = new VBox();
        semesterColumn.setMinWidth(200);
        semesterColumn.setPrefWidth(200);
        semesterColumn.setSpacing(10);
        semesterColumn.setPadding(new Insets(10));
        semesterColumn.setStyle("-fx-background-color: #eef6ff; -fx-border-color: #b3d9ff; -fx-border-width: 0 1 0 0;");

        Label headerLabel = new Label(title);
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        headerLabel.setAlignment(Pos.CENTER);

        VBox semesterCourseList = new VBox();
        semesterCourseList.setSpacing(5);
        semesterCourseList.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-min-height: 100; -fx-padding: 5;");
        VBox.setVgrow(semesterCourseList, Priority.ALWAYS);

        // --- חדש: יצירת מונה ו-Label לסמסטר הזה ---
        // AtomicInteger מאפשר לנו לשנות את המספר מתוך ה-Event Handler
        java.util.concurrent.atomic.AtomicInteger currentSemesterCredits = new java.util.concurrent.atomic.AtomicInteger(0);
        Label footerLabel = new Label("נק\"ז: 0");
        footerLabel.setAlignment(Pos.CENTER_RIGHT);

        // --- לוגיקה לגרירה ---
        semesterCourseList.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        semesterCourseList.setOnDragDropped(event -> {
            javafx.scene.input.Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                int courseId = Integer.parseInt(db.getString());
                Course course = dataManager.getCourseById(courseId);

                if (course != null) {

                    HBox listCard = createSemesterCourseCard(course);
                    semesterCourseList.getChildren().add(listCard);

                    // 2. עדכון מונה הסמסטר (מקומי)
                    int newSemesterTotal = currentSemesterCredits.addAndGet(course.getCredits());
                    footerLabel.setText("נק\"ז: " + newSemesterTotal);


                    totalDegreeCredits += course.getCredits();
                    totalCreditsLabel.setText("סה\"כ נקודות: " + totalDegreeCredits);

                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // הוספה לעמודה
        semesterColumn.getChildren().addAll(headerLabel, semesterCourseList, footerLabel);
        semestersContainer.getChildren().add(semesterColumn);
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
    private HBox createSemesterCourseCard(Course course) {
        HBox card = new HBox();
        card.setNodeOrientation(javafx.geometry.NodeOrientation.RIGHT_TO_LEFT);
        card.setPadding(new Insets(5));
        card.setAlignment(Pos.CENTER_LEFT);
        // עיצוב שונה - רקע תכלת בהיר כדי להבדיל מהרשימה
        card.setStyle("-fx-background-color: #e3f2fd; -fx-border-color: #90caf9; -fx-border-radius: 3; -fx-background-radius: 3;");

        Label nameLabel = new Label(course.getName());
        nameLabel.setFont(Font.font("Arial", 12));

        Label creditsLabel = new Label("(" + course.getCredits() + ")");
        creditsLabel.setFont(Font.font("Arial", 10));
        creditsLabel.setTextFill(Color.DARKGRAY);

        // רווח קטן בין השם לנקודות
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(nameLabel, spacer, creditsLabel);
        return card;
    }
}