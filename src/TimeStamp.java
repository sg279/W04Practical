
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

    public String getDirectory(){
        Date               d = new Date();
        String            df = new String("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        String             s = sdf.format(d);
        return s;
    }

    public String getFile(){
        Date               d = new Date();
        String            df = new String("yyyy-MM-dd_HH:mm:ss.SSS");
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        String             s = sdf.format(d);
        return s;
    }
}