package com.example.areyoukittenme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.Stack;


public class MazeView extends View {

    private Cell[][] cells;
    private static final int COLS = 10, ROWS = 5;
    private static final float WALL_THICKNESS = 38;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint;
    private BitmapShader wallTexture;
    private Bitmap hedge;

    public MazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    wallPaint = new Paint();
    wallPaint.setColor(Color.BLACK);
    wallPaint.setStrokeWidth(WALL_THICKNESS);

    Bitmap hedge = BitmapFactory.decodeResource(getResources(), R.drawable.hedge);
    wallTexture = new BitmapShader(hedge,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT);

    wallPaint.setShader(wallTexture);


    createMaze();
    }

    private void createMaze() {
//        Stack<Cell> stack = new Stack<>();
//        Cell current, next;
//
        cells = new Cell[COLS][ROWS];

//        landscape view- rows became columns?
//        for(int x=0; x<COLS; x++) {
//            for(int y=0; y<ROWS; y++) {

        for(int y=0; y<ROWS; y++) {
            for(int x=0; x<COLS; x++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        // removing walls
        // first row in landscape
        cells[0][0].bottomWall = false;
        cells[0][0].rightWall = false;
        cells[1][0].leftWall = false;
        cells[1][0].rightWall = false;
        cells[2][0].bottomWall = false;
        cells[2][0].leftWall = false;
        cells[3][0].bottomWall = false;
        cells[3][0].rightWall = false;
        cells[4][0].leftWall = false;
        cells[4][0].rightWall = false;
        cells[5][0].leftWall = false;
        cells[5][0].bottomWall = false;
        cells[6][0].rightWall = false;
        cells[6][0].bottomWall = false;
        cells[7][0].leftWall = false;
        cells[7][0].rightWall = false;
        cells[8][0].leftWall = false;
        cells[8][0].rightWall = false;
        cells[9][0].leftWall = false;
        cells[9][0].bottomWall = false;
        // second row in landscape
        cells[0][1].bottomWall = false;
        cells[0][1].topWall = false;
        cells[1][1].rightWall = false;
        cells[2][1].topWall = false;
        cells[2][1].leftWall = false;
        cells[3][1].topWall = false;
        cells[3][1].rightWall = false;
        cells[4][1].leftWall = false;
        cells[4][1].bottomWall = false;
        cells[5][1].rightWall = false;
        cells[5][1].topWall = false;
        cells[6][1].leftWall = false;
        cells[6][1].bottomWall = false;
        cells[6][1].topWall = false;
        cells[7][1].rightWall = false;
        cells[7][1].bottomWall = false;
        cells[8][1].bottomWall = false;
        cells[8][1].rightWall = false;
        cells[9][1].leftWall = false;
        cells[9][1].topWall = false;
        // 3rd row in landscape
        cells[0][2].topWall = false;
        cells[0][2].rightWall = false;
        cells[1][2].leftWall = false;
        cells[1][2].bottomWall = false;
        cells[2][2].bottomWall = false;
        cells[2][2].rightWall = false;
        cells[3][2].leftWall = false;
        cells[4][2].rightWall = false;
        cells[4][2].topWall = false;
        cells[4][2].bottomWall = false;
        cells[5][2].leftWall = false;
        cells[5][2].bottomWall = false;
        cells[6][2].rightWall = false;
        cells[6][2].topWall = false;
        cells[7][2].leftWall = false;
        cells[7][2].topWall = false;
        cells[8][2].topWall = false;
        cells[8][2].bottomWall = false;
        cells[9][2].bottomWall = false;
        // 4th row in landscape
        cells[0][3].bottomWall = false;
        cells[0][3].rightWall = false;
        cells[1][3].leftWall = false;
        cells[1][3].rightWall = false;
        cells[1][3].topWall = false;
        cells[2][3].leftWall = false;
        cells[2][3].topWall = false;
        cells[3][3].rightWall = false;
        cells[3][3].bottomWall = false;
        cells[4][3].topWall = false;
        cells[4][3].leftWall = false;
        cells[4][3].bottomWall = false;
        cells[5][3].topWall = false;
        cells[5][3].rightWall = false;
        cells[6][3].leftWall = false;
        cells[7][3].bottomWall = false;
        cells[7][3].rightWall = false;
        cells[8][3].leftWall = false;
        cells[8][3].topWall = false;
        cells[8][3].rightWall = false;
        cells[9][3].leftWall = false;
        cells[9][3].topWall = false;
        // 5th row in landscape
        cells[0][4].topWall = false;
        cells[0][4].rightWall = false;
        cells[1][4].leftWall = false;
        cells[1][4].rightWall = false;
        cells[2][4].rightWall = false;
        cells[2][4].leftWall = false;
        cells[3][4].topWall = false;
        cells[3][4].leftWall = false;
        cells[4][4].rightWall = false;
        cells[4][4].topWall = false;
        cells[5][4].leftWall = false;
        cells[6][4].rightWall = false;
        cells[7][4].leftWall = false;
        cells[7][4].rightWall = false;
        cells[7][4].topWall = false;
        cells[8][4].leftWall = false;
        cells[8][4].rightWall = false;
        cells[9][4].leftWall = false;
        cells[9][4].bottomWall = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);


        int width = getWidth();
        int height = getHeight();

        if (width / height < COLS / ROWS) {
            cellSize = (width / (COLS + 1)+1);
        } else {
            cellSize = (height / (ROWS + 1)+1);
        }

////        if (width / COLS > height / ROWS){
////            cellSize = height / (ROWS + 1);
////        }
////        else {
////            cellSize = width / (COLS + 1);
////        }

        hMargin = (width - COLS * cellSize) / 2;
        vMargin = (height - ROWS * cellSize) / 2;

        canvas.translate(hMargin, vMargin);


        for(int y=0; y<ROWS; y++) {
            for(int x=0; x<COLS; x++) {
                if(cells[x][y].topWall) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            wallPaint);
                }

                if(cells[x][y].leftWall) {
                    canvas.drawLine(
                            x * cellSize,
                            y * cellSize,
                            x * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if(cells[x][y].bottomWall) {
                    canvas.drawLine(
                            x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if(cells[x][y].rightWall) {
                    canvas.drawLine(
                            (x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
            }
        }
    }

    private class Cell{
        boolean
            topWall = true,
            leftWall = true,
            bottomWall = true,
            rightWall = true;

        int col, row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}
