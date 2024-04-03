package com.example.cs205proj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs205proj.GameOver;
import com.example.cs205proj.MainActivity;

public class PauseGameButton extends View{
    private int screenWidth, screenHeight;
    private Paint paint;
    private boolean isPaused = false;
    private Context context;
    private Bitmap pauseButtonImage;
    private int pauseButton_x_offset = 0;
    private static final int pauseButton_y_offset = 80;


    public PauseGameButton(Context context){
        super(context);
        this.context = context;

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        this.pauseButtonImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.pause_button);
    }

    public void setDimensions(int screenWidth, int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        int buttonWidth = 200;
        int buttonHeight = 100;
        int buttonLeft = (screenWidth - buttonWidth) / 2;
        int buttonTop = 0;
        int buttonRight = buttonLeft + buttonWidth;
        int buttonBottom = buttonTop + buttonHeight;
        setMeasuredDimension(buttonWidth, buttonRight);
        layout(buttonLeft, buttonTop, buttonRight, buttonBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.pauseButton_x_offset = screenWidth - pauseButtonImage.getWidth() - 100;
        canvas.drawBitmap(pauseButtonImage, pauseButton_x_offset, pauseButton_y_offset, paint);
    }

    @Override
    public boolean performClick() {
        isPaused = !isPaused;
        Intent intent = new Intent(context, GameOver.class);
        context.startActivity(intent);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (x >= pauseButton_x_offset && x <= pauseButton_x_offset + pauseButtonImage.getWidth()
                && y >= pauseButton_y_offset && y <= pauseButton_y_offset + pauseButtonImage.getHeight()) {
                // Click is within the bounds of the button
                isPaused = !isPaused;
                performClick();
                return true; // Consume the event
            }
        }
        return super.onTouchEvent(event);
    }
}
