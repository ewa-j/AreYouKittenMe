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

    Timer timer, hpTimer;

//    public static int hp = 50;
    public int hp = MazeView.hp;
//    public String health = getDecreasedHp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

//        int healthPoints = deriveHealthPoints();
//        String message = "HP " + hp;


//        TextView hpTextView = (TextView) findViewById(R.id.hp);
//        hpTextView.setText("HP " + hp);


        // countdown timer
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
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
          countDownTimer.cancel();
        }
//        if (hpTimer != null) {
//            hpTimer.cancel();
        }


//    public void hpText() {
//        // hp timer
//        hpTimer = new Timer();
//        hpTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MazeActivity.this, MazeActivity.class);
//                startActivity(intent);
//            }
//        }, 120000);
//
//        TextView hpTextView = (TextView) findViewById(R.id.hp);
//
//        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
//        startHpTimer();
//    }
//
//    private void startHpTimer() {
//        hpTimer = new hpTimer(timeLeftInMillis, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timeLeftInMillis = millisUntilFinished;
//                updateHpText();
//            }
//
//            @Override
//            public void onFinish() {
//                timeLeftInMillis = 0L;
//                updateHpText();
//            }
//        }.start();
//    }
//
//    private void updateHpText() {
//
//        TextView hpTextView = (TextView) findViewById(R.id.hp);
//        hpTextView.setText("HP " + hp);
//
//
//        if (hp < 10) {
//            textViewCountDown.setTextColor(Color.RED);
//        } else {
//            textViewCountDown.setTextColor(textColourDefaultCd);
//        }
//    }











    public int getHp() {
        Intent intent = new Intent(MazeActivity.this, MazeView.class);
        startActivity(intent);
        return hp;
    }

//    public static void setHp(int newHp) {
////        Intent intent = new Intent(MazeActivity.this, MazeView.class);
////        startActivity(intent);
//        hp = newHp;
//    }

//    public void decreaseHp() {
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