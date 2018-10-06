import java.io.BufferedReader;
import java.io.File;

public class FileReader {

    public String getTodaysMessages(){
        TimeStamp timeStamp = new TimeStamp();
        String messages;
        File folder = new File("/cs/home/sg279/nginx_default/cs2003/Net1/"+timeStamp.getDirectory());
        if (!folder.isDirectory()){
            return null;
        }
        else{
            for (File file: folder.listFiles()
                 ) {

            }
        }
        return null;
    }

    private String readFile(File file){
        try{
            if (file.isFile()){
                //BufferedReader reader = new BufferedReader(new FileReader());
            }
        }
        catch(Exception e){}
        return null;
    }
}
