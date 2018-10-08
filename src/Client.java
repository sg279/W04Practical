
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

    //Define static properties for the client
    static int bufferSize = 140;
    static int timeout = 10;
    static Socket connection;
    static OutputStream output;

    public static void main(String[] args) {

        //Prints usage information and exits if the user runs the program without two arguments
        if (args.length != 2) { // user has not provided arguments
            System.out.println("\n TcpClientSimpleNB <servername> <portnumber> \n");
            System.exit(0);
        }

        //Define an array of bytes called buffer
        byte[] buffer;
        //Define an integer called b
        int b ;
        //Set the connection socket to the startClient method called with the arguments as parameters
        connection = startClient(args[0], args[1]);

        //While true
        while(true){
            //Try the following
            try {
                //Set the output stream to the socket's output stream
                output = connection.getOutputStream();
                //Set b to 0
                b = 0;
                //Set the buffer array to a new array of bytes the size of the bufferSize property
                buffer = new byte[bufferSize];
                //If there are bytes to read set b to the array of bytes
                if (System.in.available() > 0) {
                    b = System.in.read(buffer);
                }
                //If b is more than 0 do the following
                if (b > 0) {
                    //Create an array of bytes called message the size of b
                    byte[] message = new byte[b];
                    //Copy the buffer array to the message array
                    System.arraycopy(buffer, 0, message, 0, b);
                    //Write the message to the output stream
                    output.write(message, 0, b);
                    //Print the number of bytes being sent
                    System.out.println("Sending " + b + " bytes");

                }
                //Create a new input stream from the input stream of the connection
                InputStream input = connection.getInputStream();
                //Create a new buffered reader from the input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                //Instantiate a string as the first line of the input stream
                String line=reader.readLine();
                //While the line string isn't null, print it and read the next line
                while(line!=null){
                    System.out.println(line);
                    line=reader.readLine();
                }

            }
            catch (SocketTimeoutException e) {
                // no incoming data - just ignore
            }
            //If an IOException is thrown print it
            catch (IOException e) {
                System.err.println("IO Exception: " + e.getMessage());
            }
        }

    }

    /**
     * This method tries to connect to a server with the given parameters
     * @param hostname The hostname of the server
     * @param portNumber The port number of the server
     * @return The socket with the connection to the server
     */
    static Socket startClient(String hostname, String portNumber) {
        //Create a socket called connection and set it to null
        Socket connection = null;
        //Try the following
        try {
            //Create a new InetAddress object called address
            InetAddress address;
            //Create an integer called port
            int port;
            //Set the Inet address to the address of the server at the hostname parameter
            address = InetAddress.getByName(hostname);
            //Set the port integer to the portNumber parameter parsed to an integer
            port = Integer.parseInt(portNumber);
            //Set the connection socket to a new socket with the address and port values parsed as parameters
            connection = new Socket(address, port);
            //Set the socket timeout to the timeout value
            connection.setSoTimeout(timeout);
            //Print that that the server is being connected to and the server details
            System.out.println("--* Connecting to " + connection.toString());
        }
        //If an exception is thrown print it
        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
        //Return the socket
        return connection;
    }

    /**
     * This method runs when the program exits and sends the server an indication it has disconnected
     */
    protected void finalize() {
        //try the following
        try {
            //Create a new empty array of bytes
            byte[] disconnect = new byte[0];
            //Set the first object in the array to 0
            disconnect[0]=0;
            //Write the array to the output stream1
            output.write(disconnect);
            //Close the connection
            connection.close();
        }
        //If an IOException is thrown print it
        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

}