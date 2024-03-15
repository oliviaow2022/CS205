package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Paint paint;
    private int playerX, playerY;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        playerX = 100;
        playerY = 100;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                playerX = (int) event.getX();
                playerY = (int) event.getY();
                break;
        }
        return true;
    }

    public void update() {
        // Update game logic here
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawCircle(playerX, playerY, 50, paint); // Draw player
        }
    }
}
