package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.WindowMetrics;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread gameThread;
    private final Player player = new Player(100,100);
    private final Joystick joystick = new Joystick();
    private final Paint paint = new Paint();

    private Rect display;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
        display = windowMetrics.getBounds();
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
        joystick.onTouchEvent(event);
        player.update(joystick, display);
        return true;
    }

    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            player.draw(canvas, paint);
            joystick.draw(canvas, paint);
        }
    }
}