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
            frames[i] = Bitmap.createScaledBitmap(frame, player.width, player.height,true);
        }
    }

    public void update(long deltaTime) {
        if (Math.abs(player.velocityX) > 0 || Math.abs(player.velocityY) > 0) {
            player.playerStateMachine.changeState("walk");
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
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(frames[currentAnimation], null, new Rect(player.x, player.y, player.x + player.width, player.y + player.height), paint);
    }
}
