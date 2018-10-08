
/**
 * Simple example of a timestamp.
 *
 * Saleem Bhatti, https://saleem.host.cs.st-andrews.ac.uk/
 * 18 Sep 2018
 *
 */

import java.util.*;
import java.text.*;

public class TimeStamp {

    /**
     * This method returns a string containing today's date properly formatted for the program to read as a directory name
     * @return The date properly formatted
     */
    public String getDirectory(){
        //Create a new date object
        Date               d = new Date();
        //Create a new string specifying the date format
        String            df = new String("yyyy-MM-dd");
        //Create a new SimpleDateFormat object using the format specified in the previous string
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        //Instantiate a string called s to SimpleDateFormat's format method on the date object
        String             s = sdf.format(d);
        //Return s
        return s;
    }

    /**
     * This method returns a string containing today's date and time properly formatted for the program to read as a file name
     * @return The date and time properly formatted
     */
    public String getFile(){
        //Create a new date object
        Date               d = new Date();
        //Create a new string specifying the date and time format
        String            df = new String("yyyy-MM-dd_HH:mm:ss.SSS");
        //Create a new SimpleDateFormat object using the format specified in the previous string
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        //Instantiate a string called s to SimpleDateFormat's format method on the date object
        String             s = sdf.format(d);
        //Return s
        return s;
    }
}