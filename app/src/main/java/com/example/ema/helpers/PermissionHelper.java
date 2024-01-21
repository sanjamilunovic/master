package com.example.ema.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {
    public static void checkAndSetPermissions(Activity activity, String[] permissions, int requestCode) {
        if (!checkPermissions(activity, permissions))
            requestPermissions(activity, permissions, requestCode);
    }

    public static boolean checkPermissions(Context context, String[] permissions) {
        boolean allPermissionsAvailable = true;
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                allPermissionsAvailable = false;
                break;
            }
        }

        return allPermissionsAvailable;
    }

    public static boolean shouldRequestPermissionRationale(Activity activity, String permission) {
        int hasPermission = ContextCompat.checkSelfPermission(activity, permission);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= 23) {
                return !activity.shouldShowRequestPermissionRationale(permission);
            }
            return false;
        }
        return false;
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static String[] getPicturePermissions() {
        if (Build.VERSION.SDK_INT < 33)
            return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        else
            return new String[]{Manifest.permission.CAMERA};

    }

}
