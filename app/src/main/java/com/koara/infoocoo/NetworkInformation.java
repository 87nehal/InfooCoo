package com.koara.infoocoo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class NetworkInformation {
    private TextView networkTextView;
    private Context context;
    private Handler handler;
    private long previousRxBytes;
    private long previousTxBytes;

    public NetworkInformation(Context context,TextView networkTextView) {
        this.networkTextView = networkTextView;
        this.context = context;
        this.handler = new Handler();
        this.previousRxBytes = TrafficStats.getTotalRxBytes();
        this.previousTxBytes = TrafficStats.getTotalTxBytes();
        updateNetworkInfo();
    }

    private void updateNetworkInfo() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                String carrierName = telephonyManager.getNetworkOperatorName();
                String ipAddress = getIPAddress();
                String macAddress = getMacAddress(wifiInfo);
                String networkType = getNetworkType(activeNetwork);
                String networkSpeed = getNetworkSpeed();

                String networkInfoText = "Carrier: " + carrierName + "\n"
                        + "IP Address: " + ipAddress + "\n"
                        + "MAC Address: " + macAddress + "\n"
                        + "Network Type: " + networkType + "\n"
                        + "Network Speed: " + networkSpeed;

                networkTextView.setText(networkInfoText);

                // Schedule the next update
                updateNetworkInfo();
            }
        }, 1000); // Update every 1000 milliseconds (1 second)
    }

    private String getIPAddress() {
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : networkInterfaces) {
                List<InetAddress> inetAddresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress inetAddress : inetAddresses) {
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private String getMacAddress(WifiInfo wifiInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface networkInterface : networkInterfaces) {
                    if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                        byte[] macBytes = networkInterface.getHardwareAddress();
                        if (macBytes != null) {
                            StringBuilder macAddressBuilder = new StringBuilder();
                            for (byte b : macBytes) {
                                macAddressBuilder.append(String.format("%02X:", b));
                            }
                            if (macAddressBuilder.length() > 0) {
                                macAddressBuilder.deleteCharAt(macAddressBuilder.length() - 1);
                            }
                            return macAddressBuilder.toString();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return "N/A";
        } else {
            return wifiInfo.getMacAddress();
        }
    }

    private String getNetworkType(NetworkInfo activeNetwork) {
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WiFi";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return "Mobile";
            }
        }
        return "N/A";
    }

    private String getNetworkSpeed() {
        long currentRxBytes = TrafficStats.getTotalRxBytes();
        long currentTxBytes = TrafficStats.getTotalTxBytes();
        long rxBytes = currentRxBytes - previousRxBytes;
        long txBytes = currentTxBytes - previousTxBytes;
        previousRxBytes = currentRxBytes;
        previousTxBytes = currentTxBytes;

        return rxBytes + " B/s (down), " + txBytes + " B/s (up)";
    }
}
