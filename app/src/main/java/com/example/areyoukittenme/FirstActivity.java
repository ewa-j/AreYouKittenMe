package com.example.areyoukittenme;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;

import com.example.areyoukittenme.databinding.ActivityFullscreenBinding;

public class FirstActivity extends AppCompatActivity {
    private View mControlsView;
    private View mContentView;

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


    private ActivityFullscreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
//        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        mControlsView = binding.fullscreenContentControls;
//        mContentView = binding.fullscreenContent;

    }
}
