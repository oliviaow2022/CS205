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
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;

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
    private Context context;

    public GameView(Context context, Player player, Score score) {
        super(context);
        this.context = context;
        this.player = player;
        this.score = score;
        joystick = new Joystick(context, player);
        Hitbox playerHitbox = player.getHitbox();
        playerAttackButton = new AttackButton(playerHitbox);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
        display = windowMetrics.getBounds();

        playerHealth = new PlayerHealth();
        enemies = new Enemies(2, display, player, score, playerHealth, background);
        background = new Background(context, 64);
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
                enemies.end();
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        joystick.onTouchEvent(event);
        if(pauseButton.onTouchEvent(event)){
            pauseGame();
        }
        playerAttackButton.onTouchEvent(event);
        return true;
    }

    public void update(long deltaTime) {
        player.update(deltaTime, joystick, background);
        enemies.update(deltaTime, display);
        if (playerHealth.getHealth() <= 0) {
            gameThread.setRunning(false);
            Intent intent = new Intent(getContext(), GameOver.class);
            getContext().startActivity(intent);
        }
        if (Rect.intersects(background.getGoalRect(), player.getRect())) {
            System.out.println("GOALLLLLL");
            gameThread.setRunning(false);
            System.out.println("game stopped running");
            Intent intent = new Intent(getContext(), GameWin.class);
            getContext().startActivity(intent);
        }
    }
    public void pauseGame() {
        gameThread.setPause();
        showPauseDialog(context);
    }

    public void resumeGame() {
        gameThread.setResume();
    }

    public void stopGame() {
        enemies.end();
        gameThread.setRunning(false);
    }

    private void showPauseDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Game Paused");
        builder.setMessage("Choose an option:");
        builder.setPositiveButton("Resume", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Resume the game
                dialog.dismiss();
                resumeGame();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Quit the game
                Intent intent = new Intent(context, GameOver.class);
                context.startActivity(intent);
                stopGame();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override //draw game objects
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Calculate the offset to center the player on the screen
            int offsetX = display.width() / 2 - player.getX();
            int offsetY = display.height() / 2 - player.getY();
            background.draw(canvas, paint, offsetX, offsetY);

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

            playerHealth.draw(canvas, paint);
            joystick.draw(canvas, paint, canvasHeight);
            playerAttackButton.draw(canvas, paint, canvasHeight, canvasWidth);
            score.draw(canvas, paint);
            pauseButton.draw(canvas);
        }
    }
} 