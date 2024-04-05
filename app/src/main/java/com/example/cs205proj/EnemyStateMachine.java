package com.example.cs205proj;

import android.graphics.Canvas;
import android.graphics.Paint;

public class EnemyStateMachine {
    private final Enemy enemy;
    private final EnemyWalkingState enemyWalkingState;
    private final EnemyShootState enemyShootState;
    BaseState currentState;

    public EnemyStateMachine(Enemy enemy, EnemyFrames enemyFrames) {
        this.enemy = enemy;

        enemyWalkingState = new EnemyWalkingState(enemy, enemyFrames);
        enemyShootState = new EnemyShootState(enemy, enemyFrames);
        currentState = enemyWalkingState;
    }

    public void changeState(String state) {
        if (state.equals("shoot")) {
            enemy.setWidth(140);
            currentState = enemyShootState;
        } else if (state.equals("walk")) {
            enemy.setWidth(120);
            currentState = enemyWalkingState;
        }
    }

    public void update(long deltaTime) {
        currentState.update(deltaTime);
    }

    public void draw(Canvas canvas, Paint paint) {
        currentState.draw(canvas, paint);
    }
}
