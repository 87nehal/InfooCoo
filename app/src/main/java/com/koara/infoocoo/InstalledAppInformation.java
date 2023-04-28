package com.koara.infoocoo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import java.util.List;

public class InstalledAppInformation {
    public InstalledAppInformation(Context context, TextView appsTextView) {
        StringBuilder sb=new StringBuilder("");
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            sb.append(packageInfo.packageName+"\n");
        }
        appsTextView.setText(sb.toString());
    }
}
