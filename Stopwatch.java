package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton, stopButton;
    private TextView timeDisplay;
    private long startTime = 0L;
    private Handler handler = new Handler();
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.timeDisplay);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTimer();
                    animateButton(startButton);
                    isRunning = true;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    stopTimer();
                    animateButton(stopButton);
                    isRunning = false;
                }
            }
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis() - timeSwapBuff;
        handler.postDelayed(updateTimerThread, 0);
    }

    private void stopTimer() {
        timeSwapBuff += timeInMilliseconds;
        handler.removeCallbacks(updateTimerThread);
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = System.currentTimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timeDisplay.setText(String.format("%02d:%02d:%02d", mins, secs, milliseconds / 10));
            handler.postDelayed(this, 0);
        }
    };

    private void animateButton(Button button) {
        Animation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(200);
        button.startAnimation(anim);
    }
}