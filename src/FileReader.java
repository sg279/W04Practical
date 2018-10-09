import java.io.BufferedReader;
import java.io.File;

/**
 * This class contains methods for reading and deleting messages in the directory
 */
public class FileReader {

    /**
     * This method gets all of the messages in the directory for today's date
     * @return Today's messages and the times they were sent
     */
    public String getTodaysMessages(){
        //Create a new TimeStamp object called timeStamp
        TimeStamp timeStamp = new TimeStamp();
        //Create an empty string called messages
        String messages="";
        //Create a new file object called folder at the same location as the php with the getDirectory method called to get the date
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+timeStamp.getDirectory());
        //If folder isn't a directory or there are no files in it print that there are no messages for today
        if (!folder.isDirectory()||folder.listFiles().length==0){
            return "No messages today!";
        }
        //If not, for each file in the directory add the content and time it was created to the messages string and return that string after the loop is finished
        else{
            for (File file: folder.listFiles()
                 ) {
                messages += "\n"+readFile(file)+" at "+file.getName().substring(11);
            }
            return "Today's messages are:\n"+messages;
        }
    }

    /**
     * This method gets messages for a particular date
     * @param directory The directory of the messages (the date)
     * @return The messages sent on that date and their times
     */
    public String getMessages(String directory){
        //Create an empty string called messages
        String messages="";
        //Create a new file object called folder at the same location as the php with the directory parameter added
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+directory);
        //If folder isn't a directory print that the directory doesn't exist
        if (!folder.isDirectory()){
            return "No such directory exists!";
        }
        //If not, for each file in the directory add the content and time it was created to the messages string and return that string after the loop is finished
        else{
            for (File file: folder.listFiles()
            ) {
                messages += "\n"+readFile(file)+" at "+file.getName().substring(11);
            }
        }
        return "Messages for "+directory+" are:\n"+messages;
    }

    /**
     * This method deletes a given directory
     * @param directory The directory to be deleted
     * @return A message confirming the directory has been deleted or that it doesn't exist
     */
    public String deleteDirectory(String directory){
        //Create a new file object called folder at the same location as the php with the directory parameter added
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+directory);
        //If folder isn't a directory print that the directory doesn't exist
        if (!folder.isDirectory()){
            return "No such directory exists!";
        }
        //If not, for each file in the directory delete the file, then, after the loop has finished delete the folder
        //and print that the directory was deleted
        else{
            for (File file: folder.listFiles()
            ) {
                file.delete();
            }
            folder.delete();
            return "Directory deleted!";
        }
    }

    /**
     * This method deletes a message sent at a specific date and time
     * @param message The location and file name of the file to be deleted
     * @return A message confirming if the message was deleted or that it doesn't exist
     */
    public String deleteMessage(String message){
        //Try the following
        try{
            //Create a new file object called folder at the same location as the php with the first 11 characters
            //of the mssage parameter (the date) added
            File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+message.substring(0,10));
            //If folder isn't a directory print that there are no messages for that day
            if (!folder.isDirectory()){
                return "No messages for that day!";
            }
            //If not, define a string as message does not exist, and for each file in the folder if the file's name is the one
            //that the user specified delete it and change the return message to say that the message was deleted and return it
            else{
                String returnMessage = "Message does not exist!";
                for (File file: folder.listFiles()
                ) {
                    if(file.getName().equals(message)){
                        file.delete();
                        returnMessage = "Message deleted!";
                    }
                }

                return returnMessage;
            }
        }
        //If an index out of bound exception is thrown (the user entered a file name less than 11 characters)
        //print that the message doesn't exist
        catch(IndexOutOfBoundsException e){
            return "Message does not exist!";
        }

    }

    /**
     * This method reads the content of a plain text file and returns it
     * @param file The file to be read
     * @return The content of the file
     */
    private String readFile(File file){
        //Create a null string called message
        String message=null;
        //Try the following
        try{
            //If the file is a file, create a new buffered reader for the file and set the message string to it's content
            if (file.isFile()){
                BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
                message = reader.readLine();
            }
        }
        //If an exception is thrown set the return message to alert that there was an error reading the file
        catch(Exception e){
            message = "File read error!";
        }
        //Return the return message
        return message;
    }
}
