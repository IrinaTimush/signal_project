package com.cardio_generator.outputs;


/**
 * The {@code OutputStrategy} interface defines the contract for outputting patient health data.
 * Classes implementing this interface are responsible for managing how data generated by different
 * data generators is handled, formatted, and transmitted or stored.
 *
 * Usage:
 * Implement this interface to create various output handlers such as console logging, file storage,
 * or network transmissions (like WebSocket or TCP). Each method implementation should know how to
 * handle incoming data for specific patients at given timestamps.
 */
public interface OutputStrategy {

    /**
     * Outputs the specified health data for a given patient at a particular timestamp.
     * Implementations of this method should ensure that the data is processed in a way that aligns
     * with the chosen output medium (e.g., displaying on a console, writing to a file, or sending over a network).
     *
     * @param patientId The ID of the patient for whom the data is being outputted. This helps in identifying
     *                  which patient the data belongs to in the output.
     * @param timestamp The time at which the data is being recorded or transmitted, typically represented as
     *                  a UNIX timestamp.
     * @param label The label of the data being outputted, such as "BloodPressure" or "HeartRate", which categorizes
     *              the type of data being handled.
     * @param data The actual data to be outputted, typically in a string format that might include measurements
     *             or other relevant health information.
     */
    void output(int patientId, long timestamp, String label, String data);
}
