package com.example.areyoukittenme.lib;

import android.graphics.PointF;
import android.widget.ImageView;

import java.util.Random;

public class Axolotl {
    public ImageView axolotl;
    private PointF last;
    public PointF newPosition = null;
    public float speed = 10f;
    Random rand;


    public Axolotl(ImageView axolotl){
        this.axolotl = axolotl;
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

        axolotl.setX((xDiff / time) + axolotl.getX());
        axolotl.setY((yDiff / time) + axolotl.getY());
        if(Math.abs(axolotl.getX() - Math.abs(newPosition.x)) < 8 && Math.abs(axolotl.getY() - Math.abs(newPosition.y)) < 8){
            newPosition = null;
        }
    }

    private void createNewPosition(ImageView container){
        speed = rand.nextFloat() * 8 + 3;
        last = new PointF(axolotl.getX(), axolotl.getY());
        newPosition = new PointF(rand.nextFloat()*(container.getRight() - axolotl.getWidth()),
                container.getTop() + rand.nextFloat()*(container.getHeight() - axolotl.getHeight()));
    }
}

