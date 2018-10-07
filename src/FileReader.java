import java.io.BufferedReader;
import java.io.File;

public class FileReader {

    public String getTodaysMessages(){
        TimeStamp timeStamp = new TimeStamp();
        String messages=null;
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+timeStamp.getDirectory());
        if (!folder.isDirectory()){
            return "No messages today!";
        }
        else{
            for (File file: folder.listFiles()
                 ) {
                messages += "\n"+readFile(file)+" at "+file.getName().substring(11);
            }
        }
        return "Todays messages are:\n"+messages;
    }

    public String getMessages(String directory){
        String messages=null;
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+directory);
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
