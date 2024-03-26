package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerStateMachine {
    private final PlayerWalkingState playerWalkingState;
    private final PlayerSwingSwordState playerSwingSwordState;
    private BaseState currentState;

    public PlayerStateMachine(Player player) {
        Context context = GlobalContext.getInstance().getContext();
        playerWalkingState = new PlayerWalkingState(context, player);
        playerSwingSwordState = new PlayerSwingSwordState(context, player);
        currentState = playerWalkingState;
    }

    public void update(long deltaTime) {
        // playerWalkingState.update(deltaTime);
    }

    public void changeState(String state) {
        if (state.equals("walk")) {
            currentState = playerWalkingState;
        } else if (state.equals("swing-sword")) {
            currentState = playerSwingSwordState;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        // currentState.draw(canvas, paint);
    }
}
