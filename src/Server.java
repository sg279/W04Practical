
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

    //Define the static properties for the server
    static int           port = 51251; // You need to change this!
    static Socket connection;
    static OutputStream output;
    static InputStream input;
    static ServerSocket  server;
    static int           sleepTime = 100; // milliseconds
    static int           bufferSize = 140; // a line
    static String commands = "Start typing to send a message or use a : to enter one of the following commands:\n" +
            "today- Prints all of today's messages\n" +
            "get <yyyy-MM-dd>- Prints all messages for a given day\n" +
            "delete <yyyy-mm-dd>- Deletes all messages for a given day\n" +
            "deletemessage <yyyy-MM-dd_HH:mm:ss.SSS>- Deletes a messages for a given day and time\n" +
            "help- print this instruction set";

    public static void main(String[] args) {

        //Call the startServer method
        startServer();
        //Loop the following
        while (true) {
            //Try the following
            try {
                //Wait for a connection request, then set the socket to the connection made to the server socket
                connection = server.accept();
                //Set the input stream property to the connection's input stream
                input = connection.getInputStream();
                //Set the output stream to the connection's output stream
                output=connection.getOutputStream();
                //Write the commands string to the output stream
                output.write((commands+"\n").getBytes());
                //Instantiate a boolean called clientConnected as true
                boolean clientConnected = true;
                //Output that a connection was made and the inet address, host name, and port of the client
                System.out.println("New connection ... " +
                        connection.getInetAddress().getHostName() + ":" +
                        connection.getPort());
                //While the client connected boolean is true do the following
                while(clientConnected) {
                    //Create a new array of bytes called buffer
                    byte[] buffer = new byte[bufferSize];
                    //Instantiate an integer called b as 0
                    int b = 0;
                    //If b is less than one do the following
                    if (b < 1) {
                        //Call the sleep method on the thread with the sleepTime parameter
                        Thread.sleep(sleepTime);
                        //Set b to the input stream read to the buffer array
                        b = input.read(buffer);
                        //If first item in the array is 0 (null) close the connection and output that the client has
                        //disconnected, then set clientConnected to false
                        if (buffer[0]==0){
                            connection.close();
                            System.out.println("Client disconnected");
                            clientConnected = false;
                        }

                    }

                    //If b is more than 0 do the following
                    if (b > 0) {
                        //Create a new array of bytes of size b called message
                        byte[] message = new byte[b];
                        //Copy the buffer array to the message array
                        System.arraycopy(buffer, 0, message, 0, b);
                        //Create a string called s from the message array
                        String s = new String(message);
                        //If s starts with : call the commandHandler method on the rest of the string trimmed and parsed to lower case
                        if (s.startsWith(":")){
                            String command = s.trim().substring(1).toLowerCase();
                            commandHandler(command);
                        }
                        //Otherwise, do the following
                        else{
                            //Create a new TimeStamp object called timeStamp
                            TimeStamp timeStamp = new TimeStamp();
                            //Create a new DirAndFile object called DirAndFile
                            DirAndFile dirAndFile = new DirAndFile();
                            //Create a string called directory at the location of the php with the timeStamp.getDirectory method added
                            String directory = "/cs/home/sg279/nginx_default/cs2003/Net1/" + timeStamp.getDirectory() + "/";
                            //Call the writeFile method on the dirAndFile object with the directory, timeStamp's getFile method, and s as parameters
                            output.write(dirAndFile.writeFile(directory, timeStamp.getFile(), s).getBytes());
                        }
                    }
                }

            }
            catch (SocketTimeoutException e) {
                // no incoming data - just ignore
            }
            //If exceptions are thrown print them
            catch (InterruptedException e) {
                System.err.println("Interrupted Exception: " + e.getMessage());
            }
            catch (IOException e) {
                System.err.println("IO Exception: " + e.getMessage());
            }
        }
    }

    /**
     * This method sets the server socket to a new socket at the specified port, prints that a server is starting
     * and print an exception if one is thrown
     */
    public static void startServer() {
        try {
            server = new ServerSocket(port); // make a socket
            System.out.println("--* Starting server " + server.toString());
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    /**
     * This is an overridden method that runs when the program closes. It attempts to close the server and prints an exception if one is thrown
     */
    protected void finalize() { // tidy up when program ends
        try {
            server.close();
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    /**
     * This method handles commands that are sent to the server from the client
     * @param command The user's command
     */
    private static void commandHandler (String command){
        //Try the following
        try{
            //Create a new FileReader object called FileReader
            FileReader reader = new FileReader();
            //Create a new string called clientMessage
            String clientMessage;
            //Based on the command, set the clientMessage string to the corresponding method from the file reader, output
            //the clientMessage string and write it to the output stream. If the command isn't recognised alert the user
            if(command.equals("today")){
                clientMessage=reader.getTodaysMessages();
                System.out.println(clientMessage);
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.startsWith("get ")){
                if(command.contains("../")){
                    clientMessage = "You do not have permission to read that!";
                }
                else{
                    clientMessage = reader.getMessages(command.substring(4));
                    System.out.println(clientMessage);
                    output.write((clientMessage+"\n").getBytes());
                }
            }
            else if(command.startsWith("delete ")){
                if(command.contains("../")){
                    clientMessage = "You do not have permission to delete that!";
                }
                else{
                    clientMessage = reader.deleteDirectory(command.substring(7));
                    System.out.println(clientMessage);
                }
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.startsWith("deletemessage ")){
                if(command.contains("../")||command.contains("php")){
                    clientMessage = "You do not have permission to delete that!";
                }
                else {
                    clientMessage = reader.deleteMessage(command.substring(14));
                    System.out.println(clientMessage);
                }
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.equals("help")){
                System.out.println(commands);
                output.write((commands+"\n").getBytes());
            }
            else{
                System.out.println("Invalid command!");
                output.write("Invalid command!\n".getBytes());
            }
        }
        //Print an exception if it is thrown
        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

}