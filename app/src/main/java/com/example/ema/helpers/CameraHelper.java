package com.example.ema.helpers;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraHelper {
    public static String getOutputMediaFile(Activity activity) {
        try {
            File mediaStorageDir = null;
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mediaStorageDir = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Ema");
            } else {
                mediaStorageDir = new File(activity.getFilesDir(), "Ema");
            }
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            return mediaStorageDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg";

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
