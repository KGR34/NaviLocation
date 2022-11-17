package com.varsitycollege.simplelocation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    Button btnGrant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(MainActivity.this, MapActivity.class));
            finish();
            return;
        }

        btnGrant = findViewById(R.id.btn_grant);

        btnGrant.setOnClickListener(view -> Dexter.withContext(MainActivity.this)
               .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                   public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                         startActivity(new Intent(MainActivity.this, MapActivity.class));
                         finish();
                   }

                    @Override
                   public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                            if(permissionDeniedResponse.isPermanentlyDenied()){
                               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Permission Denied")
                                       .setMessage("Permission to access device location is permanently denied. You need to go to settings to allow the permission")
                                        .setNegativeButton("Cancel", null)
                                        .setPositiveButton("OK", (dialog, which) -> {

                                            Intent intent =  new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                           intent.setData(Uri.fromParts("package", getPackageName(), null));

                                        })
                                        .show();
                            } else {
                                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                         permissionToken.continuePermissionRequest();
                   }
                })
                .check());
    }
}