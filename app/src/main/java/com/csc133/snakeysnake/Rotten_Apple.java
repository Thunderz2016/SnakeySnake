package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/*The image for rotten apple is temp, will change it later*/
public class Rotten_Apple extends Drawable implements Spawnable {
    private Point location = new Point();
    private Point mSpawnRange;
    private int mSize;
    // An image to represent the apple
    private Bitmap mBitmapRottenApple;

    Rotten_Apple(Context context, Point sr, int s){

        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s;
        // Hide the apple off-screen
        location.set(-10,0);

        // Load the image to the bitmap
        mBitmapRottenApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.rot_apple);
        // Resize the bitmap
        mBitmapRottenApple = Bitmap.createScaledBitmap(mBitmapRottenApple, s, s, false);
    }

    @Override
    public void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    @Override
    public void spawn(int x, int y) {
        location.set(x,y);
    }

    // Let SnakeGame know where the apple is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapRottenApple,
                location.x * mSize, location.y * mSize, paint);

    }
}
