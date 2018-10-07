/**
 * Simple example creating a directory and a file.
 *
 * Saleem Bhatti, https://saleem.host.cs.st-andrews.ac.uk/
 * 18 Sep 2018
 *
 */

import java.io.*;

public class DirAndFile {

    /**
     * This method handles the finding or creation of the correct directory for content to be written to
     * a plain text file in with the write method
     * @param directory The directory where the file will be written
     * @param fileName The name of the file
     * @param text The content of the file
     */
    public void writeFile(String directory, String fileName, String text) {

        //Create a new file called dir from the directory parameter
        File dir = new File(directory);
        //If the file exists do the following
        if (dir.exists()) {
            //Print that the directory already exists
            System.out.println("++ File already exists: " + directory);
            //Call the write method with the directory, fileName, and text as parameters
            write(directory, fileName, text);
            //Output that the content was written to the the file
            System.out.println("++ Wrote " + text + " to file: " + fileName);
        }
        //Otherwise, if the directory is able to be created do the following
        else if (dir.mkdir()) {
            //Print that the directory was created
            System.out.println("++ Created directory: " + directory);
            //Call the write method with the directory, fileName, and text as parameters
            write(directory, fileName, text);
            //Print that the text was written to the file
            System.out.println("++ Wrote " + text + " to file: " + fileName);
        }
        //Otherwise, print that the directory couldn't be created
        else {
            System.out.println("++ Failed to create directory: " + directory);
        }


    }

    /**
     * This method writes content to a plain text file in a given directory with a given name
     * @param directory The directory where the file will be written
     * @param fileName The name of the file
     * @param text The content of the file
     */
    private void write(String directory, String fileName, String text){
        //Set the filename string to the directory destination plus the file name
        fileName = directory + File.separator + fileName;
        //Create a new file object using the file name
        File file = new File(fileName);
        //If the file already exists output that the file exists
        if (file.exists()) {
            System.out.println("++ File already exists: " + fileName);
        }
        //Otherwise, try the following
        else{
            try {
                //Create a new FileWriter object called fw with the file object as a parameter
                FileWriter fw = new FileWriter(file);
                //Write the text to the file
                fw.write(text);
                //Clear and close the writer
                fw.flush();
                fw.close();
            }
            //If an exception is thrown, print it
            catch (IOException e) {
                System.out.println("IOException - write(): " + e.getMessage());
            }
        }
    }
}