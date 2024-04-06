package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/*
 * In the walking state, the enemy shoots a projectile with a specified probability
 * The projectile will be added to the enemy's ArrayList
 * and the enemy transitions to the shoot state
 */
public class EnemyWalkingState extends BaseState {
    int currentAnimation = 0;
    Enemy enemy;
    Animation[] animations;
    Rect spriteRect;

    public EnemyWalkingState(Enemy enemy, EnemyFrames enemyFrames) {
        this.enemy = enemy;
        this.spriteRect = enemyFrames.getSpriteRect();

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
        canvas.drawBitmap(animations[currentAnimation].getCurrentFrame(), spriteRect, new Rect(enemy.getX(), enemy.getY(), enemy.getX() + enemy.getWidth(), enemy.getY() + enemy.getHeight()), paint);
    }
}
