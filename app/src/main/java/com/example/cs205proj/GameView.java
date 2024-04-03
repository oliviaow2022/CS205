package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;

import com.example.cs205proj.ui.PauseGameButton;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread gameThread;
    private final Player player;
    private final Enemies enemies;
    private final Joystick joystick;
    private final Paint paint = new Paint();
    private final Rect display;
    private final Score score;
    private final AttackButton playerAttackButton;
    private final PlayerHealth playerHealth;
    private Background background;
    private final PauseGameButton pauseButton;

    public GameView(Context context, Player player, Score score) {
        super(context);
        this.player = player;
        this.score = score;
        joystick = new Joystick(context);
        Hitbox playerHitbox = player.getHitbox();
        playerAttackButton = new AttackButton(playerHitbox);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
        display = windowMetrics.getBounds();

        playerHealth = new PlayerHealth();
        enemies = new Enemies(2, display, player, score, playerHealth);
        background = new Background(context, R.drawable.tile1, 64);
        pauseButton = new PauseGameButton(context);
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
        pauseButton.setDimensions(width, height);
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
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        joystick.onTouchEvent(event);
        if (pauseButton.onTouchEvent(event)) {
            gameThread.setRunning(false);
        }
        playerAttackButton.onTouchEvent(event);
        return true;
    }

    public void update(long deltaTime) {
        player.update(deltaTime, joystick, display);
        enemies.update(deltaTime, display);
    }

    @Override //draw game objects
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Calculate the offset to center the player on the screen
            int offsetX = display.width() / 2 - player.x;
            int offsetY = display.height() / 2 - player.y;
            background.draw(canvas, offsetX, offsetY, getWidth(), getHeight());


            // Save the current canvas state
            canvas.save();
    
            // Translate canvas to center on player
            canvas.translate(offsetX, offsetY);

            // Draw objects relative to the centered canvas
            player.draw(canvas, paint);
            enemies.draw(canvas, paint);

            // Restore the canvas state to original
            canvas.restore();

            int canvasHeight = getHeight();
            int canvasWidth = getWidth();

            playerHealth.draw(canvas, paint, canvasWidth);
            joystick.draw(canvas, paint, canvasHeight);
            playerAttackButton.draw(canvas, paint, canvasHeight, canvasWidth);
            score.draw(canvas, paint);
            pauseButton.draw(canvas);
        }
    }
} 