package com.example.areyoukittenme;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.areyoukittenme.databinding.ActivityEndBinding;
import com.example.areyoukittenme.databinding.ActivityFirstBinding;

public class EndActivity extends AppCompatActivity {
    private final View.OnTouchListener restartBtnListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_UP:
                    Intent startShapes = new Intent(EndActivity.this, FullscreenActivity.class);
                    startActivity(startShapes);
                default:
                    break;
            }
            return false;
        }
    };

    private ActivityEndBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        binding = ActivityEndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.restart.setOnTouchListener(restartBtnListener);
    }
}
