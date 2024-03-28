package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerWalkingState extends BaseState {
    Player player;
    Animation[] animations;
    int currentAnimation = 0;
    public PlayerWalkingState(Context context, Player player) {
        this.player = player;

        Animation[] animations = new Animation[4];

        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_walk);

        int[] xCoordinate = {0, 45, 90, 130};
        int[] yCoordinate = {0, 90, 180, 250};

        for (int i = 0; i < yCoordinate.length; i++) {
            Bitmap[] frames = new Bitmap[4];

            for (int j = 0; j < xCoordinate.length; j++) {
                Bitmap frame = Bitmap.createBitmap(spriteSheet, xCoordinate[j], yCoordinate[i], 45, 90); // (Bitmap, x, y, width, height)
                frames[j] = Bitmap.createScaledBitmap(frame, player.width, player.height,true);
            }

            animations[i] = new Animation(frames, true, 200);
        }

        this.animations = animations;

        // Bitmap bm = Bitmap.createBitmap(spriteSheet, 0, 250, 45, 90);
        // scaledBitmap = Bitmap.createScaledBitmap(bm, player.width, player.height, true);
    }

    public void update(long deltaTime) {
        if (player.velocityX == 0 && player.velocityY == 0) {
            player.playerStateMachine.changeState("idle");
        }

        if (player.direction.equals("down")) {
            currentAnimation = 0;
        } else if (player.direction.equals("right")) {
            currentAnimation = 1;
        } else if (player.direction.equals("up")) {
            currentAnimation = 2;
        } else if (player.direction.equals("left")) {
            currentAnimation = 3;
        }

        animations[currentAnimation].update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), null, new Rect(player.x, player.y, player.x + player.width, player.y + player.height), paint);
    }
}
