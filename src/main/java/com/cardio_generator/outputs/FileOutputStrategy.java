package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the {@code OutputStrategy} for writing patient health data to files.
 * Each type of data (label) is stored in a separate file within a specified base directory.
 *
 * Usage:
 * This strategy is initialized with a base directory where all output files will be stored.
 * Data for each type of measurement is appended to a specific file labeled accordingly (e.g., BloodPressure.txt).
 */

public class FileOutputStrategy implements OutputStrategy { //Changed the class name to start from an upper case letter


    private String baseDirectory; //Changed the variable name to camelCase

    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>(); //Changed variable name to camelCase



    /**
     * Constructs a {@code FileOutputStrategy} with the specified base directory.
     *
     * @param baseDirectory the directory where output files will be created and data will be written.
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the health data for a patient into a file. Each type of data label has its own file.
     * Data entries are appended to the respective file and include the patient ID, timestamp, label, and data.
     *
     * @param patientId The ID of the patient for whom the data is being outputted.
     * @param timestamp The time at which the data is being recorded, typically represented as a UNIX timestamp.
     * @param label The label of the data being outputted (e.g., "BloodPressure"), used to categorize and direct the data to the appropriate file.
     * @param data The actual health data to be outputted, formatted as a string.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        //Changed the variable name to camelCase
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}