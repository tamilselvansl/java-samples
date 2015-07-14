package com.tamil.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class ContentSorter {

    private String NEWLINE = "\n";
    private void doProcess() {
        readInputFile("C:\\1.txt");
    }

    private void readInputFile(String FileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileName));
            String readLine = "";            
            Set<String> contents = new TreeSet<String>();
            while ((readLine = reader.readLine()) != null) {
                contents.add(readLine);
            }
            for (String aString : contents) {
                System.out.println(aString);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ContentSorter sorter = new ContentSorter();
        sorter.doProcess();
    }
}
