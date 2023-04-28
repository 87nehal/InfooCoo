package com.koara.infoocoo;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CpuInformation {
    private TextView cpuTextView;
    private Context context;
    private Handler handler;

    public CpuInformation(Context context, TextView cpuTextView) {
        this.context = context;
        this.cpuTextView = cpuTextView;
        this.handler = new Handler();
        updateCpuInfo();
    }

    private void updateCpuInfo() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String cpuArchitecture = getCpuArchitecture();
                int numberOfCores = getNumberOfCores();
                String cpuManufacturer = getCpuManufacturer();

                StringBuilder cpuInfoText = new StringBuilder();
                cpuInfoText.append("CPU Architecture: ").append(cpuArchitecture).append("\n")
                        .append("Number of Cores: ").append(numberOfCores).append("\n")
                        .append("CPU Manufacturer: ").append(cpuManufacturer).append("\n");

                for (int i = 0; i < numberOfCores; i++) {
                    String coreFrequency = getCoreFrequency(i);
                    cpuInfoText.append("\nCore ").append(i).append(":\n")
                            .append("Frequency: ").append(coreFrequency).append("\n");
                }

                cpuTextView.setText(cpuInfoText.toString());

                // Schedule the next update
                updateCpuInfo();
            }
        }, 1000); // Update every 1000 milliseconds (1 second)
    }

    private String getCpuArchitecture() {
        return Build.SUPPORTED_ABIS[0];
    }

    private int getNumberOfCores() {
        return Runtime.getRuntime().availableProcessors();
    }
    private String getCpuManufacturer() {
        return (Build.HARDWARE).toString().toUpperCase();
    }

    private String getCoreFrequency(int core) {
        String coreFrequency = "N/A";
        try {
            String coreFreqPath = "/sys/devices/system/cpu/cpu" + core + "/cpufreq/scaling_cur_freq";
            File coreFreqFile = new File(coreFreqPath);
            if (coreFreqFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(coreFreqFile));
                String line = reader.readLine();
                if (line != null) {
                    int frequency = Integer.parseInt(line) / 1000;
                    coreFrequency = frequency + " MHz";
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coreFrequency;
    }
}

