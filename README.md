# Java Chat Application

This project is a **Java-based chat application** that facilitates real-time communication between clients over a network. The application uses **Java Sockets** to manage connections and supports both single and multiple clients through a server-client architecture. It is built using **Java Swing** for the user interface and **Java Networking** for handling communication.

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Architecture and Workflow](#architecture-and-workflow)
- [Setup and Installation](#setup-and-installation)
- [Features](#features)
- [Future Enhancements](#future-enhancements)

---

## Project Overview

This chat application enables users to connect with each other over a network. It includes both client and server modules, where multiple clients can connect to a central server and exchange messages in real-time. The GUI is built with **Java Swing**, offering a simple and intuitive chat interface.

## Tech Stack

- **Java**: Core programming language.
- **Java Swing**: GUI framework for building the chat interface.
- **Java Sockets**: For managing client-server communication.
- **Multithreading**: Enables handling multiple clients simultaneously.

## Architecture and Workflow

The application follows a client-server model with the following components:

1. **Server**: Manages connections and routes messages between connected clients.
2. **Client**: Connects to the server and facilitates sending and receiving messages.

### Workflow Diagram

```plaintext
            +-------------------+        +-------------------+        +-------------------+
            |                   |        |                   |        |                   |
            |     Client 1      | -----> |      Server       | <----- |     Client 2      |
            |                   |        |                   |        |                   |
            +-------------------+        +-------------------+        +-------------------+
                      |                            |                           |
                      |                            |                           |
                      | <---------------- Message --------------------------- |
                      |                       Broadcast                        |
                      V
                  Other Clients
```

In this setup:
- Each **Client** connects to the **Server** using Java Sockets.
- The **Server** manages each client connection on a separate thread, facilitating real-time communication across all clients.
- Messages from any client are broadcasted to all connected clients.

## Setup and Installation

### Prerequisites
- **Java Development Kit (JDK) 8** or higher.

### Installation and Execution

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Acrto3Hil3/ChatApplication-java.git
   ```

2. **Compile the Project**:
   Navigate to the project directory and compile the code:
   ```bash
   javac *.java
   ```

3. **Start the Server**:
   Run the `Server` class to initiate the server on a specific port.
   ```bash
   java Server
   ```

4. **Run the Client**:
   Start the `Client` class to connect to the server.
   ```bash
   java Client
   ```

5. **Connect Multiple Clients**:
   Run multiple instances of the `Client` class to simulate a multi-user chat.

### Configuration

By default, the server listens on a specific port (defined in the `Server` class). You may update the server IP and port in the `Client` class to match your network settings if needed.

## Features

- **Real-Time Messaging**: Send and receive messages instantly.
- **Multiple Clients**: Connects multiple clients to the server for group chat.
- **User-Friendly Interface**: Java Swing-based GUI for intuitive interaction.

## Future Enhancements

- **File Sharing**: Allow users to send files within the chat.
- **User Authentication**: Implement login functionality for secure access.
- **Private Messaging**: Enable one-to-one private conversations between users.
