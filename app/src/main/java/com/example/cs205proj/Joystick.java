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
    int Height;
    int x = 500;
    int y = 2000;

    Bitmap pad;
    Rect padSrcRect, padDstRect;

    Bitmap ball;
    Rect ballSrcRect, ballDstRect;

    float scaleFactor = (radius - 50) * 2.0f / 300;
    int scaledSpriteSize = (int) (300 * scaleFactor);


    public Joystick(Context context) {
        this.pad = BitmapFactory.decodeResource(context.getResources(), R.drawable.pad);
        this.ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        padSrcRect = new Rect(0, 0, pad.getWidth(), pad.getHeight());
        padDstRect = new Rect();
        ballSrcRect = new Rect(0, 0, ball.getWidth(), ball.getHeight());
        ballDstRect = new Rect();

    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();
        float distanceX = touchX - joystickCenterX;
        float distanceY = touchY - joystickCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (action == MotionEvent.ACTION_MOVE){
            if (distance <= radius - 50){
                x = (int) touchX;
                y = (int) touchY;
            } else {
                double angle = Math.atan2(distanceY, distanceX);
                x = (int) (joystickCenterX + (radius - 50) * Math.cos(angle));
                y = (int) (joystickCenterY + (radius - 50) * Math.sin(angle));
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
        this.Height = height;
        joystickCenterX = 50 + 3*radius;
        joystickCenterY = (int)(height - 50 - 1.5*radius);
        float distanceX = x - joystickCenterX;
        float distanceY = y - joystickCenterY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if (distance > radius){
            x = joystickCenterX;
            y = joystickCenterY;
        }
//        paint.setColor(Color.WHITE);
//        canvas.drawCircle(joystickCenterX, joystickCenterY, radius, paint);
//        paint.setColor(Color.BLACK);
//        canvas.drawCircle(x,y,75, paint); // Draw center of joyStick

        // Draw joystick sprite
        ballDstRect.set(
                (int) (x - scaledSpriteSize / 2),
                (int) (y - scaledSpriteSize / 2),
                (int) (x + scaledSpriteSize / 2),
                (int) (y + scaledSpriteSize / 2)
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
        joystickCenterX = 50 + radius;
        joystickCenterY = Height - 50 - radius;
        float distanceX = x - joystickCenterX;
        float distanceY = y - joystickCenterY;
        float[] result = {distanceX,distanceY};
        return result;
    }
}
