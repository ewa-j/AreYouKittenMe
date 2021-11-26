package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class InstructionActivity extends AppCompatActivity {

    MediaPlayer play_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        play_theme = MediaPlayer.create(this, R.raw.meow);

        findViewById(R.id.startLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (play_theme != null) {
                    play_theme.start();
                }
                startActivity(new Intent(InstructionActivity.this, AquariumInstructionsActivity.class));
            }
        });
    }
}