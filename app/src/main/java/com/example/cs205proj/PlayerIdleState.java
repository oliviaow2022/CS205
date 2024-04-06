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
    int spriteWidth;
    int spriteHeight;

    public PlayerIdleState(Context context, Player player) {
        this.player = player;

        frames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_down_1);
        frames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_right_1);
        frames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_up_1);
        frames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_left_1);

        spriteWidth = frames[0].getWidth();
        spriteHeight = frames[1].getHeight();
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
        canvas.drawBitmap(frames[currentAnimation], new Rect(0, 0, spriteWidth, spriteHeight), new Rect(player.getX(), player.getY(), player.getX() + player.getWidth(), player.getY() + player.getHeight()), paint);
    }
}
