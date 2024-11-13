package com.example.assignment2; // Change this to your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment2.DatabaseHelper;
import com.example.assignment2.R;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    Button startAdventureButton;
    DatabaseHelper databaseHelper;
    TextView sensorDataText;  // Assuming you have a TextView for sensor data
    private boolean isLoggedIn = false; // Flag to track login status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);

        // Add a test user, if not already added
        addTestUser();

        // Bind the EditTexts for username and password
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        // Bind the Button and TextView from your layout
        startAdventureButton = findViewById(R.id.StartMyAdventure);
        sensorDataText = findViewById(R.id.sensorData);  // Assuming you have a TextView for sensor data

        // Set up the login button event
        startAdventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyLogin(); // Directly call verifyLogin which handles navigation on success
            }
        });

        // Other initializations
        initializeOtherComponents();
    }

    private void addTestUser() {
        // Check if the user already exists to avoid duplicates
        if (!databaseHelper.checkUser("user1", "cse476")) {
            databaseHelper.addUser("user1", "cse476");
        }
    }


    private void verifyLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check the credentials against the database
        if (databaseHelper.checkUser(username, password)) {
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            navigateToLocationActivity();

        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    // Include other methods that are part of your MainActivity
    private void initializeOtherComponents() {
        // For example, setting up sensors, loading data, etc.
        // This is a placeholder for any other initializations you have
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Include any actions that need to be performed when the app resumes
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Handle pauses, like releasing resources
    }

    private void navigateToLocationActivity() {
        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
        startActivity(intent);
    }

}
