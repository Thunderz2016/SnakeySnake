package com.csc133.snakeysnake;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;
public class Pause {
    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();
    // An image to represent the apple
    private Bitmap mBitmapPause;
    /// Set up the apple in the constructor
    private int mSize;
    Pause(Context context, Point sr, int s){
    //Pause(Context context, Point sr){

        // Make a note of the passed in spawn range
        //mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s; //getting rid of this will cause the pause button to appear on top of score!
        //Have the button at start of the game
        location.x = 37;
        location.y = 0;

        // Load the image to the bitmap
        mBitmapPause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);

        // Resize the bitmap
        mBitmapPause = Bitmap.createScaledBitmap(mBitmapPause, 150, 150, false);
    }
    // Draw the button
    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmapPause,
                location.x * mSize, location.y * mSize, paint);
    }
}
