package com.example.areyoukittenme;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    MediaPlayer play_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        play_theme = MediaPlayer.create(this, R.raw.meow);
        int score = getIntent().getExtras().getInt("score");
        int hp = getIntent().getExtras().getInt("hp");

        findViewById(R.id.startLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (play_theme != null) {
                    play_theme.start();
                }
                Intent intent = new Intent(FirstActivity.this, MazeActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("hp", hp);
                startActivity(intent);
            }
        });
    }
}