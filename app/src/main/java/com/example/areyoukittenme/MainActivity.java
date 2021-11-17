package com.example.areyoukittenme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.areyoukittenme.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final View.OnTouchListener changeScreenListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {

                case MotionEvent.ACTION_UP:
                    Intent startShapes = new Intent(MainActivity.this, FirstActivity.class);
                    startActivity(startShapes);
                default:
                    break;
            }
            return false;
        }
    };

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        binding.playBtn.setOnTouchListener(changeScreenListener);
    }
}