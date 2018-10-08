
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

    static int           port = 51251; // You need to change this!
    static Socket connection;
    static OutputStream output;
    static ServerSocket  server;
    static int           sleepTime = 100; // milliseconds
    static int           bufferSize = 140; // a line
    static String commands = "today- Prints all of today's messages\n" +
            "get <yyyy-MM-dd>- Prints all messages for a given day\n" +
            "delete <yyyy-mm-dd>- Deletes all messages for a given day\n" +
            "deletemessage <yyyy-MM-dd_HH:mm:ss.SSS>- Deletes a messages for a given day and time";

    public static void main(String[] args) {

        startServer();

        while (true) {

            try {
                InputStream input;
                connection = server.accept();
                input = connection.getInputStream();

                System.out.println("New connection ... " +
                        connection.getInetAddress().getHostName() + ":" +
                        connection.getPort());

                while(true) {
                    byte[] buffer = new byte[bufferSize];
                    int b = 0;
                    if (b < 1) {
                        Thread.sleep(sleepTime);
                        buffer = new byte[bufferSize];
                        b = input.read(buffer);
                        if (buffer[0]==0){
                            connection.close();
                            System.out.println("Client disconnected");
                            break;
                        }

                    }

                    if (b > 0) {
                        byte[] message = new byte[b];
                        System.arraycopy(buffer, 0, message, 0, b);
                        String s = new String(message);
                        if (s.startsWith(":")){
                            String command = s.trim().substring(1).toLowerCase();
                            commandHandler(command);
                        }
                        else{
                            TimeStamp timeStamp = new TimeStamp();
                            DirAndFile dirAndFile = new DirAndFile();
                            String directory = "/cs/home/sg279/nginx_default/cs2003/Net1/" + timeStamp.getDirectory() + "/";
                            dirAndFile.writeFile(directory, timeStamp.getFile(), s);
                        }
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
            server = new ServerSocket(port); // make a socket
            System.out.println("--* Starting server " + server.toString());
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    protected void finalize() { // tidy up when program ends
        try {
            server.close();
        }

        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    private static void commandHandler (String command){

        try{
            OutputStream output = connection.getOutputStream();
            FileReader reader = new FileReader();
            String clientMessage;
            if(command.equals("today")){
                clientMessage=reader.getTodaysMessages();
                System.out.println(clientMessage);
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.startsWith("get ")){
                clientMessage = reader.getMessages(command.substring(4));
                System.out.println(clientMessage);
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.startsWith("delete ")){
                clientMessage = reader.deleteDirectory(command.substring(7));
                System.out.println(clientMessage);
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.startsWith("deletemessage ")){
                clientMessage = reader.deleteMessage(command.substring(14));
                System.out.println(clientMessage);
                output.write((clientMessage+"\n").getBytes());
            }
            else if(command.equals("help")){
                System.out.println(commands);
                output.write((commands+"\n").getBytes());
            }
            else{
                System.out.println("Invalid command!");
                output.write("Invalid command!".getBytes());
            }
        }
        catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

}