package com.example.areyoukittenme;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AquariumInstructionsActivity extends AppCompatActivity {

    MediaPlayer play_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquariuminstructions);
        play_theme = MediaPlayer.create(this, R.raw.meow);

        findViewById(R.id.startLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (play_theme != null) {
                    play_theme.start();
                }
                startActivity(new Intent(AquariumInstructionsActivity.this, AquariumActivity.class));
            }
        });
    }
}
