package com.csc133.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Drawable {
    private Canvas canvas;
    private Paint paint;

    public Drawable(Canvas canvas, Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
    }

    protected Drawable() {
    }

    public abstract void draw(Canvas canvas, Paint paint);
}
