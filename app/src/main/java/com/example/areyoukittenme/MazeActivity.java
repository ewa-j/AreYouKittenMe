package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MazeActivity extends AppCompatActivity {
    private static final long COUNTDOWN_IN_MILLIS = 120000;
    private ColorStateList textColourDefaultCd;

    private TextView textViewCountDown;

    private CountDownTimer countDownTimer;
    private Long timeLeftInMillis;

    Timer timer;

    public static int hp = 50;
//    public String health = getDecreasedHp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

//        int healthPoints = deriveHealthPoints();
//        String message = "HP " + hp;

        TextView hpTextView = (TextView) findViewById(R.id.hp);
        hpTextView.setText("HP " + hp);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MazeActivity.this, GameOverActivity.class);
                startActivity(intent);
            }
        }, 120000);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        textColourDefaultCd = textViewCountDown.getTextColors();

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
    }

//    private int GetHPPercentage(int currentHealth, int maxHealth) {
//        return currentHealth / maxHealth * 100;
//
//        int maxHealth = 50
//        int currentHealth = 50
//        health
//    }
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0L;
                updateCountDownText();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis/1000) / 60;
        int seconds = (int) (timeLeftInMillis/1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColourDefaultCd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
          countDownTimer.cancel();
        }
    }

    public int getHp() {
        Intent intent = new Intent(MazeActivity.this, MazeView.class);
        startActivity(intent);
        return hp;
    }

    public void setHp(int newHp) {
//        Intent intent = new Intent(MazeActivity.this, MazeView.class);
//        startActivity(intent);
        hp = newHp;
    }

//    public void decreseHp() {
//        if (MazeView.enemyCollision) {
//            setHp(getHp()-5);
//        }
//    }
//    public String getDecreasedHp() {
//        Intent intent = getIntent();
//        String hp = intent.getStringExtra("hp");
//        return hp;
//    }

}