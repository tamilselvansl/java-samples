package com.tamil.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description : Rename files and copy it to another folder aswell
 *
 */

public class RenameFiles implements FilenameFilter {
    private int count = 0;
    private String fileNamePrefix = "Photo";
    private String sourceFolderPath = "C:\\Source_folder";
    private String destFolderPath = "D:\\Renamed_Files_Folder\\";

    private String getDynaFileName() {
        count = count + 1;
        return destFolderPath + fileNamePrefix + "_" + count + ".jpg";
    }

    private void renameFiles() throws IOException {

        File sourcePath = new File(sourceFolderPath);
        if (sourcePath.isDirectory()) {
            File[] files = sourcePath.listFiles(this);
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File name : " + file.getName());
                    File destFile = new File(getDynaFileName());
                    System.out.println("destFile.getAbsolutePath() = " + destFile.getAbsolutePath());
                    destFile.setLastModified(System.currentTimeMillis());

                    file.renameTo(destFile);
                }
            }
        }
    }

    public boolean accept(File dir, String name) {
        return true;
    }

    public static void main(String[] args) throws IOException {
        RenameFiles renameFiles = new RenameFiles();
        renameFiles.renameFiles();

    }
}
