package com.example.assignment2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private EditText locationInput;  // EditText for capturing user input
    private FusedLocationProviderClient fusedLocationClient;  // FusedLocationProviderClient to access location data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.location);

        // Request location permissions if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, get location
            getCurrentLocation();
        }

        // Handle system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.locationpage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the EditText and Button
        locationInput = findViewById(R.id.location);
        Button buttonNavigate = findViewById(R.id.search);

        // Set up click listener for the search button
        buttonNavigate.setOnClickListener(view -> {
            String location = locationInput.getText().toString();  // Get the input text

            // Validate the input
            if (!location.isEmpty()) {
                // Navigate to the next activity and pass the location data
                Intent intent = new Intent(LocationActivity.this, Activity.class);
                intent.putExtra("location", location);  // Pass the user input to the next activity
                startActivity(intent);
            } else {
                // Show a message if no location was entered
                Toast.makeText(LocationActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Handle location permission result
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the current location
                getCurrentLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied. Unable to access location data.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to get the current location using FusedLocationProviderClient
    private void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    // Get latitude and longitude
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Display the location in a Toast (you can also pass this to the next activity)
                    Toast.makeText(this, "Location: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();

                    // You can also pass the latitude and longitude to the next activity if needed
                    /*
                    Intent intent = new Intent(LocationActivity.this, Activity.class);
                    intent.putExtra("location", "Lat: " + latitude + ", Long: " + longitude);
                    startActivity(intent);
                    */
                } else {
                    Toast.makeText(this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
