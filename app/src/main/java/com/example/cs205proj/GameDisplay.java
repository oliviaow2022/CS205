package com.example.cs205proj;

public class GameDisplay {

    private double displayOffsetX;
    private double displayOffsetY;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private Player player;

    public GameDisplay(int width, int height, Player player) {
        this.player = player;
        displayCenterX = width / 2.0;
        displayCenterY = height / 2.0;
    }

    public void update() {
        gameCenterX = player.x;
        gameCenterY = player.y;
        displayOffsetX = displayCenterX - gameCenterX;
        displayOffsetY = displayCenterY - gameCenterY;

    }
    public double displayX (double x) {
        return x + displayOffsetX;
    }

    public double displayY (double y) {
        return y + displayOffsetY;
    }
}
