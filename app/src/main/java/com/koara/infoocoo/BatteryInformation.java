package com.koara.infoocoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class BatteryInformation extends BroadcastReceiver {
    private TextView textView;

    public BatteryInformation(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryLevel = level * 100 / (float)scale;

        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        String statusString = "";
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            statusString = "Charging";
        } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            statusString = "Discharging";
        } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
            statusString = "Not Charging";
        } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
            statusString = "Full";
        } else {
            statusString = "Unknown";
        }

        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        String healthString = "";
        if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
            healthString = "Unknown";
        } else if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
            healthString = "Good";
        } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
            healthString = "Overheat";
        } else if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
            healthString = "Dead";
        } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
            healthString = "Over Voltage";
        } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
            healthString = "Unspecified Failure";
        } else if (health == BatteryManager.BATTERY_HEALTH_COLD) {
            healthString = "Cold";
        }

        String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);

        // Get the temperature of the device
        float temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0f;

        // Get the system uptime in hours and minutes
        long uptimeMs = SystemClock.elapsedRealtime();
        long uptimeHrs = TimeUnit.MILLISECONDS.toHours(uptimeMs);
        long uptimeMins = TimeUnit.MILLISECONDS.toMinutes(uptimeMs) % 60;

        String text = String.format("Battery Level: %.0f%%\nStatus: %s\nHealth: %s\nTechnology: %s\nTemperature: %.1f°C\nUptime: %d hours %d minutes", batteryLevel, statusString, healthString, technology, temperature, uptimeHrs, uptimeMins);
        textView.setText(text);
    }
}



