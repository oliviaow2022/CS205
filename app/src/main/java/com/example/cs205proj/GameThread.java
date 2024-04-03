package com.example.cs205proj;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    private boolean paused;
    private long targetFPS = 50;

    private ElapsedTimer elapsedTimer = new ElapsedTimer();

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.paused = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setPause(){
        paused = true;
    }
    public void setResume(){
        paused = false;
    }

    @Override
    public void run() {
        long targetFrameTime = 1000 / targetFPS;

        while (running) {
            if(!paused) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(); // lock canvas for drawing
                    final long deltaTime = elapsedTimer.progress();
                    synchronized (surfaceHolder) {
                        if (deltaTime > 0) {
                            gameView.update(deltaTime);
                        }
                        gameView.draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                long updateEndTime = System.currentTimeMillis();
                long sleepTime = targetFrameTime - (updateEndTime - elapsedTimer.getUpdateStartTime());
                try {
                    if (sleepTime > 0)
                        sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try{
                    sleep(100);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
