
import java.io.*;
import java.net.*;

/**
 * Simple, non-blocking TCP server, checks before reading
 *
 * Saleem Bhatti, https://saleem.host.cs.st-andrews.ac.uk/
 * September 2018
 * February 2007
 *
 */
public class Server {

    static int           port_ = 51251; // You need to change this!
    static ServerSocket  server_;
    static int           sleepTime_ = 100; // milliseconds
    static int           bufferSize_ = 140; // a line

    public static void main(String[] args) {
        startServer();

        while (true) {

            try {
                Socket connection;
                InputStream rx;
                connection = server_.accept(); // waits for connection
                boolean clientActive = true;
                rx = connection.getInputStream();
                //server_.close(); // no need to wait now

                System.out.println("New connection ... " +
                        connection.getInetAddress().getHostName() + ":" +
                        connection.getPort());

                while(true) {
                    byte[] buffer = new byte[bufferSize_];
                    int b = 0;
                    if (b < 1) {
                        Thread.sleep(sleepTime_);
                        buffer = new byte[bufferSize_];
                        b = rx.read(buffer);
                        if (!connection.getInetAddress().isReachable(5)) {
                            connection.close();
                            System.out.println("Client disconnected");
                            break;
                        }

                    }

                    if (b > 0) {
                        byte[] message = new byte[b];
                        System.arraycopy(buffer, 0, message, 0, b);
                        String s = new String(message);

                        TimeStamp timeStamp = new TimeStamp();
                        DirAndFile dirAndFile = new DirAndFile();
                        String directory = "/cs/home/sg279/nginx_default/cs2003/Net1/" + timeStamp.getDirectory() + "/";
                        dirAndFile.writeFile(new String[]{directory, timeStamp.getFile(), s});
                    }
                }

            }
            catch (SocketTimeoutException e) {
                // no incoming data - just ignore
            }
            catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
            }
            catch (IOException e) {
                System.err.println("IO Exception: " + e.getMessage());
            }
        }
    }


    public static void startServer() {
        try {
            server_ = new ServerSocket(port_); // make a socket
            System.out.println("--* Starting server " + server_.toString());
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    protected void finalize() { // tidy up when program ends
        try {
            server_.close();
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

}