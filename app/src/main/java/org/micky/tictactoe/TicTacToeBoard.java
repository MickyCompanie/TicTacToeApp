package org.micky.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int WinningLineColor;

    private final Paint paint = new Paint();

    private int cellSize = getWidth() / 3;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // getting values from the attrs.xml files
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            WinningLineColor = a.getInteger(R.styleable.TicTacToeBoard_WinningLineColor, 0);
        }finally {
            a.recycle();
        }

    }

    // setting board's size
    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        // find the smallest dimension of the user's screen size
        int dimension = Math.min(getMeasuredWidth() ,getMeasuredHeight());

        cellSize = dimension / 3;

        // setting a scare for the board that is equal to the smallest dimension of the user's screen size
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        // columns
        for (int c = 1; c < 3; c++){
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getWidth(), paint);
        }
        // rows
        for (int r = 1; r < 3; r++){
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }
}
