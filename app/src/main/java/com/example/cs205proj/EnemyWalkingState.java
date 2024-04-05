package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class EnemyWalkingState extends BaseState {
    int currentAnimation = 0;
    Enemy enemy;
    Animation[] animations;

    public EnemyWalkingState(Enemy enemy, EnemyFrames enemyFrames) {
        this.enemy = enemy;

        Animation[] walkingAnimation = {new Animation(enemyFrames.rightFrames, true, 200), new Animation(enemyFrames.leftFrames, true, 200)};
        this.animations = walkingAnimation;
    }

    public void update(long deltaTime) {
        if (enemy.getDirection().equals("left")) {
            currentAnimation = 1;
        } else if (enemy.getDirection().equals("right")) {
            currentAnimation = 0;
        }

        // generate projectile with certain probability
        double randomNumber = Math.random();
        if (randomNumber < 0.05 && (enemy.getDirection().equals("left") || enemy.getDirection().equals("right"))) {
            enemy.projectileList.add(new Projectile(enemy.getX() - 50, enemy.getY() + (enemy.getHeight() / 2), enemy.getDirection(), enemy.player, enemy.playerHealth));
            enemy.enemyStateMachine.changeState("shoot");
        }

        animations[currentAnimation].update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), null, new Rect(enemy.getX(), enemy.getY(), enemy.getX() + enemy.getWidth(), enemy.getY() + enemy.getHeight()), paint);
    }
}
