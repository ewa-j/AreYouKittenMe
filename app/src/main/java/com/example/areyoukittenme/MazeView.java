package com.example.areyoukittenme;


import static java.lang.Thread.sleep;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;


public class MazeView extends View {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Cell[][] cells;
    private Cell player, exit, enemy, enemyTwo, butterfly, butterflyTwo;
    private static final int COLS = 8, ROWS = 5;
    private static final float WALL_THICKNESS = 38;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint, playerPaint, exitPaint, enemyPaint, butterflyPaint, scorePaint, hpPaint;
    private BitmapShader wallTexture;
    private Bitmap hedge;
    private Random random;
    int randomX;
    int randomY;

    public static int hp = 50;
    public int score = 0;
    Context context;
    String hpText = "HP " + hp;
    String scoreText = "Score " + score;

    boolean gameState = true;


    public MazeView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);

        exitPaint = new Paint();
        exitPaint.setColor(Color.CYAN);

        enemyPaint = new Paint();
        enemyPaint.setColor(Color.RED);

        butterflyPaint = new Paint();
        butterflyPaint.setColor(Color.YELLOW);

        hpPaint = new Paint();
        hpPaint.setColor(Color.BLACK);
        hpPaint.setTextSize(70);
        hpPaint.setStyle(Paint.Style.FILL);


        scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(70);
        scorePaint.setStyle(Paint.Style.FILL);

        Bitmap hedge = BitmapFactory.decodeResource(getResources(), R.drawable.floweryhedgetwo);
        wallTexture = new BitmapShader(hedge,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);

        wallPaint.setShader(wallTexture);

        random = new Random();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 120000;

                while (System.currentTimeMillis() < futureTime) {
                    synchronized (this) {
                        try {
                            moveEnemy();
                            moveButterfly();
                            sleep(300);
                            invalidate();

                        } catch (Exception e) {
                        }
                    }
                }
            }
        };
        Thread newThread = new Thread(run);
        newThread.start();

        createMaze();

        playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);

        exitPaint = new Paint();
        exitPaint.setColor(Color.CYAN);

        hedge = BitmapFactory.decodeResource(getResources(), R.drawable.hedge);
        wallTexture = new BitmapShader(hedge,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);

        wallPaint.setShader(wallTexture);

        random = new Random();
        createMaze();
    }

    private Cell getNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();

        //left neighbour
        if (cell.col > 0) {
            if (!cells[cell.col - 1][cell.row].visited) {
                neighbours.add(cells[cell.col - 1][cell.row]);
            }
        }

        //right neighbour
        if (cell.col < COLS - 1) {
            if (!cells[cell.col + 1][cell.row].visited) {
                neighbours.add(cells[cell.col + 1][cell.row]);
            }
        }

        //top neighbour
        if (cell.row > 0) {
            if (!cells[cell.col][cell.row - 1].visited) {
                neighbours.add(cells[cell.col][cell.row - 1]);
            }
        }

        //bottom neighbour
        if (cell.row < ROWS - 1) {
            if (!cells[cell.col][cell.row + 1].visited) {
                neighbours.add(cells[cell.col][cell.row + 1]);
            }
        }

        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        return null;
    }

    private void removeWall(Cell current, Cell next) {
        // neighbour below
        if (current.col == next.col & current.row == next.row + 1) {
            current.topWall = false;
            next.bottomWall = false;
        }

        // neighbour above
        if (current.col == next.col & current.row == next.row - 1) {
            current.bottomWall = false;
            next.topWall = false;
        }

        // neighbour right
        if (current.col == next.col + 1 & current.row == next.row) {
            current.leftWall = false;
            next.rightWall = false;
        }

        // neighbour left
        if (current.col == next.col - 1 & current.row == next.row) {
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    private void createMaze() {
        // random maze creation
        Stack<Cell> stack = new Stack<>();
        Cell current, next;
        randomX = random.nextInt(COLS-1);
        randomY = random.nextInt(ROWS-1);

        cells = new Cell[COLS][ROWS];

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        player = cells[0][0];
        exit = cells[COLS - 1][ROWS - 1];
        enemy = cells[(COLS - 2) / 2][(ROWS - 3) / 2];
        enemyTwo = cells[COLS - 2][ROWS - 2];
        butterfly = cells[(COLS - 1)][0];
        butterflyTwo = cells[(COLS - 6)][ROWS - 1];

        current = cells[0][0];
        current.visited = true;
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else {
                current = stack.pop();
            }

        } while (!stack.empty());
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        canvas.drawText(hpText, 20, 100, hpPaint);
        canvas.translate(0, 0);

        if (player == enemy || player == enemyTwo) {
            hp -= 10;
            randomX = random.nextInt(COLS-1);
            randomY = random.nextInt(ROWS-1);
            if (player == enemy) { enemy = cells[randomX][randomY]; }
            if (player == enemyTwo) { enemyTwo = cells[randomX][randomY]; }
            if(hp < 20) {
                hpPaint.setColor(Color.RED);
            }
            hpText = "HP " + hp;
            canvas.drawText(hpText, 20, 100, hpPaint);
            canvas.translate(0, 0);

            if (hp <= 0) {
                Context context = getContext();
                Intent intent = new Intent(context, GameOverActivity.class);
                intent.putExtra("score", score);
                context.startActivity(intent);
            }
        }

        if(player == butterfly) {
            score += 10;
            randomX = random.nextInt(COLS-1);
            randomY = random.nextInt(ROWS-1);
            butterfly = cells[randomX][randomY];
        }

        canvas.restore();

        int width = getWidth();
        int height = getHeight();

        if (width / height < COLS / ROWS) {
            cellSize = (width / (COLS + 1) + 1);
        } else {
            cellSize = (height / (ROWS + 1) + 1);
        }

        hMargin = (width - COLS * cellSize) / 2;
        vMargin = (height - ROWS * cellSize) / 2;

        canvas.translate(hMargin, vMargin);


        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (cells[x][y].topWall) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            wallPaint);
                }

                if (cells[x][y].leftWall) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if (cells[x][y].bottomWall) {
                    canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if (cells[x][y].rightWall) {
                    canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
            }
        }

        float margin = cellSize / 8;

