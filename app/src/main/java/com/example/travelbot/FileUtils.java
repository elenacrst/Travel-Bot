package com.example.travelbot;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    public static void storeAiml(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    101);
            return;

        }

        AssetManager assets = context.getResources().getAssets();
        File robotPhoneDir = new File(Environment.getExternalStorageDirectory().toString() + "/robot/bots/Robot");
        robotPhoneDir.mkdirs();
        if (robotPhoneDir.exists()) {
            try {
                for (String dir : assets.list("TravelBot")) {
                    File subdir = new File(robotPhoneDir.getPath() + "/" + dir);
                    subdir.mkdirs();
                    for (String file : assets.list("TravelBot/" + dir)) {
                        InputStream in ;
                        OutputStream out;
                        in = assets.open("TravelBot/" + dir + "/" + file);
                        out = new FileOutputStream(robotPhoneDir.getPath() + "/" + dir + "/" + file);
                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/robot";
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();

    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
