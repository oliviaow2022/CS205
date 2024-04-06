package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerWalkingState extends BaseState {
    private final Player player;
    private final Animation[] animations;
    private int currentAnimation = 0;
    private final Rect spriteRect;

    public PlayerWalkingState(Context context, Player player) {
        this.player = player;

        Animation[] animations = new Animation[4];

        Bitmap[] walkDownFrames = new Bitmap[4];
        walkDownFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_down_1);
        walkDownFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_down_2);
        walkDownFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_down_3);
        walkDownFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_down_4);
        animations[0] = new Animation(walkDownFrames, true, 200);

        Bitmap[] walkRightFrames = new Bitmap[4];
        walkRightFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_right_1);
        walkRightFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_right_2);
        walkRightFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_right_3);
        walkRightFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_right_4);
        animations[1] = new Animation(walkRightFrames, true, 200);

        Bitmap[] walkUpFrames = new Bitmap[4];
        walkUpFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_up_1);
        walkUpFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_up_2);
        walkUpFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_up_3);
        walkUpFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_up_4);
        animations[2] = new Animation(walkUpFrames, true, 200);

        Bitmap[] walkLeftFrames = new Bitmap[4];
        walkLeftFrames[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_left_1);
        walkLeftFrames[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_left_2);
        walkLeftFrames[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_left_3);
        walkLeftFrames[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.walk_left_4);
        animations[3] = new Animation(walkLeftFrames, true, 200);

        spriteRect = new Rect(0, 0, walkDownFrames[0].getWidth(), walkDownFrames[0].getHeight());

        this.animations = animations;
    }

    public void update(long deltaTime) {
        if (player.getVelocityX() == 0 && player.getVelocityY() == 0) {
            player.getPlayerStateMachine().changeState("idle");
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

        animations[currentAnimation].update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), spriteRect, new Rect(player.getX(), player.getY(), player.getX() + player.getWidth(), player.getY() + player.getHeight()), paint);
    }
}
