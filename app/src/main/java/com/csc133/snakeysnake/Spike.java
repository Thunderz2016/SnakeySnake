package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;
class Spike extends Drawable implements Spawnable{

    // The location of the spike on the grid
    // Not in pixels
    private Point location = new Point();

    // The range of values we can choose from
    // to spawn a spike
    private Point mSpawnRange;
    private int mSize;

    // An image to represent the spike
    private Bitmap mBitmapSpike;

    /// Set up the spike in the constructor
    Spike(Context context, Point sr, int s){

        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of a spike
        mSize = s;
        // Hide the spike off-screen until the game starts
        location.x = -10;

        // Load the image to the bitmap
        mBitmapSpike = BitmapFactory.decodeResource(context.getResources(), R.drawable.spikythorn);

        // Resize the bitmap
        mBitmapSpike = Bitmap.createScaledBitmap(mBitmapSpike, 150, 150, false);
    }

    // This is called every time a spike is eaten
    @Override
    public void spawn(){
        // Choose two random values and place the spike
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    @Override
    public void spawn(int x, int y) {
        return;
    }

    // Let SnakeGame know where the spike is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }

    // Draw the spike
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapSpike,
                location.x * mSize, location.y * mSize, paint);

    }

}

