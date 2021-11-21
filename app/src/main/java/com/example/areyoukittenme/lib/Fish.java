package com.example.areyoukittenme.lib;

import android.graphics.PointF;
import android.widget.ImageView;

import java.util.Random;

public class Fish {

    public ImageView fish;
    private PointF last;
    public PointF newPosition = null;
    public float speed = 10f;
    Random rand;


    public Fish(ImageView fish){
        this.fish = fish;
        rand = new Random();
    }

    public void move(ImageView container){

        if(newPosition == null){
            createNewPosition(container);
        }

        float xDiff = newPosition.x - last.x;
        float yDiff = newPosition.y - last.y;

        if (xDiff == 0 && yDiff == 0) {
            newPosition = null;
            return;
        }

        float time = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff) / speed;

        fish.setX((xDiff / time) + fish.getX());
        fish.setY((yDiff / time) + fish.getY());
        if(Math.abs(fish.getX() - Math.abs(newPosition.x)) < 8 && Math.abs(fish.getY() - Math.abs(newPosition.y)) < 8){
            newPosition = null;
        }
    }

    private void createNewPosition(ImageView container){
        speed = rand.nextFloat() * 5 + 3;
        last = new PointF(fish.getX(), fish.getY());
        newPosition = new PointF(rand.nextFloat()*(container.getRight() - fish.getWidth()),
                container.getTop() + rand.nextFloat()*(container.getHeight() - fish.getHeight()));
    }
}
