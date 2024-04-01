package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class EnemyShootState extends BaseState {

    int currentAnimation = 0;
    Enemy enemy;
    Animation[] animations;

    public EnemyShootState(Enemy enemy, EnemyFrames enemyFrames) {
        this.enemy = enemy;

        Animation[] shootingAnimation = {new Animation(enemyFrames.rightShootFrames, true, 200), new Animation(enemyFrames.leftShootFrames, true, 200)};
        this.animations = shootingAnimation;
    }

    public void update(long deltaTime) {
        if (enemy.direction.equals("left")) {
            currentAnimation = 1;
        }

        if (animations[currentAnimation].timesPlayed > 0) {
            animations[currentAnimation].timesPlayed = 0;
            enemy.enemyStateMachine.changeState("walk");
        }

        animations[currentAnimation].update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), null, new Rect(enemy.x, enemy.y, enemy.x + enemy.width, enemy.y + enemy.height), paint);
    }
}
