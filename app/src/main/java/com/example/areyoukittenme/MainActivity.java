package com.example.areyoukittenme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    MediaPlayer main_theme;
    MazeView mazeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_theme = MediaPlayer.create(this, R.raw.main_theme);
        if (main_theme != null) {
            main_theme.start();
        }

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AquariumInstructionsActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (main_theme != null) {
            main_theme.stop();
            main_theme.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (main_theme != null) {
            main_theme.stop();
            main_theme.release();
        }
    }
}