//        canvas.drawRect(
//                butterfly.col * cellSize + margin,
//                butterfly.row * cellSize + margin,
//                (butterfly.col + 1) * cellSize - margin,
//                (butterfly.row + 1) * cellSize - margin,
//                butterflyPaint);

        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat_sprite);
        canvas.drawBitmap(myBitmap, null, new RectF(
                        player.col*cellSize+margin,
                        player.row*cellSize+margin,
                        (player.col+1)*cellSize-margin,
                        (player.row+1)*cellSize-margin),
                null);

        Bitmap exitBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maze_exit);
        canvas.drawBitmap(exitBitmap, null, new RectF(
                        (exit.col+0.1f)*cellSize+margin/2,
                        (exit.row)*cellSize+margin/2,
                        (exit.col+0.9f)*cellSize-margin/2,
                        (exit.row+0.9f)*cellSize-margin/2),
                exitPaint);

        Bitmap enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cucumber);
        canvas.drawBitmap(enemyBitmap, null, new RectF(
                        (enemy.col+0.1f)*cellSize+margin/2,
                        (enemy.row)*cellSize+margin/2,
                        (enemy.col+0.9f)*cellSize-margin/2,
                        (enemy.row+0.9f)*cellSize-margin/2),
                enemyPaint);

        canvas.drawBitmap(enemyBitmap, null, new RectF(
                        (enemyTwo.col+0.1f)*cellSize+margin/2,
                        (enemyTwo.row)*cellSize+margin/2,
                        (enemyTwo.col+0.9f)*cellSize-margin/2,
                        (enemyTwo.row+0.9f)*cellSize-margin/2),
                enemyPaint);

        Bitmap butterflyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.butterfly);
        canvas.drawBitmap(butterflyBitmap, null, new RectF(
                        (butterfly.col+0.1f)*cellSize+margin/2,
                        (butterfly.row)*cellSize+margin/2,
                        (butterfly.col+0.9f)*cellSize-margin/2,
                        (butterfly.row+0.9f)*cellSize-margin/2),
                butterflyPaint);

    }

    private void moveEnemy() {

        Direction direction;
        random = new Random();
        int randomDirection = random.nextInt(Direction.values().length);
        direction = Direction.values()[randomDirection];
        Direction directionTwo;
        int randomDirectionTwo = random.nextInt(Direction.values().length);
        directionTwo = Direction.values()[randomDirectionTwo];

        switch (direction) {
            case UP:
                if (!enemy.topWall)
                    enemy = cells[enemy.col][enemy.row - 1];
            case DOWN:
                if (!enemy.bottomWall)
                    enemy = cells[enemy.col][enemy.row + 1];
                break;
            case LEFT:
                if (!enemy.leftWall)
                    enemy = cells[enemy.col - 1][enemy.row];
                break;
            case RIGHT:
                if (!enemy.rightWall)
                    enemy = cells[enemy.col + 1][enemy.row];
                break;
        }


        switch (directionTwo) {
            case UP:
                if (!enemyTwo.topWall)
                    enemyTwo = cells[enemyTwo.col][enemyTwo.row - 1];
            case DOWN:
                if (!enemyTwo.bottomWall)
                    enemyTwo = cells[enemyTwo.col][enemyTwo.row + 1];
                break;
            case LEFT:
                if (!enemyTwo.leftWall)
                    enemyTwo = cells[enemyTwo.col - 1][enemyTwo.row];
                break;
            case RIGHT:
                if (!enemyTwo.rightWall)
                    enemyTwo = cells[enemyTwo.col + 1][enemyTwo.row];
                break;
        }
    }


    private void moveButterfly() {
        Direction direction;
        random = new Random();
        int randomDirection = random.nextInt(Direction.values().length);
        direction = Direction.values()[randomDirection];

        switch (direction) {
            case UP:
                if (!butterfly.topWall)
                    butterfly = cells[butterfly.col][butterfly.row - 1];
                break;
            case DOWN:
                if (!butterfly.bottomWall)
                    butterfly = cells[butterfly.col][butterfly.row + 1];
                break;
            case LEFT:
                if (!butterfly.leftWall)
                    butterfly = cells[butterfly.col - 1][butterfly.row];
                break;
            case RIGHT:
                if (!butterfly.rightWall)
                    butterfly = cells[butterfly.col + 1][butterfly.row];
                break;
        }
    }


    private void movePlayer(Direction direction) {
        switch (direction) {
            case UP:
                if (!player.topWall)
                    player = cells[player.col][player.row - 1];
                break;
            case DOWN:
                if (!player.bottomWall)
                    player = cells[player.col][player.row + 1];
                break;
            case LEFT:
                if (!player.leftWall)
                    player = cells[player.col - 1][player.row];
                break;
            case RIGHT:
                if (!player.rightWall)
                    player = cells[player.col + 1][player.row];
                break;
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
            return true;

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();

            //player
            float playerCenterX = hMargin + (player.col + 0.5f) * cellSize;
            float playerCenterY = vMargin + (player.row + 0.5f) * cellSize;

            float dx = x - playerCenterX;
            float dy = y - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > cellSize || absDy > cellSize) {
                if (absDx > absDy) {
                    //move to x direction
                    if (dx > 0) {
                        //right
                        movePlayer(Direction.RIGHT);
                    } else {
                        //left
                        movePlayer(Direction.LEFT);
                    }
                } else {
                    //move to y direction
                    if (dy > 0) {
                        //down
                        movePlayer(Direction.DOWN);
                    } else {
                        //up
                        movePlayer(Direction.UP);
                    }
                }
            }

            if (player == exit && gameState) {

                score += 100;
                gameState = false;
                Context context = getContext();
                Intent intent = new Intent(context, WinActivity.class);
                intent.putExtra("score", score);
                context.startActivity(intent);
                ((Activity)context).finish();
                return false;
            }

            return true;
        }
        return super.onTouchEvent(event);
    }

    private class Cell {
        boolean
                topWall = true,
                leftWall = true,
                bottomWall = true,
                rightWall = true;
        boolean visited = false;

        int col, row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

}


