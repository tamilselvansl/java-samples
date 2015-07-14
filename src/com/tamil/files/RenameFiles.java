package com.tamil.files;

import com.tamil.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;


public class RenameFiles {

    private Collection<String> failedFiles;
    private String destFolderName = "C:\\DIR_All_Renamed_Files";
    private String srcFolderName = "C:\\DIR_Filed_to_be_renamed";

    private void renameAndRelocateFiles(File[] files, String destFolderName) {
        for (File aFile : files) {
            String newFileName = getNewFileName(aFile.getName());
            System.out.println(aFile.getName() + "-->" + newFileName);
            newFileName = destFolderName + "\\" + newFileName;

            try {
                copyFile(aFile, new File(newFileName));
            } catch (IOException e) {
                e.printStackTrace();
                this.failedFiles.add(newFileName);
            }
        }
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null, destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) source.close();
            if (destination != null) destination.close();
        }
    }

    private File[] getFiles(String directory, boolean includeSubDir) {
        File dir = new File(directory);
        File[] files = new File[10];
        if (dir.exists() && dir.isDirectory()) {
            files = dir.listFiles();
        }
        return files;
    }

    private String getNewFileName(String oldFileName) {
        String extension = oldFileName.split("\\.")[oldFileName.split("\\.").length - 1];

        if (oldFileName.contains("TamilWire.com")) {
            oldFileName = oldFileName.substring(0, oldFileName.indexOf("TamilWire.com"));
            if (oldFileName.trim().endsWith("-")) {
                oldFileName = oldFileName.substring(0, oldFileName.lastIndexOf("-"));
            }
        }
        return oldFileName.trim() + "." + extension;
    }

    private void doProcess() {
        Long overAllStartTime = System.currentTimeMillis();
        this.failedFiles = new ArrayList<String>();
        File[] files = getFiles(srcFolderName, false);
        System.out.println("Number of files retrived: " + files.length);
        File destDir = new File(destFolderName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        renameAndRelocateFiles(files, destFolderName);
        System.out.println("Over all time taken to process : " + Utils.getElapsedTime(overAllStartTime));
        if (!failedFiles.isEmpty()) {
            System.out.println("failedFiles = " + failedFiles);
        }
    }

    public static void main(String[] args) {
        RenameFiles renameFile = new RenameFiles();
        renameFile.doProcess();
    }
}
