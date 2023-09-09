package com.bryle_sanico.gui_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Intent CallMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CallMain = new Intent(this, MainActivity.class);
        // startActivity();
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                } finally {
                    startActivity(CallMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}