
import java.io.*;
import java.net.*;

/**
 * Simple, non-blocking TCP client with input from keybaord,
 * using socket timeout.
 * Saleem Bhatti, https://saleem.host.cs.st-andrews.ac.uk/
 * September 2018
 * February 2007
 *
 */
public class Client {
    static int sleepTime_ = 5000; // milliseconds
    static int bufferSize_ = 140; // a line
    static int soTimeout_ = 10; // milliseconds
    static Socket       connection;

    public static void main(String[] args) {
        if (args.length != 2) { // user has not provided arguments
            System.out.println("\n TcpClientSimpleNB <servername> <portnumber> \n");
            System.exit(0);
        }

        OutputStream tx;
        InputStream	 rx;
        byte[]       buffer;
        int          b ;

        connection = startClient(args[0], args[1]);
        while(true){
            try {
                tx = connection.getOutputStream();
                b = 0;

                buffer = new byte[bufferSize_];
                if (System.in.available() > 0) {
                    b = System.in.read(buffer); // keyboard
                }

                if (b > 0) {
                    byte[] message = new byte[b];
                    System.arraycopy(buffer, 0, message, 0, b);
                    tx.write(message, 0, b); // send to server
                    System.out.println("Sending " + b + " bytes");
                }
            }

            catch (SocketTimeoutException e) {
                // no incoming data - just ignore
            }
            catch (IOException e) {
                System.err.println("IO Exception: " + e.getMessage());
            }
        }

    }

    static Socket startClient(String hostname, String portnumber) {
        Socket connection = null;

        try {
            InetAddress address;
            int         port;

            address = InetAddress.getByName(hostname);
            port = Integer.parseInt(portnumber);

            connection = new Socket(address, port); // server
            connection.setSoTimeout(soTimeout_);

            System.out.println("--* Connecting to " + connection.toString());
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }

        return connection;
    }

    protected void finalize() { // tidy up when program ends
        try {
            connection.close();
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

}