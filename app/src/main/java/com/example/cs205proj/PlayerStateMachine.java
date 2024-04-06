package com.example.cs205proj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
* Player has 3 states: idle, walking, swing sword
* Transitions between states are done from the currently active state
*/
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

    // modify player widths and heights with state changes because the sprites have different aspect ratios
    public void changeState(String state) {
        if (state.equals("idle")) {
            currentState = playerIdleState;
            player.setWidth(100);
            player.setHeight(156);
        } else if (state.equals("walk")) {
            currentState = playerWalkingState;
            player.setWidth(100);
            player.setHeight(156);
        } else if (state.equals("swing-sword")) {
            currentState = playerSwingSwordState;
            player.setWidth(200);
            player.setHeight(200);
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        currentState.draw(canvas, paint);
    }
}