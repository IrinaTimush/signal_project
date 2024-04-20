package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * The {@code PatientDataGenerator} interface defines the contract for generating patient-specific data.
 * Implementations of this interface are responsible for generating and sending simulated health data
 * for a given patient to a specified output strategy.
 *
 * Usage:
 * Implement this interface to create custom generators for different types of patient data,
 * such as ECG, blood pressure, or blood saturation levels. Each generator will receive an ID representing
 * a specific patient and an output strategy to handle the generated data.
 */

public interface PatientDataGenerator {

    /**
     * Generates health data for a specific patient and sends it to the provided output strategy.
     * This method should be implemented to simulate real-time data generation based on the specific health metrics
     * the implementing class is responsible for.
     *
     * @param patientId The ID of the patient for whom data is being generated. This ID is used to uniquely identify
     *                  the patient in the data output.
     * @param outputStrategy The output strategy to which the generated data should be sent. This could be any class
     *                       that implements the {@link OutputStrategy} interface, such as console output, file output,
     *                       or network-based outputs like WebSocket or TCP.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
