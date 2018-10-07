
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
    static ServerSocket  server;
    static int           sleepTime = 100; // milliseconds
    static int           bufferSize = 140; // a line

    public static void main(String[] args) {

        startServer();

        while (true) {

            try {
                Socket connection;
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
                            FileReader reader = new FileReader();
                            String command = s.trim().substring(1).toLowerCase();
                            if(command.equals("today")){
                                System.out.println(reader.getTodaysMessages());
                            }
                            else if(command.startsWith("get ")){
                                System.out.println(reader.getMessages(command.substring(4)));
                            }
                            else if(command.startsWith("delete ")){
                                System.out.println(reader.deleteDirectory(command.substring(7)));
                            }
                            else if(command.startsWith("deletemessage ")){
                                System.out.println(reader.deleteMessage(command.substring(14)));
                            }
                            else if(command.equals("help")){
                                System.out.println("today- Prints all of today's messages\n" +
                                        "get <yyyy-MM-dd>- Prints all messages for a given day\n" +
                                        "delete <yyyy-mm-dd>- Deletes all messages for a given day\n" +
                                        "deletemessage <yyyy-MM-dd_HH:mm:ss.SSS>- Deletes a messages for a given day and time");
                            }
                            else{
                                System.out.println("Invalid Command!");
                            }

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

}