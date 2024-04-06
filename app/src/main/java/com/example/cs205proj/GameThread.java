package com.example.cs205proj;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
 * The game thread has three aspects.
 * Firstly, draw renders the game visually, with all the relevant sprites, animations and graphics
 * Secondly, update handles the game logic at every unit time delta
 * Thirdly, the thread sleeps between active periods to conserve system resources and maintain consistent frame rates
 */
public class GameThread extends Thread {
    final SurfaceHolder surfaceHolder;
    GameView gameView;
    private boolean running;
    private boolean paused;
    long targetFPS = 50;
    ElapsedTimer elapsedTimer = new ElapsedTimer();

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
        System.out.println("Exiting Thread!");
    }


}
