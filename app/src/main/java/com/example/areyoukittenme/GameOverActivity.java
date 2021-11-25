package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView tvScore;
    MediaPlayer gameover_theme;
    MediaPlayer play_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        play_theme = MediaPlayer.create(this, R.raw.meow);
        gameover_theme = MediaPlayer.create(this, R.raw.gameover);
        gameover_theme.setLooping(true);
        if (gameover_theme != null) {
            gameover_theme.start();
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
                startActivity(new Intent(GameOverActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameover_theme != null) {
            gameover_theme.stop();
            gameover_theme.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameover_theme != null) {
            gameover_theme.stop();
            gameover_theme.release();
        }
    }
}