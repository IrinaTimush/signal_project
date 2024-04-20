package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Implements {@code PatientDataGenerator} to simulate and generate blood saturation data for patients.
 * This class manages random variations in saturation values to mimic real-time fluctuations in patient health data.
 *
 * Usage:
 * An instance of this class should be created with the total number of patients as a parameter.
 * It initializes baseline saturation values for each patient and provides a method to generate periodic updates.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;

    /**
     * Constructs a {@code BloodSaturationDataGenerator} with the specified number of patients.
     * Initializes baseline saturation values between 95% and 100% for each patient to start.
     *
     * @param patientCount The total number of patients for which to generate data.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }



    /**
     * Generates and outputs a new blood saturation value for a specific patient.
     * The method simulates small fluctuations in saturation and ensures values remain within a realistic range (90% to 100%).
     * Outputs the new saturation data using the provided output strategy.
     *
     * @param patientId The ID of the patient for whom the data is being generated.
     * @param outputStrategy The output strategy to which the generated data should be sent.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
