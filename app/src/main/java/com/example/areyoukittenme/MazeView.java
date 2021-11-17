package com.example.areyoukittenme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MazeView extends View {

    private Cell[][] cells;
    private static final int COLS = 7, ROWS = 10;
    private static final float WALL_THICKNESS = 4;
    private float cellSize, hMargin, vMargin;
    private Paint wallPaint;

    public MazeView(Context context, @Nullable AttributeSet attrs) { super(context, attrs);

    wallPaint = new Paint();
    wallPaint.setColor(Color.BLACK);
    wallPaint.setStrokeWidth(WALL_THICKNESS);
    }


    private void createMaze() {
        cells = new Cell[COLS][ROWS];

        for(int x=0; x<COLS; x++) {
            for(int y=0; x<ROWS; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
    }

    private class Cell {
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
