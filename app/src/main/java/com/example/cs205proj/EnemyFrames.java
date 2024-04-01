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
    Bitmap[] leftShootFrames = new Bitmap[4];
    Bitmap[] rightShootFrames = new Bitmap[4];

    public EnemyFrames() {
        Context context = GlobalContext.getInstance().getContext();
        Bitmap spriteSheetRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_robot);
        int[] xCoordinateRight = {80, 340, 610, 870};

        for (int i = 0; i < xCoordinateRight.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheetRight, xCoordinateRight[i], 0, 120, 200);
            rightFrames[i] = Bitmap.createScaledBitmap(frame, 120, 200, true);
        }

        int[] xCoordinateRightShoot = {70, 340, 600, 870};

        for (int i = 0; i < xCoordinateRightShoot.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheetRight, xCoordinateRightShoot[i], 520, 145, 200);
            rightShootFrames[i] = Bitmap.createScaledBitmap(frame, 145, 200, true);
        }

        Bitmap spriteSheetLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_robot_flip);
        int[] xCoordinateLeft = {1640, 1910, 2170, 2430};

        for (int i = 0; i < xCoordinateLeft.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheetLeft, xCoordinateLeft[i], 0, 145, 200);
            leftFrames[i] = Bitmap.createScaledBitmap(frame, 145, 200, true);
        }

        int[] xCoordinateLeftShoot = {1640, 1910, 2170, 2430};

        for (int i = 0; i < xCoordinateLeftShoot.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheetLeft, xCoordinateLeftShoot[i], 520, 145, 200);
            leftShootFrames[i] = Bitmap.createScaledBitmap(frame, 145, 200, true);
        }
    }
}
