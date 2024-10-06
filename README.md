# JavaServerClient

This repository contains a simple implementation of a chat server and client using Java's networking capabilities. The server can accept multiple clients, allowing them to communicate with each other in real-time.

## Server.java

The `Server.java` file implements a multi-threaded chat server. Here’s a brief explanation of its components:

### Key Components

- **Imports**: The necessary classes for networking (`ServerSocket`, `Socket`), input/output (`Scanner`, `Formatter`), and data structures (`ArrayList`) are imported.

- **Server Class**: This class handles incoming connections, maintains a list of connected clients, and manages message broadcasting.

- **AcceptNewClient Class**: A nested class that implements `Runnable`. It listens for new client connections:
  - Accepts connections and initializes input/output streams.
  - Prompts each client to enter their name.
  - Starts a new thread for each connected client.

- **Client Class**: Another nested class that implements `Runnable`. It handles communication for individual clients:
  - Reads messages from the client and broadcasts them to all connected clients.
  - Tracks the number of online clients.

### Main Method

The `main` method initializes the server, starts the thread for accepting new clients, and waits for user input to shut down the server.


## How to Run

1. **Compile the Java files**:
   ```bash
   javac Server.java Client.java
   ```
2. **Run the server**:
   ```bash
   java Server
   ```
3. **Run the client**:
   ```bash
   java Client
   ```
4. **To Exit**: Type ``exit`` in the client console.

## Features

- Multiple clients can connect to the server.
- Clients can send messages to each other.
- The server keeps track of online users.
