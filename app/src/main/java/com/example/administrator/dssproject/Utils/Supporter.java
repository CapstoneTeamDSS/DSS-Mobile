package com.example.administrator.dssproject.Utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.MainThread;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.MediaSrcDAO_Impl;
import com.example.administrator.dssproject.MainActivity;
import com.example.administrator.dssproject.SDCard.CheckForSDCard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Supporter {

    private static final String downloadDirectory = "DSSDownloadData";
//    public static final String downloadVideo = "http://androhub.com/demo/demo.mp4";


    public static String saveDataToSDCard(Context context, String downloadUrl, String downloadFileName, String downloadTailFileName) {
        String newUrl;
        String pathname = Environment.getExternalStorageDirectory() + "/" + downloadDirectory;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();
            if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());
            }

            File apkStorage;
            if (CheckForSDCard.isExternalStorageWritable()) {
                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/" + Supporter.downloadDirectory);
            } else {
                Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
                return null;
            }

            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e(TAG, "Directory Created.");
            }
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            downloadFileName = downloadFileName + " " + timestamp;
            downloadFileName = downloadFileName.replace(" ", "").trim();
            downloadFileName = downloadFileName.replace(":", "").trim();
            downloadFileName = downloadFileName.replace("-", "").trim();
            File outputFile = new File(apkStorage, downloadFileName + downloadTailFileName);
            //Create New File if not present
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                Log.e(TAG, "File Created");
            }

            Log.e("File download", outputFile.getAbsolutePath());
//            int n = numberOfFiles(pathname);

            /*if(n > 50){
                String localUrl = getLocalUrlOfLongestUsedFile();
                deleteLongestUsedFile(pathname, localUrl);
            }*/

            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
            InputStream is = c.getInputStream();//Get InputStream for connection
            byte[] buffer = new byte[1024];//Set buffer type
            int len1 = 0;//init length
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);//Write new file
            }
            //Close all connection after doing task
            fos.close();
            is.close();
        } catch (Exception e) {
            //Read exception if something went wrong
            e.printStackTrace();
            Log.e(TAG, "Download Error Exception " + e.getMessage());
        }
        newUrl = pathname + "/" + downloadFileName + downloadTailFileName;
        return newUrl;
    }


    public static String getFileName(String path) {
        int lastSeparator = path.lastIndexOf("/");
        return path.substring(lastSeparator + 1);
    }

    public static String getPathFolder(String path) {
        int lastSeparator = path.lastIndexOf("/");
        return path.substring(0, lastSeparator);
    }

    private static int numberOfFiles(String filePath) {
        File dir = new File(filePath);
        return  dir.listFiles().length;
    }

    private static long longestModified(String filePath){
        File dir = new File(filePath);
        File fileList[] = dir.listFiles();
        long longestModified = fileList[0].lastModified();
        for(File f : fileList){
            if(f.lastModified() < longestModified){
                longestModified = f.lastModified();
            }
        }
        return longestModified;
    }

    private static void deleteFile(String filePath){
        long longestModified = longestModified(filePath);
        File dir = new File(filePath);
        File fileList[] = dir.listFiles();
        for(File f : fileList){
//            f.getAbsolutePath();
            if(f.lastModified() == longestModified){
                f.delete();
                Log.e(TAG, "Delete Sucess");
            }
        }
    }

    private static String getLocalUrlOfLongestUsedFile(){
        List<MediaSrc> mediaSrcList = MainActivity.myAppDatabase.mediaSrcDAO().getMediaSrc();
        long lastUsed = mediaSrcList.get(0).getLastUsed();
        String localUrl = "";
        for (MediaSrc mediaSrc : mediaSrcList) {
            if(mediaSrc.getLastUsed() != 0 && mediaSrc.getLastUsed() < lastUsed){
                lastUsed = mediaSrc.getLastUsed();
                localUrl = MainActivity.myAppDatabase.mediaSrcDAO().getUrlLocalByLastUsed(lastUsed);
            }
        }
        return localUrl;
    }

    private static void deleteLongestUsedFile(String filePath, String urlLocal){
        File dir = new File(filePath);
        File fileList[] = dir.listFiles();
        for(File f : fileList) {
            if(f.getAbsolutePath() == urlLocal){
                f.delete();
                MainActivity.myAppDatabase.mediaSrcDAO().deleteMediaSrcByLocalUrl(urlLocal);
            }
        }
    }

}