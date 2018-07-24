package com.example.administrator.dssproject.Utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.DataBase.ScenarioItem;
import com.example.administrator.dssproject.SDCard.CheckForSDCard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Supporter {

    public static final String downloadDirectory = "DSSDownloadData";
//    public static final String downloadVideo = "http://androhub.com/demo/demo.mp4";


    public static String savingDataToSDCard(Context context, File outputFile, File apkStorage, String downloadUrl, String downloadFileName, String downloadTailFileName ){
        String newUrl = "";
        String pathname = Environment.getExternalStorageDirectory() + "/"
                + Supporter.downloadDirectory;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.connect();

            if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                        + " " + c.getResponseMessage());

            }

            if (new CheckForSDCard().isExternalStorageWritable()) {
                apkStorage = new File(
                        context.getExternalFilesDir(null) + "/"
                                + Supporter.downloadDirectory);

            } else
                Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e(TAG, "Directory Created.");
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            downloadFileName = downloadFileName + " " + timestamp;
            downloadFileName = downloadFileName.replace(" ","_").trim();

            outputFile = new File(apkStorage, downloadFileName + downloadTailFileName);//Create Output file in Main File

            //Create New File if not present
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                Log.e(TAG, "File Created");
            }

            Log.e("File download",outputFile.getAbsolutePath());
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
            outputFile = null;
            Log.e(TAG, "Download Error Exception " + e.getMessage());
        }
//        newUrl = pathname + "/" + downloadFileName + downloadTailFileName;

        return outputFile.getAbsolutePath();
    }


    /*public static List<ScenarioItem> scenarioItemsSortByDisplayOrder(List<ScenarioItem> scenarioItemList){
        Collections.sort(scenarioItemList);
        return scenarioItemList;
    }

    public static List<PlaylistItem> playlistItemsSortByDisplayOrder(List<PlaylistItem> playlistItemList){
        Collections.sort(playlistItemList);
        return playlistItemList;
    }*/

}
