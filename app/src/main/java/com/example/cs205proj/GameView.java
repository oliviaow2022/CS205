package com.example.cs205proj;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
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
    private final Player player = new Player(100,100);
    private final Joystick joystick = new Joystick();
    private final Paint paint = new Paint();
    private final Rect display;
    private GameDisplay gameDisplay;

    public static int getScreenWidth(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }
    public static int getScreenHeight(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().height() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
        display = windowMetrics.getBounds();

        //initialise game display and center it to player
        gameDisplay = new GameDisplay(getScreenWidth((Activity) getContext()),
                getScreenHeight((Activity) getContext()), player);
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
        //player.update(joystick, display);
        return true;
    }

    public void update(long deltaTime) {
        player.update(joystick, display);
        gameDisplay.update();
    }

    @Override //draw game objects
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            player.draw(canvas, paint, gameDisplay);
            joystick.draw(canvas, paint);
        }
    }
}