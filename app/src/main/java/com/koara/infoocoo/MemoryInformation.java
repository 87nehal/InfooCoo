package com.koara.infoocoo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MemoryInformation {
    TextView memoryTextView;
    ActivityManager activityManager;
    StatFs statFs;
    Handler handler;
    Runnable runnable;

    MemoryInformation(Context context, TextView memoryTextView) {
        this.memoryTextView = memoryTextView;
        this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateMemoryInfo();
                handler.postDelayed(this, 1000); // update every 1 second
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @SuppressLint("SetTextI18n")
    private void updateMemoryInfo() {
        // Get total RAM
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalRAM = (maxMemory == Long.MAX_VALUE ? totalMemory : maxMemory);

        // Get free RAM and RAM in usage
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long freeRAM = memoryInfo.availMem;
        long usedRAM = totalRAM - freeRAM;

        // Get total ROM, free ROM, and used ROM
        long totalROM = statFs.getTotalBytes();
        long freeROM = statFs.getAvailableBytes();
        long usedROM = totalROM - freeROM;

        // Get temp file size
        File tempFile = null;
        long tempFileSize = 0;
        try {
            tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
            tempFileSize = tempFile.length();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }

        // Set text to memoryTextView
        memoryTextView.setText("Total RAM: " + totalRAM + " bytes\n" +
                "Free RAM: " + freeRAM + " bytes\n" +
                "RAM in usage: " + usedRAM + " bytes\n" +
                "Total ROM: " + totalROM + " bytes\n" +
                "Free ROM: " + freeROM + " bytes\n" +
                "Used ROM: " + usedROM + " bytes\n" +
                "Temp file size: " + tempFileSize + " bytes");
    }
}

