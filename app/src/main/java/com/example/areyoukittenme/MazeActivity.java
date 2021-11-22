package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MazeActivity extends AppCompatActivity {

    int hp = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

//        int healthPoints = deriveHealthPoints();
//        String message = "HP " + hp;
        TextView hpTextView = (TextView) findViewById(R.id.hp);
        hpTextView.setText("HP " + hp);


        findViewById(R.id.win).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MazeActivity.this, WinActivity.class));
            }
        });

        findViewById(R.id.lose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MazeActivity.this, GameOverActivity.class));
            }
        });
    }

//    private int GetHPPercentage(int currentHealth, int maxHealth) {
//        return currentHealth / maxHealth * 100;
//
//        int maxHealth = 50
//        int currentHealth = 50
//        health
//    }

}