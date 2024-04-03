package com.example.cs205proj.ui;

import android.content.Context;
import android.content.Intent;
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
    public void updateDimensions(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    public PauseGameButton(Context context){
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
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
        int width = getWidth();
        int height = getHeight();
        canvas.drawRect(1000, 0, width + 1000, height, paint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(40); // Set text size
        textPaint.setTextAlign(Paint.Align.CENTER); // Align text to center

        float textX = width / 2f;
        float textY = height / 2f + textPaint.getTextSize() / 2f;
        canvas.drawText("End game", textX + 1000, textY, textPaint);
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
            if (x >= 1000 && x <= getWidth() + 1000 && y >= 0 && y <= getHeight()) {
                // Click is within the bounds of the button
                isPaused = !isPaused;
                performClick();
                return true; // Consume the event
            }
        }
        return super.onTouchEvent(event);
    }
}
