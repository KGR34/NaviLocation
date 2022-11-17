package com.varsitycollege.simplelocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity  {

    private static String measurement;
    private DatabaseReference mDatabase;
    Button buttonSaveSettings;
    Button btnShare;
    TextView savedSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //-- firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //-- button settings
        buttonSaveSettings = findViewById(R.id.btnSaveSettings);
        btnShare = findViewById(R.id.btnShare);

        //-- Text view
        savedSet = findViewById(R.id.saved_settings);

        //--declare spinner object
        Spinner spinner = (Spinner) findViewById(R.id.spinnerMetrics);

        //-- spinner
        List<String> settings = new ArrayList<>();
        settings.add("Kilometeres");
        settings.add("Miles");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,settings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //-- bnutton on click
        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedItem = spinner.getSelectedItem().toString();

                //-- write to firebase
                mDatabase.child("Settings").child(GlobalUser.replaceID(GlobalUser.userID)).setValue(selectedItem);
                Toast.makeText(SettingsActivity.this, "Settings saved to firebase", Toast.LENGTH_SHORT).show();

                //-- read from data base
                FirebaseDatabase.getInstance().getReference().child("Settings").child(GlobalUser.replaceID(GlobalUser.userID)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ss : snapshot.getChildren()) {
                            savedSet.setText(ss.getValue().toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body="Download this App";
                String Sub="SimpleLocation.com";
                intent.putExtra(Intent.EXTRA_TEXT,Body);
                intent.putExtra(Intent.EXTRA_TEXT,Sub);
                startActivity(Intent.createChooser(intent,"Share Using"));
            }
        });



    }
}

/*
code attribution
author: Android Developers
code available at: https://developer.android.com/develop/ui/views/components/spinner
 */