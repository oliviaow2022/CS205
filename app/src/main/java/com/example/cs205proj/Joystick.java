package com.example.cs205proj;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Joystick {
    int joystickCenterX;
    int joystickCenterY;
    final int radius = 150; // Radius of the joystick
    int x = joystickCenterX;  //this is the varying coordinates of the joystick itself
    int y = joystickCenterY;
    Player player;

    Bitmap pad;
    Rect padSrcRect, padDstRect;

    Bitmap ball;
    Rect ballSrcRect, ballDstRect;
    int dirX = 0;
    int dirY = 0;

    float scaleFactor = (radius - 50) * 2.0f / 300;
    int scaledSpriteSize = (int) (250 * scaleFactor);


    public Joystick(Context context, Player thePlayer) {
        this.pad = BitmapFactory.decodeResource(context.getResources(), R.drawable.pad);
        this.ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        padSrcRect = new Rect(0, 0, pad.getWidth(), pad.getHeight());
        padDstRect = new Rect();
        ballSrcRect = new Rect(0, 0, ball.getWidth(), ball.getHeight());
        ballDstRect = new Rect();
        player = thePlayer;

    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();
        float distanceX = touchX - joystickCenterX;
        float distanceY = touchY - joystickCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (action == MotionEvent.ACTION_MOVE){
            if (distance <= radius) {
                x = (int) touchX;
                y = (int) touchY;
                dirX = x;
                dirY = y;
            }  else if (distance <= radius + 300){
                double angle = Math.atan2(distanceY, distanceX);
                x = (int) (joystickCenterX + (radius - 25) * Math.cos(angle));
                y = (int) (joystickCenterY + (radius - 25) * Math.sin(angle));
                dirX = x;
                dirY = y;
            }
        }
        if (action == MotionEvent.ACTION_UP){
            x = joystickCenterX;
            y = joystickCenterY;
        }
        return true;
    }

    public void draw(Canvas canvas, Paint paint, int height) {
        // player is currently a circle
        joystickCenterX = (int)(2*radius);
        joystickCenterY = (int)(height - 50 - 1.5*radius);
        float distanceX = x - joystickCenterX;
        float distanceY = y - joystickCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (distance > radius){ //this resets position of joystick when chaning orientation
            x = joystickCenterX;
            y = joystickCenterY;
        }

        // Draw joystick sprite
        ballDstRect.set(
                (int) (x - scaledSpriteSize / 1.5),
                (int) (y - scaledSpriteSize / 1.5),
                (int) (x + scaledSpriteSize / 1.5),
                (int) (y + scaledSpriteSize / 1.5)
        );
        padDstRect.set(
                (int) (joystickCenterX - scaledSpriteSize),
                (int) (joystickCenterY - scaledSpriteSize),
                (int) (joystickCenterX + scaledSpriteSize),
                (int) (joystickCenterY + scaledSpriteSize)
        );
        canvas.drawBitmap(pad, padSrcRect, padDstRect, null);
        canvas.drawBitmap(ball, ballSrcRect, ballDstRect, null);

    }



    public float[] getJoystickOffset(){
        float distanceX = dirX - joystickCenterX;
        float distanceY = dirY - joystickCenterY;
        float[] result = {distanceX,distanceY};
        return result;
    }
}
