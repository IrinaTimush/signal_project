package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Implements {@code PatientDataGenerator} to simulate and manage alert states for patients.
 * This class toggles alert states between "triggered" and "resolved" based on a probability model.
 *
 * Usage:
 * This class should be instantiated with the number of patients to manage alert states for.
 * It maintains a state for each patient and generates alert signals periodically based on defined probabilities.
 */

public class AlertGenerator implements PatientDataGenerator {

    public static final Random RANDOM_GENERATOR = new Random();// Changed the variable name to upper case

    private boolean[] alertStates; // false = resolved, true = pressed   //Changed the variable name to camelCase


    /**
     * Constructs an {@code AlertGenerator} for the specified number of patients.
     * Initializes all patients' alert states to "resolved" (false).
     *
     * @param patientCount The total number of patients for which to manage alert states.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }


    /**
     * Generates an alert condition for a specific patient based on stochastic processes.
     * Alerts may be triggered or resolved each time this method is called, depending on the current state and
     * defined probabilities.
     *
     * @param patientId The ID of the patient for whom to generate the alert data.
     * @param outputStrategy The output strategy to which the alert state should be sent.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double Lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-Lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
