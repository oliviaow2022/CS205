package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerSwingSwordState extends BaseState {
    private final Player player;
    private final Animation[] animations;
    private int currentAnimation;
    Rect spriteRect;
    int xOffset = 50;

    public PlayerSwingSwordState(Context context, Player player) {
        this.player = player;

        Animation[] animations = new Animation[4];

        Bitmap[] swordDownFrames = new Bitmap[4];
        swordDownFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_down_1);
        swordDownFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_down_2);
        swordDownFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_down_3);
        swordDownFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_down_4);
        animations[0] = new Animation(swordDownFrames, true, 200);

        Bitmap[] swordRightFrames = new Bitmap[4];
        swordRightFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_right_1);
        swordRightFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_right_2);
        swordRightFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_right_3);
        swordRightFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_right_4);
        animations[1] = new Animation(swordRightFrames, true, 200);

        Bitmap[] swordUpFrames = new Bitmap[4];
        swordUpFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_up_1);
        swordUpFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_up_2);
        swordUpFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_up_3);
        swordUpFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_up_4);
        animations[2] = new Animation(swordUpFrames, true, 200);

        Bitmap[] swordLeftFrames = new Bitmap[4];
        swordLeftFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_left_1);
        swordLeftFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_left_2);
        swordLeftFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_left_3);
        swordLeftFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_left_4);
        animations[3] = new Animation(swordLeftFrames, true, 200);

        spriteRect = new Rect(0, 0, swordDownFrames[0].getWidth(), swordDownFrames[0].getHeight());

        this.animations = animations;
    }

    public void update(long deltaTime){
        if (player.getDirection().equals("down")) {
            currentAnimation = 0;
        } else if (player.getDirection().equals("right")) {
            currentAnimation = 1;
        } else if (player.getDirection().equals("up")) {
            currentAnimation = 2;
        } else if (player.getDirection().equals("left")) {
            currentAnimation = 3;
        }

        animations[currentAnimation].update(deltaTime);

        if (animations[currentAnimation].timesPlayed > 0) {
            animations[currentAnimation].timesPlayed = 0;
            player.getPlayerStateMachine().changeState("idle");
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), spriteRect, new Rect(player.getX() - xOffset, player.getY(), player.getX() - xOffset + player.getWidth(), player.getY() + player.getHeight()), paint);
    }
}