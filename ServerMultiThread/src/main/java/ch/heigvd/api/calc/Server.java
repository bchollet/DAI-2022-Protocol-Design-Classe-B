package ch.heigvd.api.calc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation - multi-thread
 */
public class Server {

    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    /**
     * Main function to start the server
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() {
        ServerSocket serverSocket;
        try {
            //Create the server socket
            serverSocket = new ServerSocket(8008);

            //Waiting for new clients to connect
            while(true) {
                Socket clientSocket = serverSocket.accept();
                LOG.log(Level.INFO, "A new client has arrived.");
                // Create new thread to handle this client
                ServerWorker worker = new ServerWorker(clientSocket);
                Thread thread = new Thread(worker);
                thread.start();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }

    }
}
