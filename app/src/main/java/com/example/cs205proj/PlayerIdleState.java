package com.example.cs205proj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Rect;

public class PlayerIdleState extends BaseState {
    Player player;
    Bitmap[] frames = new Bitmap[4];
    int currentAnimation = 0;

    public PlayerIdleState(Context context, Player player) {
        this.player = player;

        Bitmap spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.character_walk);

        int[] yCoordinate = {0, 90, 180, 250};

        for (int i = 0; i < yCoordinate.length; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheet, 0, yCoordinate[i], 45, 90); // (Bitmap, x, y, width, height)
            frames[i] = Bitmap.createScaledBitmap(frame, player.getWidth(), player.getHeight(),true);
        }
    }

    public void update(long deltaTime) {
        if (Math.abs(player.velocityX) > 0 || Math.abs(player.velocityY) > 0) {
            player.playerStateMachine.changeState("walk");
        }

        if (player.getDirection().equals("down")) {
            currentAnimation = 0;
        } else if (player.getDirection().equals("right")) {
            currentAnimation = 1;
        } else if (player.getDirection().equals("up")) {
            currentAnimation = 2;
        } else if (player.getDirection().equals("left")) {
            currentAnimation = 3;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(frames[currentAnimation], null, new Rect(player.getX(), player.getY(), player.getX() + player.getWidth(), player.getY() + player.getHeight()), paint);
    }
}
