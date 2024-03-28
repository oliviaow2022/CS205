package com.example.cs205proj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerHealth {
    private final Bitmap scaledFullHeart;
    private final Bitmap scaledEmptyHeart;
    private int x;
    private int y;
    private final Player player;

    public PlayerHealth(Player player) {
        this.player = player;

        Context context = GlobalContext.getInstance().getContext();
        Bitmap heartsSpriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.hearts);
        Bitmap emptyHeart = Bitmap.createBitmap(heartsSpriteSheet, 2, 0, 43, 43);
        Bitmap fullHeart = Bitmap.createBitmap(heartsSpriteSheet, 178, 0, 43, 43);
        scaledFullHeart = Bitmap.createScaledBitmap(fullHeart, 100, 100,true);
        scaledEmptyHeart = Bitmap.createScaledBitmap(emptyHeart, 100, 100, true);
    }

    public void draw(Canvas canvas, Paint paint, int canvasWidth) {
        this.x = 100;
        this.y = 150;

        for (int i = 0; i < player.health; i++) {
            canvas.drawBitmap(scaledFullHeart, null, new Rect(x + 100*i, y, x + 100*(i+1), y+100), paint);
        }
        for (int i = player.health; i < player.maxHealth; i++) {
            canvas.drawBitmap(scaledEmptyHeart, null, new Rect(x+100*i, y, x+100*(i+1), y+100), paint);
        }
    }
}
