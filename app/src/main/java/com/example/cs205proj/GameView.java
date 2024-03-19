package com.example.cs205proj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread gameThread;
    private final Player player;
    private final Enemies enemies;
    private final Joystick joystick = new Joystick();
    private final Paint paint = new Paint();
    private final Rect display;

    public GameView(Context context, Player player) {
        super(context);
        this.player = player;
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
        display = windowMetrics.getBounds();
        enemies = new Enemies(5, display, player);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
//        enemies.setRunning(true);
//        enemies.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        display.set(0, 0, width, height);

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
        joystick.onTouchEvent(event);
        //player.update(joystick, display);
        return true;
    }

    public void update(long deltaTime) {
            player.update(joystick, display);
            enemies.update(display);
    }

    @Override //draw game objects
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Calculate the offset to center the player on the screen
            int offsetX = display.width() / 2 - player.x;
            int offsetY = display.height() / 2 - player.y;
    
            // Save the current canvas state
            canvas.save();
    
            // Translate canvas to center on player
            canvas.translate(offsetX, offsetY);
    
            // Draw objects relative to the centered canvas
            player.draw(canvas, paint);
            enemies.draw(canvas, paint);
    
            // Restore the canvas state to original
            canvas.restore();
    
            // Draw joystick at its fixed position
            joystick.draw(canvas, paint);
        }
    }
} 