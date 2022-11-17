package com.varsitycollege.simplelocation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AppPermissions extends AppCompatActivity {


    public static boolean hasPermissions(Context context, String... permissions)
    {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M && context != null && permissions != null);
        {
            for (String permission : permissions)
            {
                if(ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }

        return true;
    }
}

