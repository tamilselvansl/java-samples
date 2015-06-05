package com.tamil.downloader.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.TreeSet;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description : Class to download given URL set
 *
 */

public class FilesDownloader extends URLDownloader {

    private String downloadDir = "D:\\Downloads\\";
    private String knownFileName = "D:\\Downloads\\known-urls.txt";

    private Collection<String> readURLsFromFinalUrlsFile(String fileName) {
        Collection<String> urls = new TreeSet<String>();

        File file = new File(fileName);
        if (!file.exists()) return urls;
        BufferedReader fileReader = null;
        try {
            String line;
            fileReader = new BufferedReader(new FileReader(file));
            while ((line = fileReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fileReader) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return urls;
    }

    private void doDownload() {
        debug("Reading download URLS from file " + knownFileName);
        Collection<String> urls = readURLsFromFinalUrlsFile(knownFileName);

        if (urls.isEmpty()) {
            debug("Download URL's list is EMPTY, will not invoke downloader");
            return;
        }
        debug("URL's Size : [" + urls.size() + "] " + urls);
        super.setDownloadFolder(downloadDir);
        super.setUrls(urls);
        super.setThreadPoolSize(10);
        super.downloadUrls();
    }

    public static void main(String[] args) {
        FilesDownloader downloader = new FilesDownloader();
        downloader.doDownload();
    }    
}
