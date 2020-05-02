package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int time;
    SeekBar seekBar;
    TextView textView;
    Boolean isActive =false;
    Button button;
    CountDownTimer timer;

    public void resetTimer(){
        textView.setText("00:30");
        button.setText("Go!!");
        seekBar.setProgress(30);
        timer.cancel();
        seekBar.setEnabled(true);
        isActive = false;
    }

    public void start(View view){

        if(isActive){
            resetTimer();
        }
        else {

            isActive = true;
            seekBar.setEnabled(false);

            button.setText("STOP!");

            //Log.i("Seconds left are", String.valueOf(time));
            int time_left = time;
            timer = new CountDownTimer(seekBar.getProgress() * 1000 + 150, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateText((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.air);
                    mp.start();
                    resetTimer();
                }
            }.start();
        }
        //seekBar.setEnabled(true);
        //button.setText("Go!!");
    }

    public void updateText(int time){
        int min = time/60;
        int sec = time%60;
        String s = String.valueOf(sec);
        if(sec<=9){
            s= "0" + s;
        }
        textView.setText(String.valueOf(min) + ":" + s);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //int time;
                if(progress<10){
                    seekBar.setProgress(10);
                    textView.setText("00:10");
                }
                else{
                    time=progress;
                    updateText(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
