package com.example.areyoukittenme;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.areyoukittenme.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {
    private View mControlsView;
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };


//    private final Runnable mShowPart2Runnable = new Runnable() {
//        @Override
//        public void run() {
//            // Delayed display of UI elements
//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.show();
//            }
//            mControlsView.setVisibility(View.VISIBLE);
//        }
//    };

    private final View.OnTouchListener endBtnListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_UP:
                    Intent startShapes = new Intent(FirstActivity.this, EndActivity.class);
                    startActivity(startShapes);
                default:
                    break;
            }
            return false;
        }
    };


    private ActivityFirstBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.endBtn.setOnTouchListener(endBtnListener);
//        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        mControlsView = binding.fullscreenContentControls;
//        mContentView = binding.fullscreenContent;
    }
}
