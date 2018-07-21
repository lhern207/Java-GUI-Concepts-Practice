/*
 * 
 */

/**
 *
 * @author Lester
 */
import java.io.BufferedWriter;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JOptionPane;

public class BasicFile {

    File f;

    public BasicFile() {
        JFileChooser choose = new JFileChooser(".");
        int status = choose.showOpenDialog(null);

        try {
            if (status != JFileChooser.APPROVE_OPTION) {
                throw new IOException();
            }
            f = choose.getSelectedFile();
            if (!f.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            display(e.toString(), "File not found ....");
        } catch (IOException e) {
            display(e.toString(), "Approve option was not selected");
        }
    }

    void display(String msg, String s) {
        JOptionPane.showMessageDialog(null, msg, s, JOptionPane.ERROR_MESSAGE);
    }

    public void copyFile(String copy) throws FileNotFoundException, IOException {

        FileInputStream source = new FileInputStream(f.getAbsolutePath());
        FileOutputStream destination = new FileOutputStream(copy);

        FileChannel sourceFileChannel = source.getChannel();
        FileChannel destinationFileChannel = destination.getChannel();

        long size = sourceFileChannel.size();
        sourceFileChannel.transferTo(0, size, destinationFileChannel);
    }

    public void appendToFile(String text) throws FileNotFoundException, IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f.getAbsolutePath(), true)));
        out.println(text);
        out.close();
    }

    public void overWriteFile(String text) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(f.getAbsolutePath());
        out.println(text);
        out.close();
    }

    public String getPath() {
        return f.getAbsolutePath();
    }

    public String getName() {
        return f.getName();
    }

    public String getFileSize() {
        long bytes = f.getTotalSpace();
        double kilobytes = bytes / 1024.00;
        DecimalFormat formatter = new DecimalFormat("0.00");
        return formatter.format(kilobytes);
    }

    public int getNumberOfLines() throws FileNotFoundException {
        int numberOfLines = 0;
        Scanner read = new Scanner(f);

        while (read.hasNextLine()) {
            read.nextLine();
            numberOfLines++;
        }

        return numberOfLines;
    }

    public String getFilesAndDirsInPath() {
        String parentPath = f.getParent();
        File directory = new File(parentPath);
        File[] filesList = directory.listFiles();
        ArrayList<String> filesAndDirectories = new ArrayList<String>();
        String result = new String();

        for (int i = 0; i < filesList.length; i++) {
            if (filesList[i].isFile()) {
                filesAndDirectories.add("File: " + filesList[i].getName());
            } else {
                filesAndDirectories.add("Directory: " + filesList[i].getName());
            }
        }
        
        for (int i = 0; i < filesAndDirectories.size(); i++){
            result += filesAndDirectories.get(i) + "\n";
        }
        return result;

    }

    public String getContents() throws FileNotFoundException {
        String contents = "";
        Scanner read = new Scanner(f);

        if (read.hasNextLine()) {
            while (read.hasNextLine()) {
                contents += read.nextLine() + "\n";
            }
        } else {
            return "File is empty";
        }
        return contents;
    }

    public String findMatchingSearch(String searchTerm) throws FileNotFoundException {
        
        ArrayList<String> allVariations = findVariations(searchTerm);
        ArrayList<String> allMatches = new ArrayList<String>();
        ArrayList<String> finalMatches = new ArrayList<String>();
        String allMatchesBlock = new String();
        
       

        for (int i = 0; i < allVariations.size(); i++) {
            allMatches.add(searchString(allVariations.get(i)));
        }
        
        for (int i = 0; i < allMatches.size(); i++){
            allMatchesBlock += allMatches.get(i) + "\n";
        }
        
        allMatches.clear();
        
        Scanner dissolveBlock = new Scanner(allMatchesBlock);
        while(dissolveBlock.hasNextLine()){
            allMatches.add(dissolveBlock.nextLine());
        }
        
        //Eliminate duplicates and order by line.
        finalMatches = orderMatches(allMatches);
        
        String finalMatchesBlock = new String();
        
        for (int i = 0; i < finalMatches.size(); i++){
            finalMatchesBlock += finalMatches.get(i) + "\n";
        }
        return finalMatchesBlock;

    }

    public ArrayList<String> findVariations(String searchTerm) {
        ArrayList<String> allVariations = new ArrayList<String>();
        String lowercaseTerm = searchTerm.toLowerCase();

        if (lowercaseTerm.length() == 0) {
            allVariations.add(searchTerm);
            return allVariations;
        }
        else{
            String shorter = lowercaseTerm.substring(1);

            char lowercaseLeading = lowercaseTerm.charAt(0);
            char uppercaseLeading = lowercaseTerm.toUpperCase().charAt(0);
           
            //Recursion occurs
            ArrayList<String> shorterVariations = findVariations(shorter);

            for (String shorterVariation : shorterVariations) {
                allVariations.add(lowercaseLeading + shorterVariation);
                allVariations.add(uppercaseLeading + shorterVariation);
            }
        
        return allVariations;
        }
    }

    private String searchString(String searchTerm) throws FileNotFoundException {

        ArrayList<String> result = new ArrayList<String>();
        Scanner searcher = new Scanner(f);
        int lineCounter = 1;
        String resultBlock = new String();

        while (searcher.hasNextLine()) {
            String s = searcher.nextLine();
            if (s.contains(searchTerm)) {
                result.add(lineCounter + ": " + s);
            }
            lineCounter++;
        }
        
        for (int i = 0; i < result.size(); i++){
            resultBlock += result.get(i) + "\n";
        }

        return resultBlock;
    }

    private ArrayList<String> orderMatches(ArrayList<String> allMatches) {

        //Create a Set to eliminate duplicates
        Set<String> tempSet = new HashSet<>();
        tempSet.addAll(allMatches);
        allMatches.clear();
        allMatches.addAll(tempSet);

        //Sort with Collections
        Collections.sort(allMatches);

        return allMatches;
    }

}
