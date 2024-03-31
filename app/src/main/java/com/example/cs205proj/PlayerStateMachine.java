package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerStateMachine {
    private final PlayerWalkingState playerWalkingState;
    private final PlayerSwingSwordState playerSwingSwordState;
    private final PlayerIdleState playerIdleState;
    private BaseState currentState;
    private final Player player;

    public PlayerStateMachine(Player player) {
        this.player = player;

        Context context = GlobalContext.getInstance().getContext();
        playerWalkingState = new PlayerWalkingState(context, player);
        playerSwingSwordState = new PlayerSwingSwordState(context, player);
        playerIdleState = new PlayerIdleState(context, player);
        currentState = playerIdleState;
    }

    public void update(long deltaTime) {
        currentState.update(deltaTime);
    }

    public void changeState(String state) {
        if (state.equals("idle")) {
            currentState = playerIdleState;
            player.width = 100;
        } else if (state.equals("walk")) {
            currentState = playerWalkingState;
            player.width = 100;
        } else if (state.equals("swing-sword")) {
            currentState = playerSwingSwordState;
            player.width = 200;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        currentState.draw(canvas, paint);
    }
}