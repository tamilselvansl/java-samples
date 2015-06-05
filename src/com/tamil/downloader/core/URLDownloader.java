package com.tamil.downloader.core;

import com.tamil.utils.Utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description : Class to download given URL set
 *
 */

public class URLDownloader {

    private String downloadFolder;
    private boolean RETRY_MODE = true;
    private Collection<String> urls, failedUrls, successfulUrls;
    protected int RETRY_COUNT = 4, THREAD_POOL_SIZE = 5;
    private volatile long OVER_ALL_BYTES_DOWNLOADED = 0;

    public URLDownloader() {
        this.failedUrls = new ArrayList<String>();
        this.successfulUrls = new ArrayList<String>();
    }

    public URLDownloader(Collection<String> urls, String downloadFolder) {
        this();
        this.urls = urls;
        this.downloadFolder = downloadFolder;
    }

    public void setUrls(Collection<String> urls) {
        this.urls = urls;
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    public void setThreadPoolSize(int poolSize) {
        this.THREAD_POOL_SIZE = poolSize;
    }

    public void downloadUrls() {
        this.THREAD_POOL_SIZE = urls.size() > THREAD_POOL_SIZE ? THREAD_POOL_SIZE : urls.size();
        debug("Thread Pool Size : " + this.THREAD_POOL_SIZE);
        debug("Downloading folder : " + downloadFolder);
        File tmpFile = new File(downloadFolder);
        if (!tmpFile.exists()) {
            debug("Download Folder \"" + downloadFolder + "\" is not available creating new one");
            tmpFile.mkdir();
        }
        debug("URL's needs to be downloaded [" + urls.size() + "] : " + urls);
        Long overAllStartTime = System.currentTimeMillis();
        downloadUrls(this.urls);
        debug("Successful Urls : ["+successfulUrls.size()+"] : " + successfulUrls);
        debug("Filed Urls : ["+failedUrls.size()+"] : " + failedUrls);
        debug("Over all time taken to download all the URL's : " + Utils.getElapsedTime(overAllStartTime));
        debug("OVER_ALL_BYTES_DOWNLOADED : " + OVER_ALL_BYTES_DOWNLOADED);
        debug("Application Exited....");
    }

    public void downloadUrls(Collection<String> urls) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        int looper = 1;
        for (String aUrl : urls) {
            if (aUrl.trim().isEmpty()) continue;
            URLDownloaderTask task = new URLDownloaderTask(aUrl);
            task.setTaskId("[" + looper + "/" + urls.size() + "] ");
            executor.submit(task);
            looper++;
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        debug("Finished all threads and checking if there is any failed urls...");
        debug("Failed URLS [" + failedUrls.size() + "]: " + failedUrls);
        if (!failedUrls.isEmpty() && RETRY_MODE && RETRY_COUNT > 0) {
            debug("There are some urls not to be downloaded, retrying the same.");
            this.RETRY_COUNT --;
            this.urls.clear();
            this.urls.addAll(failedUrls);
            this.failedUrls.clear();
            downloadUrls(this.urls);
        }
    }

    private class URLDownloaderTask implements Runnable {
        private String aUrl;
        private String taskId;

        public URLDownloaderTask(String url) {
            this.aUrl = url;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public void run() {
            try {
                String aUrlTemp = URLDecoder.decode(aUrl, "UTF-8");
                debug("URL added for download : " + aUrlTemp);
                HttpURLConnection conn = (HttpURLConnection) new URL(aUrl).openConnection();
                conn.connect();
                // Check if the request is handled successfully
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Long startTime = System.currentTimeMillis();
                    long contentLength = conn.getContentLength();
                    InputStream inStream = conn.getInputStream();
                    String destinationFile = aUrlTemp.substring(aUrlTemp.lastIndexOf("/"), aUrlTemp.length());
                    destinationFile = downloadFolder + destinationFile;
                    File destFile = new File(destinationFile);
                    if (destFile.exists()) {
                        long destFileSize = destFile.length();
                        if (contentLength == destFileSize) {
                            debug("[" + aUrlTemp + "] Source and destination files are in the same size, no need to download again.");
                            return;
                        }
                        destFile.delete();
                    }
                    OutputStream outstream = new FileOutputStream(destFile);
                    long numWritten = 0;
                    int len, currentPercentage, prevPercentage = 0;
                    byte[] buffer = new byte[2048]; // 2097152 => 2MB
                    debug("[" + aUrlTemp + "] Downloading URL..... ");
                    while ((len = inStream.read(buffer)) > 0) {
                        outstream.write(buffer, 0, len);
                        numWritten += len;
                        currentPercentage = (int)((numWritten * 100) / contentLength);
                        if (currentPercentage >= prevPercentage + 10) {
                            prevPercentage = currentPercentage;
                            debug("[" + aUrlTemp + "] " + prevPercentage + "% (" + numWritten + "/" + contentLength + ") Completed...");
                        }
                    }
                    OVER_ALL_BYTES_DOWNLOADED += numWritten;
                    inStream.close();
                    outstream.close();
                    debug("[" + aUrlTemp + "] Written file size : " + numWritten + " bytes");
                    if (contentLength == numWritten) {
                        debug("[" + aUrlTemp + "] File has been written successfully...");
                    } else {
                        debug("[" + aUrlTemp + "] File not written correctly, may be corrupted...");
                        failedUrls.add(aUrl);
                    }
                    debug("[" + aUrlTemp + "] Download Completed, time taken : " + Utils.getElapsedTime(startTime));
                    successfulUrls.add(aUrl);
                } else {
                    debug("[" + aUrlTemp + "] Download Failed due to invalid HTTP status [" + conn.getResponseCode() + "]");
                    failedUrls.add(aUrl);
                }
                conn.disconnect();
            } catch (IOException ioex) {
                debug("Exception occured while downloading from url : " + aUrl);
                failedUrls.add(aUrl);
                ioex.printStackTrace();
            } catch (Exception ex) {
                debug("Exception Occured: " + ex.getMessage());
                failedUrls.add(aUrl);
            }
        }

        private void debug(String msg) {
            System.out.println(this.taskId + msg);
        }
    }

    private void downloadImage() throws IOException { // For Testing purpose
        int startNo = 116;
        int endNo = 0;
        String downloadFolder = "D:\\Downloads\\Images\\" ;
        File aFolder = new File(downloadFolder);
        Collection<String> imageUrls = new TreeSet<String>();
        if (!aFolder.exists()) aFolder.createNewFile();
        while (endNo <= startNo) {
            String imageUrl = "http:site-name.com//image-0"+startNo+".jpg";
            imageUrls.add(imageUrl);
            startNo --;
        }
        this.downloadFolder = downloadFolder;
        downloadUrls(imageUrls);
    }

    public static void main(String[] args) throws IOException {
        URLDownloader downloader = new URLDownloader();
        downloader.downloadImage();
    }

    protected void debug(String msg) {
        System.out.println("URLDownloader: " + msg);
    }
}
