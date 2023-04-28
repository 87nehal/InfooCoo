package com.koara.infoocoo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

public class DeviceIdInformation {
    Context context;

    public DeviceIdInformation(Context context, TextView idsTextView) {
        this.context = context;
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(Build.ID).append("\n");
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            builder.append("Serial: ").append(Build.getSerial()).append("\n");
            builder.append("IMEI: ").append(getIMEI()).append("\n");
            builder.append("IMSI: ").append(getIMSI()).append("\n");
            builder.append("SIM Serial Number: ").append(getSimSerialNumber()).append("\n");
            builder.append("Phone Number: ").append(getPhoneNumber()).append("\n");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            builder.append("SKU: ").append(Build.SKU).append("\n");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.append("Advertising ID: ").append(getAdvertisingId()).append("\n");
        }
        builder.append("Android ID: ").append(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)).append("\n");
        builder.append("Build Version: ").append(Build.VERSION.RELEASE).append("\n");
        builder.append("Build Version Code Name: ").append(Build.VERSION.CODENAME).append("\n");
        builder.append("Build Type: ").append(Build.TYPE).append("\n");
        builder.append("Build Tags: ").append(Build.TAGS).append("\n");
        builder.append("Build User: ").append(Build.USER).append("\n");
        builder.append("Build Host: ").append(Build.HOST);
        idsTextView.setText(builder.toString());
    }

    // Get IMEI number
    private String getIMEI() {
        String imei = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                imei = telephonyManager.getImei();
            }
        } else {
            imei = Build.SERIAL;
        }
        return imei;
    }

    // Get IMSI number
    private String getIMSI() {
        String imsi = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                imsi = telephonyManager.getSubscriberId();
            }
        }
        return imsi;
    }

    // Get SIM serial number
    private String getSimSerialNumber() {
        String simSerialNumber = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                simSerialNumber = telephonyManager.getSimSerialNumber();
            }
        }
        return simSerialNumber;
    }

    // Get phone number
    private String getPhoneNumber() {
        String phoneNumber = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return "";
                }
                phoneNumber = telephonyManager.getLine1Number();
            }
        }
        return phoneNumber;
    }
    private String getAdvertisingId() {
        String advertisingId = "";
        try {
            AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            advertisingId = adInfo.getId();
        } catch (Exception e) {
            Log.e("DeviceIdInformation", "Failed to retrieve advertising ID", e);
        }
        return advertisingId;
    }

}
