package com.koara.infoocoo;

import static android.content.Context.WINDOW_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.opengl.GLES20;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

public class DeviceInformation {
    DeviceInformation(Context context,TextView deviceInfoTextView){
        String gpuVendor = getGpuInfo(context);
        String gpuModel = GLES20.glGetString(GLES20.GL_RENDERER);
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String cpuVendor = Build.BOARD;
        String cpuModel = Build.HARDWARE;
        String board = Build.BOARD;
        String hardware = Build.HARDWARE;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        int screenDensity = displayMetrics.densityDpi;
        String screenSize = screenWidth + "x" + screenHeight;
        String screenResolution = screenSize + " (" + screenDensity + "dpi)";
        String androidVersion = Build.VERSION.RELEASE;
        int apiLevel = Build.VERSION.SDK_INT;
        String securityPatchLevel = Build.VERSION.SECURITY_PATCH;
        String bootloader = Build.BOOTLOADER;
        String buildId = Build.DISPLAY;
        String javaVm = System.getProperty("java.vm.name");
        String openglEs = GLES20.glGetString(GLES20.GL_VERSION);
        String kernelArchitecture = System.getProperty("os.arch");
        String kernelVersion = System.getProperty("os.version");
        String systemInformation = "Manufacturer: " + manufacturer + "\n" +
                "Model: " + model + "\n" +
                "GPU vendor: " + gpuVendor + "\n" +
                "CPU vendor: " + cpuVendor + "\n" +
                "GPU model: " + gpuModel + "\n" +
                "CPU model: " + cpuModel + "\n" +
                "Board: " + board + "\n" +
                "Hardware: " + hardware + "\n" +
                "Screen size: " + screenSize + "\n" +
                "Screen resolution: " + screenResolution + "\n" +
                "Screen density: " + screenDensity + "dpi\n" +
                "Android version: " + androidVersion + "\n" +
                "API level: " + apiLevel + "\n" +
                "Security patch level: " + securityPatchLevel + "\n" +
                "Bootloader: " + bootloader + "\n" +
                "Build ID: " + buildId + "\n" +
                "Java VM: " + javaVm + "\n" +
                "OpenGL ES: " + openglEs + "\n" +
                "Kernel architecture: " + kernelArchitecture + "\n" +
                "Kernel version: " + kernelVersion;
        deviceInfoTextView.setText(systemInformation);
    }
    private String getGpuInfo(Context context) {
        String renderer = "N/A";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        if (memoryInfo.totalMem >= 3 * 1024 * 1024 * 1024L) {
            renderer = "Adreno";
        } else {
            renderer = "Mali";
        }
        return renderer;
    }
}
