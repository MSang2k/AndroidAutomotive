package com.example.getdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private GY521Sensor sensor;
    private TextView sensorDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorDataTextView = findViewById(R.id.sensor_data_text_view);

        try {
            sensor = new GY521Sensor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    final double[] data = sensor.readData();

                    // Update the UI on the main thread
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String text = "Sensor Data: X=" + data[0] + ", Y=" + data[1] + ", Z=" + data[2];
                            sensorDataTextView.setText(text);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000); // Read sensor data every 1 second
    }
}