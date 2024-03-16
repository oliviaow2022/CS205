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
    private int joystickCenterX = 200;
    private int joystickCenterY = 2000;
    private int joyStickX = 200;
    private int joyStickY = 2000;
    private int joystickRadius = 150; // Radius of the joystick
    private int margin = 50; // Margin between joystick and screen edges
    private int playerVelocityX = 0;
    private int playerVelocityY = 0;

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
        float touchX = event.getX();
        float touchY = event.getY();
        float distanceX = touchX - joystickCenterX;
        float distanceY = touchY - joystickCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (action == MotionEvent.ACTION_MOVE){
            if (distance <= joystickRadius - 50){
                joyStickX = (int) touchX;
                joyStickY = (int) touchY;
            }
            else {
                playerX = (int) touchX;
                playerY = (int) touchY;
            }
        }
        if (action == MotionEvent.ACTION_UP){
            joyStickX = joystickCenterX;
            joyStickY = joystickCenterY;
        }
        return true;
    }

    public boolean checkBounded(){
        boolean boundedX = false;
        boolean boundedY = false;
        if (playerX >= 50 && playerX < 2050){
            boundedX = true;
        }
        if (playerY >= 50 && playerY < 1000){
            boundedY = true;
        }
        if (boundedX && boundedY){
            return true;
        }
        return false;
    }

    public void update() {
        // Update game logic here
        playerVelocityX = joyStickX - joystickCenterX;
        playerVelocityY = joyStickY - joystickCenterY;
        playerX += playerVelocityX;
        playerY += playerVelocityY;

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);// Draw buttons
            paint.setColor(Color.WHITE);
            canvas.drawCircle(joystickCenterX, joystickCenterY, joystickRadius, paint);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(playerX, playerY, 50, paint); // Draw player
            paint.setColor(Color.BLACK);
            canvas.drawCircle(joyStickX,joyStickY , 50, paint); // Draw center of joyStick
        }
    }
}
