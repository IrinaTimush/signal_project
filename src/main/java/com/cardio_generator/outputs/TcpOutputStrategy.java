package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Implements {@code OutputStrategy} to send patient health data via a TCP socket.
 * This class establishes a TCP server on a specified port and waits for a client to connect
 * to send data. Each piece of data is sent as a formatted string over the socket connection.
 *
 * Usage:
 * This strategy should be initialized with a specific TCP port. The class handles creating a
 * server socket and manages client connections in a separate thread to prevent blocking.
 */

public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Creates a TCP server on the specified port and listens for client connections.
     * Once a client is connected, it sets up a stream to send data immediately after it's generated.
     *
     * @param port The port number on which the server will listen for connections.
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the specified health data over a TCP connection.
     * Formats the patient data as a CSV line and sends it through the socket.
     * Only sends data if a client is connected and the output stream has been established.
     *
     * @param patientId The ID of the patient for whom the data is being outputted.
     * @param timestamp The time at which the data is being recorded, represented as a UNIX timestamp.
     * @param label The label of the data being outputted (e.g., "HeartRate"), which categorizes the data.
     * @param data The actual health data to be outputted, formatted as a string.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
