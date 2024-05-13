package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;
/*
    Grape is one of the power-ups in the game
    By eating a grape every 7 score the energy level/score will be increased by 3
 */
public class EnergyBoost extends Drawable implements PowerUp{

    // The location of the grape on the grid
    private Point location = new Point();

    private Point mSpawnRange;
    private int mSize;

    // grape image
    private Bitmap mBitmapGrape;

    //Set up the grape in the constructor
    EnergyBoost(Context context, Point sr, int s){

        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of the grape
        mSize = s;
        // Hide the grape off-screen until the game starts
        location.x = -10;

        // Load the image to the bitmap
        mBitmapGrape = BitmapFactory.decodeResource(context.getResources(), R.drawable.grape);

        // Resize the bitmap
        mBitmapGrape = Bitmap.createScaledBitmap(mBitmapGrape, 80, 80, false);
    }

    // This is called every time a grape is eaten
    @Override
    public void spawn(){
        // Choose two random values and place the grape
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    @Override
    public void location(int x, int y) {
        return;
    }

    // Let SnakeGame know where the grape is
    // SnakeGame can share this with the snake
    Point getLocation(){
        return location;
    }

    // Draw the grape
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapGrape,
                location.x * mSize, location.y * mSize, paint);

    }

}