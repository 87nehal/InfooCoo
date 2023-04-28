package com.koara.infoocoo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.Manifest;
import androidx.core.app.ActivityCompat;

import java.util.Locale;
import java.util.TimeZone;

public class LocationInformation  implements LocationListener {
    private TextView locationTextView;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;

    public LocationInformation(Context context,TextView locationTextView, LocationManager locationManager) {
        this.locationTextView = locationTextView;
        this.locationManager = locationManager;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        String locationReadings = "Latitude: " + latitude + "\nLongitude: " + longitude+"\n";
        locationTextView.setText(locationReadings+getDeviceInfo());
    }
    public String getDeviceInfo() {
        String deviceTime = String.valueOf(System.currentTimeMillis());
        String deviceTimeZone = TimeZone.getDefault().getID();
        String deviceLanguage = Locale.getDefault().getLanguage();
        String deviceLocale = Locale.getDefault().toString();

        return "Device Time: "+deviceTime + "\nTimezone: " + deviceTimeZone + "\nLanguage: " + deviceLanguage + "\nLocale: " + deviceLocale;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
