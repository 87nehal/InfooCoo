package com.koara.infoocoo;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import android.widget.TextView;

public class CameraInformation {
    CameraInformation(Context context,TextView cameraTextView){
        CameraManager cameraManager = (CameraManager)context.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            if (cameraIds.length > 0) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraIds[0]);
                StreamConfigurationMap streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Size[] outputSizes = streamConfigurationMap.getOutputSizes(ImageFormat.JPEG);
                double megaPixels = (double) (outputSizes[0].getWidth() * outputSizes[0].getHeight()) / 1000000.0;
                float aperture = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES)[0];
                cameraTextView.setText(String.format("Megapixels: %.1f MP \nAperture: f/%.2f", megaPixels, aperture));
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        cameraTextView.setText(String.format("Megapixels: N/A \nAperture: N/A"));
    }
}
