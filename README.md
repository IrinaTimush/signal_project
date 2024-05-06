# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
## Project Members
- Student ID: i6341536
- Student ID: i6360797


## uml_models link:
https://github.com/IrinaTimush/signal_project/tree/master/src/uml_modelsgit 

## Sequence Diagram Description
First of all, patient data source is an external to the system object, because they are
patients themselves. From the patients, the information is sent to the AlertGenerator,
which is already an internal to the system object. AlertGenerator sends a request to the 
DataStorage System for fetching historical data to confirm trends or retrieve past
incidents of similar alerts. DataStorage System the returns the the historical data 
to the AlertGenerator, where the data is being evaluated and compared against special
criteria/ each patient's special set of thresholds.  With both historical and real-time 
data , the AlertGenerator can now evaluate the data against certain predefined criteria 
to determine if an alert should be triggered. Then, if the data of the patient that is
currently monitored, reaches a certain threshold, the AlertGenerator creates/generates 
and Alert. This alert then interacts with an external object- the medical staff. The Alert
is sent to to medical staff to be acknowledged and responded to. Then the medical staff 
can resolve the Alert  manually (by medical staff after assessing the patient and perhaps
adjusting their treatment, confirming that the alert condition no longer exists), or
automatically (if subsequent patient data shows normal readings continuously  
for a predefined period, suggesting that the condition has stabilized). 

## State Diagram Descrption
This State Diagram presents the lifecycle of an AlertGeneration system. The diagram
begins with the "Initial state," represented by a filled circle, which is the starting
point for any state machine diagram. From the initial state, the process moves into the 
"Generated" state. This occurs when the AlertGenerator detects that patient data meets 
the predefined criteria in form of thresholds. The transition to this state is not labeled
with an event, implying it's the system's automatic response to the data received. Once
an alert is generated, it transitions to the "Sent" state when the alert is communicated
to the medical staff via notifications on monitoring screens. The label "alert generated"
signifies the event that causes this transition. After the alert is sent, it reaches the 
"Acknowledged" state when a staff member notices and acknowledges the alert. This action 
is critical as it confirms that the alert has been seen and will be acted upon. The
transition here is labeled "alert sent." Finally, the "Resolved" state is achieved once
the situation that caused the alert is addressed. An alert can be resolved either 
manually by medical staff after assessing and intervening or automatically if the system
detects that the patient's condition has returned to normal. The transition to the final
state, represented by a concentric circle, is labeled with both manual and automatic 
resolution events. The diagram ends here, indicating the completion of the alert's
lifecycle.

## UML Diagrams Description
Alert Generation System: The system is designed with an AlertGenerator class that
subscribes to a data stream (patient data). It evaluates this data and triggers an
alert if specific conditions are met. The Alert class contains patient ID, condition
details, and timestamp, ensuring that each alert is adequately identified and timestamped
for tracking and auditing. An AlertManager is responsible for sending these alerts, to 
medical staff through a monitoring system, indicating a clear separation of concerns
between generating and managing alerts.

Patient Identification System: The IdentityManager ensures that patients are correctly
identified before any data processing or alert generation occurs, which is crucial for 
patient safety. PatientIdentifier matches patient IDs and retrieves patient records,
showing a direct association with PatientRecord, which includes essential information 
such as name, date of birth, and medical history.

Data Storage System: Central to data persistence, DataStorage operates with a hashmap, 
allowing quick retrieval and storage operations indexed by patient ID. DataRetriever
interacts with DataStorage, indicating a single point of interaction for data fetching,
encapsulating the storage details.

Data Access Layer: This layer acts as the bridge between the incoming data and the 
system. DataListener waits for incoming data, suggesting a possible real-time data feed.
DataParser processes the data files into PatientData, indicating a transformation from 
raw data to a structured format the system can use.
DataSourceAdapter provides an interface to standardize how different data sources 
interact with the system, ensuring flexibility and scalability.
