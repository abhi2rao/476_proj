package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity extends AppCompatActivity {

    private TextView activitiesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activities);

        // Handle window insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activities), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the TextView where the activities or location data will be displayed
        activitiesText = findViewById(R.id.activitiesText);

        // Retrieve the location data passed from LocationActivity
        String location = getIntent().getStringExtra("location");

        // Display the location data
        if (location != null) {
            activitiesText.setText("Activities near: " + location);
        } else {
            activitiesText.setText("No location provided");
        }

        // Button that returns the user to the home page
        Button buttonNavigate1 = findViewById(R.id.returnHome);
        buttonNavigate1.setOnClickListener(view -> {
            Intent intent = new Intent(Activity.this, com.example.assignment2.MainActivity.class);
            startActivity(intent);
        });

        // Button that returns the user to the location selection page
        Button buttonNavigate2 = findViewById(R.id.Changeloco);
        buttonNavigate2.setOnClickListener(view -> {
            Intent intent = new Intent(Activity.this, LocationActivity.class);
            startActivity(intent);
        });
    }
}
