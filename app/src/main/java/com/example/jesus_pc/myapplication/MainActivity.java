package com.example.jesus_pc.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                long pattern[] = {0, 800, 100};
                vib.vibrate(pattern, 2);

                Toast.makeText(MainActivity.this, "The phone is vibrating", Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

                if(proximitySensor == null) {
                    //no sensor available
                    Toast.makeText(MainActivity.this, "Sensor is not available", Toast.LENGTH_SHORT).show();
                }

                SensorEventListener proximitySensorListener = new SensorEventListener(){
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                            // Detected something nearby
                            Toast.makeText(MainActivity.this, "Close to the sensor", Toast.LENGTH_SHORT).show();
                            getWindow().getDecorView().setBackgroundColor(Color.RED);
                        } else {
                            // Nothing is nearby
                            Toast.makeText(MainActivity.this, "Away from the sensor", Toast.LENGTH_SHORT).show();
                            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
                };

                sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, "chn")
                        .setContentTitle("Alert!! Notification!!")
                        .setContentText("This is the notification content");

                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "chn";
                CharSequence chnName = "Channel";
                int imp = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel notifChn = new NotificationChannel(channelId, chnName, imp);
                nm.createNotificationChannel(notifChn);
                nm.notify(0, notification.build());

            }
        });
    }
}
