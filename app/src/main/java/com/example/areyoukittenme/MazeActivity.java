package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private MazeView mazeView;

    Timer timer;
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        int score = getIntent().getExtras().getInt("score");
        int hp = getIntent().getExtras().getInt("hp");
        tvScore = findViewById(R.id.tvScore);
        tvScore.setText("Score: " + score);
      
        mazeView = findViewById(R.id.mazeView);
        mazeView.score = score;
        mazeView.hp = hp;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mazeView.gameState) {
                    Intent intent = new Intent(MazeActivity.this, GameOverActivity.class);
                    intent.putExtra("score", score);
                    startActivity(intent);
                }
            }
        }, COUNTDOWN_IN_MILLIS);

        textViewCountDown = findViewById(R.id.text_view_countdown);
        textColourDefaultCd = textViewCountDown.getTextColors();

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvScore.setText("Score: " + mazeView.score);
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
    }
}