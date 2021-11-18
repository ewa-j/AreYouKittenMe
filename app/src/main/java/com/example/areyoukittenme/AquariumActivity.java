package com.example.areyoukittenme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.areyoukittenme.databinding.ActivityAquariumBinding;

import java.util.ArrayList;

public class AquariumActivity extends AppCompatActivity {

    float dX;
    float dY;
    ImageView attached = null;
    int caught = 0;

    private final View.OnTouchListener armListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event)
        {

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = binding.arm.getX() - event.getRawX();
                    dY = binding.arm.getY() - event.getRawY();
                    break;

                case MotionEvent.ACTION_MOVE:

                    //if(event.getRawY() + dY + view.getBottom() - view.getTop() < binding.getRoot().getBottom()) {}
                    //if(event.getRawX() + dX + view.getRight() - view.getLeft() < binding.getRoot().getRight()) {}
                    binding.arm.setX(event.getRawX()  + dX);
                    binding.arm.setY(event.getRawY() + dY);

                    for(ImageView fish : fishes){
                        if((attached == null || attached == fish)){
                            if(attached == fish && binding.arm.getHeight() + binding.arm.getY() < binding.topBackgroundView.getBottom()){
                                fish.setVisibility(View.GONE);
                                attached = null;
                                caught++;
                                if(caught == 10){
                                    Intent startShapes = new Intent(AquariumActivity.this, EndActivity.class);
                                    startActivity(startShapes);
                                }
                            }
                            else if (attached == fish) {
                                fish.setX((binding.arm.getX() + (binding.arm.getWidth() >> 1)) - (fish.getWidth() >> 1));
                                fish.setY(binding.arm.getY() + binding.arms.getHeight() + (binding.armView.getHeight() >> 1) - (fish.getHeight() >> 1));
                            }
                            else if (CheckCollision(fish, binding.armView) && fish.getVisibility() != View.GONE){
                                fish.setX((binding.arm.getX() + (binding.arm.getWidth() >> 1)) - (fish.getWidth() >> 1));
                                fish.setY(binding.arm.getY() + binding.arms.getHeight() + (binding.armView.getHeight() >> 1) - (fish.getHeight() >> 1));
                                attached = fish;
                            }

                        }
                    }

                    break;

                default:
                    return false;
            }
            return true;
        }
    };

    public boolean CheckCollision(View v1, View v2) {
        RectF R1=new RectF(v1.getX(), v1.getY(), v1.getX() + v1.getWidth(), v1.getY() + v1.getHeight());
        int left = v2.getRight() - v2.getLeft();
        RectF R2=new RectF(binding.arm.getX(), binding.arm.getY() + binding.arms.getHeight(), binding.arm.getX() + left, binding.arm.getY() + binding.arm.getHeight());
        return RectF.intersects(R1, R2);
    }

    ImageView[] fishes;
    private ActivityAquariumBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        binding = ActivityAquariumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.armView.setOnTouchListener(armListener);
        fishes = new ImageView[]{binding.imageView1 , binding.imageView2,binding.imageView3,binding.imageView4,binding.imageView5,binding.imageView6,binding.imageView7,binding.imageView8,binding.imageView9,binding.imageView10};
        
    }

}
