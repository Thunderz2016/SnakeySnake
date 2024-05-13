package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Charmer extends Drawable implements PowerUp {
    private Point location = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapCharmer;

    Charmer(Context context, Point sr, int s){

        mSpawnRange = sr;
        mSize = s;
        location.set(-10,0);

        // Load the image to the bitmap
        mBitmapCharmer = BitmapFactory.decodeResource(context.getResources(), R.drawable.charm);
        // Resize the bitmap
        mBitmapCharmer = Bitmap.createScaledBitmap(mBitmapCharmer, s, s, false);
    }

    @Override
    public void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    @Override
    public void location(int x, int y) {
        location.set(x,y);
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }


    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapCharmer,
                location.x * mSize, location.y * mSize, paint);

    }
}
