/**
 * Simple example creating a directory and a file.
 *
 * Saleem Bhatti, https://saleem.host.cs.st-andrews.ac.uk/
 * 18 Sep 2018
 *
 */

import java.io.*;

public class DirAndFile {

    public void writeFile(String args[]) {

        String dirName = new String(args[0]);
        String fileName = new String(args[1]);
        String text = new String(args[2]);

        File dir = new File(dirName);

        if (dir.exists()) {
            System.out.println("++ File already exists: " + dirName);
            write(dirName, fileName, text);
            System.out.println("++ Wrote \"" + text + "\" to file: " + fileName);
        }
        else if (dir.mkdir()) {
            System.out.println("++ Created directory: " + dirName);
            write(dirName, fileName, text);
            System.out.println("++ Wrote " + text + " to file: " + fileName);
        }
        else {
            System.out.println("++ Failed to create directory: " + dirName);
        }


    }

    private void write(String dirName, String fileName, String text){
        fileName = dirName + File.separator + fileName;
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("++ File already exists: " + fileName);
        }

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.flush();
            fw.close();
        }
        catch (IOException e) {
            System.out.println("IOException - write(): " + e.getMessage());
        }
    }
}