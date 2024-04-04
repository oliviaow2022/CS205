package com.example.cs205proj;

public class Entity {
    int x;
    int y;
    int width;
    int height;
    int velocityX;
    int velocityY;
    String direction = "down";

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }

    public int getVelocityX(){
        return this.velocityX;
    }

    public int getVelocityY() {
        return this.velocityY;
    }
    
    public String getDirection() {
        return this.direction;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
