package com.koara.infoocoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PHONE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLocationPhonePermission();
        //IDs
        TextView idsTextView = findViewById(R.id.idsTextView);
        new DeviceIdInformation(this,idsTextView);
        //Sensor
        TextView sensorTextView = findViewById(R.id.sensorTextView);
        new SensorInformation(this,sensorTextView);
        //Camera
        TextView cameraTextView = findViewById(R.id.cameraTextView);
        new CameraInformation(this,cameraTextView);
        //Location
        TextView locationTextView = findViewById(R.id.locationTextView);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        new LocationInformation(this,locationTextView,locationManager);
        //Battery
        TextView batteryTextView = findViewById(R.id.batteryTextView);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(new BatteryInformation(batteryTextView), filter);
        //Device
        TextView deviceTextView = findViewById(R.id.deviceTextView);
        new DeviceInformation(this,deviceTextView);
        //Memory
        TextView memoryTextView = findViewById(R.id.memoryTextView);
        new MemoryInformation(this,memoryTextView);
        //CPU
        TextView cpuTextView = findViewById(R.id.cpuTextView);
        new CpuInformation(this,cpuTextView);
        //Network
        TextView networkTextView = findViewById(R.id.networkTextView);
        new NetworkInformation(this,networkTextView);
        //Clipboard
        TextView clipboardTextView = findViewById(R.id.clipboardTextView);
        new ClipboardInformation(this,clipboardTextView);
        //Apps
        TextView appsTextView = findViewById(R.id.appsTextView);
        new InstalledAppInformation(this,appsTextView);
    }

    private void requestLocationPhonePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            }, REQUEST_LOCATION_PHONE_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            }, REQUEST_LOCATION_PHONE_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PHONE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q
                        || grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
                    // Permissions granted
                } else {
                    // Permissions denied
                }
                break;
        }
    }
}