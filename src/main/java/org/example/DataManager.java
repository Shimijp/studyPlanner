package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<Course> allCourses;

    /**
     * Loads courses from a JSON file into the list.
     * @param fileName The path to the JSON file (e.g., "courses.json")
     */
    public void loadCourses(String fileName) {
        ObjectMapper mapper = new ObjectMapper();

        // 1. We assume the file is directly inside 'src/main/resources'
        // We add "/" to tell Java to look at the root of the resources folder
        String resourcePath = "/" + fileName;

        // 2. Open a stream to the resource instead of a File object
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                System.err.println("CRITICAL ERROR: Could not find file: " + resourcePath);
                System.err.println("Make sure 'courses.json' is strictly inside 'src/main/resources'");
                return;
            }

            // 3. Jackson can read directly from InputStream!
            allCourses = mapper.readValue(inputStream, new TypeReference<List<Course>>(){});
            System.out.println("Successfully loaded " + allCourses.size() + " courses from resources.");

        } catch (IOException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Course> getAllCourses() {
        return allCourses;
    }
    public Course getCourseById(int id) {
        if (allCourses == null) return null;
        return allCourses.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
