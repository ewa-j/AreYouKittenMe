package com.example.areyoukittenme;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areyoukittenme.databinding.ActivityAquariumBinding;

public class AquariumActivity extends AppCompatActivity {

    float dX;
    float dY;
    ImageView attached = null;
    int caught = 0;
    ImageView[] fishes;
    ImageView[] axolotls;
    Long startTime;
    Long touchAxolotlTime = null;
    private ActivityAquariumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        binding = ActivityAquariumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.handView.setOnTouchListener(armListener);
        fishes = new ImageView[]{binding.imageView1 , binding.imageView2,binding.imageView3,binding.imageView4,binding.imageView5,binding.imageView6,binding.imageView7,binding.imageView8,binding.imageView9,binding.imageView10};
        axolotls = new ImageView[]{binding.axolotl1,binding.axolotl2,binding.axolotl3};
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private final View.OnTouchListener armListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            int actionMasked = event.getActionMasked();
            if (actionMasked == MotionEvent.ACTION_DOWN) {
                dX = binding.armContainer.getX() - event.getRawX();
                dY = binding.armContainer.getY() - event.getRawY();
            } else if (actionMasked == MotionEvent.ACTION_MOVE) {
                if(touchAxolotlTime == null && event.getRawX() + dX > 0 && event.getRawX() + dX + binding.armContainer.getWidth() < binding.getRoot().getRight())
                binding.armContainer.setX(event.getRawX() + dX);
                if(touchAxolotlTime == null && event.getRawY() + dY + binding.armView.getHeight() > 0 && event.getRawY() + dY + binding.armContainer.getHeight() < binding.getRoot().getBottom())
                binding.armContainer.setY(event.getRawY() + dY);

                for (ImageView fish : fishes) {
                    if ((attached == null || attached == fish)) {
                        if (attached == fish && binding.armContainer.getHeight() + binding.armContainer.getY() < binding.topBackgroundView.getBottom()) {
                            fish.setVisibility(View.GONE);
                            attached = null;
                            caught++;
                            if (caught == 10) {
                                startActivity(new Intent(AquariumActivity.this, EndActivity.class));
                            }
                        } else if (attached == fish) {
                            fish.setX((binding.armContainer.getX() + (binding.armContainer.getWidth() >> 1)) - (fish.getWidth() >> 1));
                            fish.setY(binding.armContainer.getY() + binding.armView.getHeight() + (binding.handView.getHeight() >> 1) - (fish.getHeight() >> 1));
                        } else if (CheckCollision(fish, binding.handView) && fish.getVisibility() != View.GONE) {
                            fish.setX((binding.armContainer.getX() + (binding.armContainer.getWidth() >> 1)) - (fish.getWidth() >> 1));
                            fish.setY(binding.armContainer.getY() + binding.armView.getHeight() + (binding.handView.getHeight() >> 1) - (fish.getHeight() >> 1));
                            attached = fish;
                        }
                    }
                }
            } else {
                return false;
            }
            return true;
        }
    };

    public boolean CheckCollision(View v1, View v2) {
        RectF R1=new RectF(v1.getX(), v1.getY(), v1.getX() + v1.getWidth(), v1.getY() + v1.getHeight());
        int left = v2.getRight() - v2.getLeft();
        RectF R2=new RectF(binding.armContainer.getX(), binding.armContainer.getY() + binding.armView.getHeight(), binding.armContainer.getX() + left, binding.armContainer.getY() + binding.armContainer.getHeight());
        return RectF.intersects(R1, R2);
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            for (ImageView axolotl : axolotls) {
                if (detectCollision(axolotl) && touchAxolotlTime == null) {
                    touchAxolotlTime = System.currentTimeMillis();
                    binding.armContainer.setY(binding.armContainer.getTop());
                    attached = null;
                    //axolotl.setVisibility(View.INVISIBLE);
                }
            }
            if(touchAxolotlTime != null && System.currentTimeMillis() - touchAxolotlTime > 3000){
                touchAxolotlTime = null;
            }

            binding.textView2.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 20);
        }
    };

    boolean detectCollision(ImageView v1){
        RectF R1=new RectF(v1.getX(), v1.getY(), v1.getX() + v1.getWidth(), v1.getY() + v1.getHeight());
        int left = binding.handView.getRight() - binding.handView.getLeft();
        RectF R2=new RectF(binding.armContainer.getX(), binding.armContainer.getY() + binding.armView.getHeight(), binding.armContainer.getX() + left, binding.armContainer.getY() + binding.armContainer.getHeight());
        return RectF.intersects(R1, R2);
    }
}
