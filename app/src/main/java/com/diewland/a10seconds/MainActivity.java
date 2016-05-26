package com.diewland.a10seconds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String TAG = "DIEWLAND";
    private TextView num;

    final static long INTERVAL=1000;
    int running;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = (TextView)findViewById(R.id.num);
        timer = countdown();
    }

    private Timer countdown(){
        running = 0;
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                running += 1;
                displayText("" + running);
                if(running>=10){
                    this.cancel();
                    return;
                }
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, INTERVAL, INTERVAL);
        return timer;
    }

    private void displayText(final String text){
        this.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Log.d(TAG, text);
                num.setText(text);
            }
        });
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "<< cancel >>");
        timer.cancel();
        timer.purge();
        timer = null;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "<< resume >>");
        num.setText("...");
        if(timer == null) {
            timer = countdown();
        }
    }
}
