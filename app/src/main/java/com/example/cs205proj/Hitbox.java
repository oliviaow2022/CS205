package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Hitbox extends Entity {
    Rect rect;
    private Player player;
    long timeActivated;
    boolean isActivated = false;
    boolean stayAtPosition;
    Bitmap reticle;
    Bitmap reticleActive;

    public Hitbox(Context context, Player player) {
        super();
        this.player = player;
        rect = new Rect(x, y, x + width, y + height); // create rectangle object for drawing and collision
        this.x = player.getX();
        this.y = player.getY();
        this.width = 150;
        this.height = 150;
        this.velocityX = 0;
        this.velocityY = 0;
        this.reticle = BitmapFactory.decodeResource(context.getResources(), R.drawable.reticle1);
        this.reticleActive = BitmapFactory.decodeResource(context.getResources(), R.drawable.reticle);
        this.stayAtPosition = false;

    }

    public void draw(Canvas canvas, Paint paint) {

        // Draw the sprite
        if (reticle != null && reticleActive != null) {
            Rect destRect = new Rect(x, y, x + width, y + height);
            if (isActivated) {
                canvas.drawBitmap(reticleActive, null, destRect, paint);
            } else {
                canvas.drawBitmap(reticle, null, destRect, paint);
            }
        }
    }

    public void update(Joystick joystick) { // updates the hitbox position to correspond to being next to player
        float[] offsets = joystick.getJoystickOffset();

        double joystickOffsetX = offsets[0];
        double joystickOffsetY = offsets[1];
    
        // Calculate angle of the joystick offset vector
        double angle = Math.atan2(joystickOffsetY, joystickOffsetX);
    
        // Calculate hitbox position based on joystick offset and angle
        int finalOffsetX = (int) (Math.cos(angle) * 50);
        int finalOffsetY = (int) (Math.sin(angle) * 50);

        if (finalOffsetX < 0){
            finalOffsetX -= 100;
        }
        if (finalOffsetY < 0){
            finalOffsetY -= 100;
        }
    
        // Calculate hitbox position relative to player's position
        int offsetX = player.getX() + finalOffsetX + 20;
        int offsetY = player.getY() + finalOffsetY + 110;
    
        // Update hitbox position
        this.x = offsetX;
        this.y = offsetY;
        if (System.currentTimeMillis() - timeActivated >= 300){
            if (isActivated == true){
                System.out.println("HITBOX DEACTIVATED");
            }
            isActivated = false;
        }
        if (this.stayAtPosition){
            return;
        }
        rect.set(x, y, x + width, y + height);
    }

    public void activateHitbox(){
        timeActivated = System.currentTimeMillis();
        isActivated = true;
        player.playerStateMachine.changeState("swing-sword");
    }
}
