package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main entry point for the JavaFX application.
 * Loads the FXML layout and initializes the primary stage.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML layout from the resources folder
        // Note: Ensure 'main_layout.fxml' is located directly under src/main/resources
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/main_layout.fxml"));
        Parent root = fxmlLoader.load();

        // Set the scene dimensions (Width: 1000px, Height: 700px)
        Scene scene = new Scene(root, 1000, 700);

        stage.setScene(scene);
        stage.setTitle("Academic Degree Planner");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application lifecycle

    }
}