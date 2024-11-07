package com.example.assignment2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView sensorDataText;
    private Button startAdventureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the TextView for displaying sensor data
        sensorDataText = findViewById(R.id.sensorData);

        // Find the button and set an OnClickListener
        startAdventureButton = findViewById(R.id.StartMyAdventure);
        startAdventureButton.setOnClickListener(v -> {
            // Create an intent to navigate to LocationActivity
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        // Get the SensorManager and the accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        // Check if the accelerometer is available
        if (accelerometer == null) {
            Toast.makeText(this, "Accelerometer not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the listener for the accelerometer sensor
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener to save battery when not active
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Get the accelerometer readings
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Display the raw sensor data in the TextView
        sensorDataText.setText("X: " + x + " Y: " + y + " Z: " + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes here (optional)
    }
}
