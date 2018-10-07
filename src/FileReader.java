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
        //Create a null string called messages
        String messages=null;
        //Create a new file object called folder at the same location as the php with the getDirectory method called to get the date
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+timeStamp.getDirectory());
        //If folder isn't a directory print that there are no messages for today
        if (!folder.isDirectory()){
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
        //Create a null string called messages
        String messages=null;
        //Create a new file object called folder at the same location as the php with the directory parameter added
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+directory);
        //If folder isn't a directory print that the directory doesn't exist
        if (!folder.isDirectory()){
            return "No such directory exists!";
        }
        else{
            for (File file: folder.listFiles()
            ) {
                messages += "\n"+readFile(file)+" at "+file.getName().substring(11);
            }
        }
        return "Messages for "+directory+" are:\n"+messages;
    }

    public String deleteDirectory(String directory){
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+directory);
        if (!folder.isDirectory()){
            return "No such directory exists!";
        }
        else{
            for (File file: folder.listFiles()
            ) {
                file.delete();
            }
            folder.delete();
            return "Directory deleted!";
        }
    }

    public String deleteMessage(String message){
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+message.substring(0,10));
        if (!folder.isDirectory()){
            return "No such directory exists!";
        }
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

    private String readFile(File file){
        String message=null;
        try{
            if (file.isFile()){
                BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
                message = reader.readLine();
            }
        }
        catch(Exception e){}
        return message;
    }
}
