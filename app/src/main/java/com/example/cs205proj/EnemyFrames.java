package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class EnemyFrames {
    Bitmap[] leftFrames = new Bitmap[4];
    Bitmap[] rightFrames = new Bitmap[4];
    int spriteWalkWidth;
    int spriteWalkHeight;
    Rect spriteRect;
    Bitmap[] leftShootFrames = new Bitmap[4];
    Bitmap[] rightShootFrames = new Bitmap[4];

    public EnemyFrames() {
        Context context = GlobalContext.getInstance().getContext();

        rightFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_right_1);
        rightFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_right_2);
        rightFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_right_3);
        rightFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_right_4);

        leftFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_left_1);
        leftFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_left_2);
        leftFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_left_3);
        leftFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.robot_left_4);

        rightShootFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_right_1);
        rightShootFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_right_2);
        rightShootFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_right_3);
        rightShootFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_right_4);

        leftShootFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_left_1);
        leftShootFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_left_2);
        leftShootFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_left_3);
        leftShootFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot_left_4);

        spriteWalkWidth = rightFrames[0].getWidth();
        spriteWalkHeight = rightFrames[0].getHeight();
        spriteRect = new Rect(0,0, spriteWalkWidth, spriteWalkHeight);
    }

    public Rect getSpriteRect() {
        return this.spriteRect;
    }
}
