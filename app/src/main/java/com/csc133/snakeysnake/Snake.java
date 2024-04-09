package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;


import java.util.ArrayList;

class Snake extends Drawable implements Spawnable{

    // The location in the grid of all the segments
    private ArrayList<Point> segmentLocations;

    // How big is each segment of the snake?
    private int mSegmentSize;

    // How big is the entire grid
    private Point mMoveRange;

    // Where is the centre of the screen
    // horizontally in pixels?
    private int halfWayPoint;

    // For tracking movement Heading
    SnakeHeading snakeHeading;

    // A bitmap for each direction the head can face
    SnakeBitmap snakeBitmap;

    Snake(Context context, Point mr, int ss) {

        // Initialize our ArrayList
        segmentLocations = new ArrayList<>();

        // Initialize the segment size and movement
        // range from the passed in parameters
        mSegmentSize = ss;
        mMoveRange = mr;

        // Create and scale the bitmaps
        snakeBitmap=new SnakeBitmap(context,ss);
        snakeHeading= new SnakeHeading();

        // The halfway point across the screen in pixels
        // Used to detect which side of screen was pressed
        halfWayPoint = mr.x * ss / 2;
    }

    @Override
    public void spawn() {
        return;
    }

    // Get the snake ready for a new game
    @Override
    public void spawn(int w, int h) {

        // Reset the heading
        snakeHeading.reset();

        // Delete the old contents of the ArrayList
        segmentLocations.clear();

        // Start with a single snake segment
        segmentLocations.add(new Point(w / 2, h / 2));
    }


    void move() {
        // Move the body
        // Start at the back and move it
        // to the position of the segment in front of it
        for (int i = segmentLocations.size() - 1; i > 0; i--) {

            // Make it the same value as the next segment
            // going forwards towards the head
            segmentLocations.get(i).x = segmentLocations.get(i - 1).x;
            segmentLocations.get(i).y = segmentLocations.get(i - 1).y;
        }

        // Move the head in the appropriate heading
        Point p = segmentLocations.get(0);
        snakeHeading.movement(p);
    }

    boolean detectDeath() {
        // Has the snake died?
        boolean dead = false;

        // Hit any of the screen edges
        if (segmentLocations.get(0).x == -1 ||
                segmentLocations.get(0).x > mMoveRange.x ||
                segmentLocations.get(0).y == -1 ||
                segmentLocations.get(0).y > mMoveRange.y) {

            dead = true;
        }

        // Eaten itself?
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            // Have any of the sections collided with the head
            if (segmentLocations.get(0).x == segmentLocations.get(i).x &&
                    segmentLocations.get(0).y == segmentLocations.get(i).y) {

                dead = true;
            }
        }
        return dead;
    }

    boolean checkDinner(Point l) {
        //if (snakeXs[0] == l.x && snakeYs[0] == l.y) {
        if (segmentLocations.get(0).x == l.x &&
                segmentLocations.get(0).y == l.y) {

            // Add a new Point to the list
            // located off-screen.
            // This is OK because on the next call to
            // move it will take the position of
            // the segment in front of it
            segmentLocations.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // Don't run this code if ArrayList has nothing in it
        if (!segmentLocations.isEmpty()) {
            // Draw the head
            Bitmap direction = snakeHeading.switchHeading(snakeBitmap);
            drawBit(direction, 0, canvas, paint);

            // Draw the snake body one block at a time
            for (int i = 1; i < segmentLocations.size(); i++) {
                drawBit(snakeBitmap.getmBitmapBody(), i, canvas, paint);
            }
        }
    }

    void drawBit(Bitmap headDirection, int location, Canvas canvas, Paint paint){
        canvas.drawBitmap(headDirection,
                segmentLocations.get(location).x
                        * mSegmentSize,
                segmentLocations.get(location).y
                        * mSegmentSize, paint);
    }


    // Handle changing direction
    void changeDirection(MotionEvent motionEvent){
        snakeHeading.changeDirection(motionEvent, halfWayPoint);
    }
}
