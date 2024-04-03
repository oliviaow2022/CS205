package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Background {
    private Bitmap spriteSheet;
    private Rect srcRect;
    private int tileSize;

    int mapWidth = 4000;
    int mapHeight = 2000;

    public Background(Context context, int drawableId, int tileSize) {
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), drawableId);
        this.tileSize = tileSize;
        int left = 0;
        int top = 0;
        int right = tileSize;
        int bottom = tileSize;
        srcRect = new Rect(left, top, right, bottom);
    }

    public void draw(Canvas canvas, int offsetX, int offsetY) {
        // Calculate the number of tiles needed to cover the screen
        int numTilesX = (mapWidth + tileSize - 1) / tileSize;
        int numTilesY = (mapHeight + tileSize - 1) / tileSize;

        // Draw the tiles to cover the entire screen
        for (int i = 0; i < numTilesX; i++) {
            for (int j = 0; j < numTilesY; j++) {
                int left = offsetX + i * tileSize;
                int top = offsetY + j * tileSize;
                int right = left + tileSize;
                int bottom = top + tileSize;
                Rect destRect = new Rect(left, top, right, bottom);
                canvas.drawBitmap(spriteSheet, srcRect, destRect, new Paint());
            }
        }
    }
}