package com.koara.infoocoo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class SensorInformation implements SensorEventListener {

        private SensorManager sensorManager;
        private Map<String, Float> sensorValues;
        private TextView sensorReadingsTextView;
        private Handler handler;
        private Runnable updateSensorReadingsRunnable;

        public SensorInformation(Context context,TextView sensorReadingsTextView) {
            this.sensorReadingsTextView = sensorReadingsTextView;
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            sensorValues = new HashMap<>();
            registerSensor(Sensor.TYPE_GYROSCOPE);
            registerSensor(Sensor.TYPE_PRESSURE);
            registerSensor(Sensor.TYPE_ACCELEROMETER);
            registerSensor(Sensor.TYPE_ROTATION_VECTOR);
            registerSensor(Sensor.TYPE_PROXIMITY);
            registerSensor(Sensor.TYPE_LIGHT);
            registerSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
            registerSensor(Sensor.TYPE_GRAVITY);
            registerSensor(Sensor.TYPE_ALL);

            handler = new Handler();
            updateSensorReadingsRunnable = new Runnable() {
                @Override
                public void run() {
                    sensorReadingsTextView.setText(getSensorReadings());
                    handler.postDelayed(this, 1000); // Update every 1000 milliseconds (1 second)
                }
            };
            handler.post(updateSensorReadingsRunnable);
        }

        public void registerSensor(int sensorType) {
            Sensor sensor = sensorManager.getDefaultSensor(sensorType);
            if (sensor != null) {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        public String getSensorReadings() {
            StringBuilder sensorReadings = new StringBuilder("");
            int numEntries = sensorValues.entrySet().size();
            int count = 0;
            for (Map.Entry<String, Float> entry : sensorValues.entrySet()) {
                sensorReadings.append(entry.getKey()).append(": ").append(entry.getValue());
                if (++count < numEntries) {
                    sensorReadings.append("\n");
                }
            }
            return sensorReadings.toString();
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;
            float[] values = event.values;
            if (sensor != null && values != null) {
                String sensorName = sensor.getName();
                for (int i = 0; i < values.length; i++) {
                    sensorValues.put(sensorName + "[" + i + "]", values[i]);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
