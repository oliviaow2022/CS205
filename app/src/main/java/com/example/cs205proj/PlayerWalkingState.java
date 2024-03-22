package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerWalkingState {
    Player player;
    Bitmap spriteSheet;
    final int frameHeight = 32;
    final int frameWidth = 16;
    Animation downAnimation;

    Bitmap frame2;
    Bitmap scaledBitmap;
    public PlayerWalkingState(Context context, Player player) {
        this.player = player;

        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_walk);
        Bitmap[] downFrames = new Bitmap[4];

        for (int i = 0; i < 4; i++) {
            downFrames[i] = Bitmap.createBitmap(spriteSheet, i*frameWidth, 0, frameWidth, frameHeight); // (Bitmap, x, y, width, height)
        }

        downAnimation = new Animation(downFrames, true, 2);
        // frame2 = Bitmap.createBitmap(spriteSheet, 16, 32, 16, 32); // (Bitmap, x, y, width, height)
        // scaledBitmap = Bitmap.createScaledBitmap(frame2, player.width, player.height,true);
    }

    public void update(long deltaTime) {
        downAnimation.update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(downAnimation.getCurrentFrame(), null, new Rect(player.x, player.y, player.x + player.width, player.y + player.height), paint);
    }
}
