package com.example.cs205proj;

import android.graphics.Bitmap;

/*
 * The Animation class cycles through an array of frames
 * with a specified interval
 * and boolean looping on whether to loop the animation indefinitely
 */

public class Animation {
    private final Bitmap[] frames;
    private final boolean looping;
    private final double interval;
    private long timer = 0L;
    private int currentFrame = 0;
    int timesPlayed = 0;

    public Animation(Bitmap[] frames, boolean looping, double interval) {
        this.frames = frames;
        this.looping = looping;
        this.interval = interval;
    }

    public void update(long deltaTime) {
        if (!looping && timesPlayed > 0) {
            return;
        }

        timer += deltaTime;

        if (timer > interval) {
            timer %= interval;

            currentFrame = Math.max(0, (currentFrame + 1) % (frames.length));

            if (currentFrame == 1) {
                timesPlayed++;
            }
        }
    }

    public Bitmap getCurrentFrame() {
        return frames[currentFrame];
    }
}
