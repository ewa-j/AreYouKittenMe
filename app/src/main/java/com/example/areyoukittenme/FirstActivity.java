package com.example.areyoukittenme;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        int score = getIntent().getExtras().getInt("score");
        int hp = getIntent().getExtras().getInt("hp");

        findViewById(R.id.startLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, MazeActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("hp", hp);
                startActivity(intent);
            }
        });
    }
}