package com.example.areyoukittenme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areyoukittenme.databinding.ActivityAquariumBinding;
import com.example.areyoukittenme.lib.Axolotl;
import com.example.areyoukittenme.lib.Fish;

public class AquariumActivity extends AppCompatActivity {

    float dX;
    float dY;
    Fish attached = null;
    int caught = 0;
    Fish[] fishes;
    Axolotl[] axolotls;
    Long startTime;
    Long touchAxolotlTime = null;
    ActivityAquariumBinding binding;
    Handler timerHandler = new Handler();
    private boolean end = false;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAquariumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.handView.setOnTouchListener(armListener);

        fishes = new Fish[]{new Fish(binding.fish1) , new Fish(binding.fish2), new Fish(binding.fish3), new Fish(binding.fish4), new Fish(binding.fish5), new Fish(binding.fish6), new Fish(binding.fish7), new Fish(binding.fish8), new Fish(binding.fish9), new Fish(binding.fish10)};
        axolotls = new Axolotl[]{new Axolotl(binding.axolotl1),new Axolotl(binding.axolotl2),new Axolotl(binding.axolotl3)};
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private final View.OnTouchListener armListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            int actionMasked = event.getActionMasked();
            //not touched axolotl within 3 seconds
            if(touchAxolotlTime == null){
                //on mouse down, get co-ordinates
                if (actionMasked == MotionEvent.ACTION_DOWN) {
                    dX = binding.armContainer.getX() - event.getRawX();
                    dY = binding.armContainer.getY() - event.getRawY();
                } else if (actionMasked == MotionEvent.ACTION_MOVE) {
                    //move horizontal if in range
                    if (event.getRawX() + dX > 0 && event.getRawX() + dX + binding.armContainer.getWidth() < binding.getRoot().getRight())
                        binding.armContainer.setX(event.getRawX() + dX);
                    //move vertical if on range
                    if (event.getRawY() + dY + binding.armView.getHeight() > 0 && event.getRawY() + dY + binding.armContainer.getHeight() < binding.getRoot().getBottom())
                        binding.armContainer.setY(event.getRawY() + dY);

                    for (Fish fish : fishes) {
                        //if fish is caught and is moved to the top, remove it
                        if (attached == fish && binding.armContainer.getHeight() + binding.armContainer.getY() < binding.topBackgroundView.getBottom()) {
                            fish.fish.setVisibility(View.GONE);
                            score += 10;
                            attached = null;
                            //move to maze instructions if all caught
                            caught++;
                            if (caught == 10 && !end) {
                                end = true;
                                timerHandler.removeCallbacks(timerRunnable);
                                score += 100;
                                Intent intent = new Intent(AquariumActivity.this, FirstActivity.class);
                                intent.putExtra("score", score);
                                startActivity(intent);
                                return false;
                            }

                        } else if (attached == fish) { //else move current fish
                            fish.fish.setX((binding.armContainer.getX() + (binding.armContainer.getWidth() >> 1)) - (fish.fish.getWidth() >> 1));
                            fish.fish.setY(binding.armContainer.getY() + binding.armView.getHeight() + (binding.handView.getHeight() >> 1) - (fish.fish.getHeight() >> 1));
                        } else if (attached == null && CheckCollision(fish.fish, binding.handView) && fish.fish.getVisibility() != View.GONE) { //else attach new fish on collision
                            fish.fish.setX((binding.armContainer.getX() + (binding.armContainer.getWidth() >> 1)) - (fish.fish.getWidth() >> 1));
                            fish.fish.setY(binding.armContainer.getY() + binding.armView.getHeight() + (binding.handView.getHeight() >> 1) - (fish.fish.getHeight() >> 1));
                            attached = fish;
                        }
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    };

    private boolean CheckCollision(View v1, View v2) {
        RectF R1=new RectF(v1.getX(), v1.getY(), v1.getX() + v1.getWidth(), v1.getY() + v1.getHeight());
        int left = v2.getRight() - v2.getLeft();
        RectF R2=new RectF(binding.armContainer.getX(), binding.armContainer.getY() + binding.armView.getHeight(), binding.armContainer.getX() + left, binding.armContainer.getY() + binding.armContainer.getHeight());
        return RectF.intersects(R1, R2);
    }

    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            updateTime();
            for (Axolotl axolotl : axolotls) {
                axolotl.move(binding.fishContainer);
                if (detectCollision(axolotl.axolotl) && touchAxolotlTime == null) {
                    touchAxolotlTime = System.currentTimeMillis();
                    binding.armContainer.setY(binding.armContainer.getTop());
                    if(attached != null) {
                        attached.newPosition = null;
                    }
                    if(score > 10) {
                        score -= 10;
                    } else {
                        score = 0;
                    }
                    attached = null;
                    binding.timeWaitView.setVisibility(View.VISIBLE);
                }
            }
            for(Fish fish: fishes){
                if(attached != fish) {
                    fish.move(binding.fishContainer);
                }
            }
            if(touchAxolotlTime != null){
                int timeLeft = (int) (3 - (System.currentTimeMillis() - touchAxolotlTime)/1000);
                binding.timeWaitView.setText(String.valueOf(timeLeft));

                if(System.currentTimeMillis() - touchAxolotlTime > 3000){
                    binding.timeWaitView.setVisibility(View.INVISIBLE);
                    touchAxolotlTime = null;
                    dY = 0; dX = 0;
                }
            }
            timerHandler.postDelayed(this, 20);
        }
    };

    private boolean detectCollision(ImageView v1){
        RectF R1=new RectF(v1.getX(), v1.getY(), v1.getX() + v1.getWidth(), v1.getY() + v1.getHeight());
        int left = binding.handView.getRight() - binding.handView.getLeft();
        RectF R2=new RectF(binding.armContainer.getX(), binding.armContainer.getY() + binding.armView.getHeight(), binding.armContainer.getX() + left, binding.armContainer.getY() + binding.armContainer.getHeight());
        return RectF.intersects(R1, R2);
    }

    private void updateTime(){
        int totalTime = 20000;//milliseconds
        long millis = totalTime - (System.currentTimeMillis() - startTime);
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        if (millis <= 0 && !end){
            end = true;
            timerHandler.removeCallbacks(timerRunnable);
            Intent intent = new Intent(AquariumActivity.this, GameOverActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            return;
        }
        else if (millis <= 10000) {
            binding.countDownTimer.setTextColor(Color.RED);
        } else {
            binding.countDownTimer.setTextColor(Color.BLACK);
        }
        binding.countDownTimer.setText(String.format("%d:%02d", minutes, seconds));
    }
}
