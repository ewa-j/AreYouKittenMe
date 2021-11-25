package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    TextView tvScore;
    MediaPlayer win_theme;
    MediaPlayer play_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        play_theme = MediaPlayer.create(this, R.raw.meow);
        win_theme = MediaPlayer.create(this, R.raw.win);
        win_theme.setLooping(true);
        if (win_theme != null) {
            win_theme.start();
        }

        int score = getIntent().getExtras().getInt("score");
        tvScore = findViewById(R.id.tvScore);
        tvScore.setText("" + score);

        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (play_theme != null) {
                    play_theme.start();
                }
                startActivity(new Intent(WinActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (win_theme != null) {
            win_theme.stop();
            win_theme.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (win_theme != null) {
            win_theme.stop();
            win_theme.release();
        }
    }
}