package org.micky.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int WinningLineColor;

    private final Paint paint = new Paint();

    private final GameLogic game;

    private int cellSize = getWidth() / 3;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

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

        cellSize = (int) (dimension * 0.75) / 3;

        // setting a scare for the board that is equal to the smallest dimension of the user's screen size
        setMeasuredDimension((int) (dimension * 0.75), (int) (dimension * 0.75));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get x & y position of user's click
        float x = event.getX();
        float y = event.getY();

        // make sure the user touched the game board
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if (game.updateGameBoard(row, col)){
                invalidate();

                // updating player's turn
                if (game.getPlayer() % 2 == 0){
                    game.setPlayer(game.getPlayer() - 1);
                }else{
                    game.setPlayer(game.getPlayer() + 1);
                }
            }

            invalidate();

            return true;
        }

        return false;
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

    private void drawMarkers(Canvas canvas){
        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                if (game.getGameBoard()[r][c] != 0){
                    if (game.getGameBoard()[r][c] == 1){
                        drawX(canvas, r, c);
                    }else{
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas  canvas, int row, int col){
        paint.setColor(XColor);

        canvas.drawLine((float) ((col+1)* cellSize - cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) (col*cellSize + cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);
        canvas.drawLine((float) (col* cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((col+1)*cellSize - cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);
    }


    private void drawO(Canvas  canvas, int row, int col){
        paint.setColor(OColor);

        canvas.drawOval((float) (col*cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((col*cellSize + cellSize) - cellSize*0.2),
                        (float) ((row*cellSize + cellSize) - cellSize*0.2),
                        paint);
    }

    public void resetGame() {
        game.resetGame();
    }
}
