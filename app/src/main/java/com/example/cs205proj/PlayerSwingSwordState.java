package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerSwingSwordState extends BaseState {
    Player player;
    Animation[] animations;
    int currentAnimation;

    public PlayerSwingSwordState(Context context, Player player) {
        this.player = player;
        player.width = 200;
        player.height = 200;

        Animation[] animations = new Animation[4];

        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_swing_sword);

        int[] xCoordinate = {0, 90, 180, 250};
        int[] yCoordinate = {0, 90, 180, 250};

        for (int i = 0; i < yCoordinate.length; i++) {
            Bitmap[] frames = new Bitmap[4];

            for (int j = 0; j < xCoordinate.length; j++) {
                Bitmap frame = Bitmap.createBitmap(spriteSheet, xCoordinate[j], yCoordinate[i], 90, 90); // (Bitmap, x, y, width, height)itmap frame = Bitmap.createBitmap(spriteSheet, xCoordinate[j], yCoordinate[i], 45, 90); // (Bitmap, x, y, width, height)
                frames[j] = Bitmap.createScaledBitmap(frame, player.width, player.height,true);
            }

            animations[i] = new Animation(frames, false, 200);
        }

        this.animations = animations;
    }

    public void update(long deltaTime){

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), null, new Rect(player.x, player.y, player.x + player.width, player.y + player.height), paint);
    }
}
