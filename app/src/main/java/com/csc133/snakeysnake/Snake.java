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
    private Point currentLocation;
    private Point previousLocation;
    private int mSegmentSize;
    private boolean dead=false;

    // How big is the entire grid
    private Point mMoveRange;

    // Where is the centre of the screen
    // horizontally in pixels?
    private int halfWayPoint;

    // For tracking movement Heading
    SnakeHeading snakeHeading;
    private Point snakeHeadLocation;

    // A bitmap for each direction the head can face
    SnakeBitmap snakeBitmap;

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }


    Snake(Context context, Point p, int blockSize) {
        // Initialize our ArrayList
        segmentLocations = new ArrayList<>();

        // Initialize the segment size and movement
        // range from the passed in parameters
        mSegmentSize = blockSize;
        mMoveRange = p;

        // The halfway point across the screen in pixels
        // Used to detect which side of screen was pressed
        halfWayPoint = p.x * blockSize / 2;

        // Create and scale the bitmaps
        snakeBitmap=new SnakeBitmap(context,blockSize);
        snakeHeading= new SnakeHeading();
    }

    @Override
    public void spawn() {}

    // Get the snake ready for a new game
    @Override
    public void spawn(int w, int h) {
        // Reset the heading
        snakeHeading.reset();
        // Delete the old contents of the ArrayList
        segmentLocations.clear();
        // Start with a single snake segment
        segmentLocations.add(new Point(w / 2, h / 2));
        snakeHeadLocation=segmentLocations.get(0);
    }
    /*
    Method that will update the length of the snake for the anti-power-ups
     */
    public void newLength(int w, int h) {

        //Get the length of the snake
        long length = segmentLocations.stream().count() - 7;

        snakeHeading.reset();
        // Delete the old contents of the ArrayList
        segmentLocations.clear();
        // Start with a single snake segment
        segmentLocations.add(new Point(w / 2, h / 2));
        snakeHeadLocation=segmentLocations.get(0);

        //Add segments to the arraylist based on the new length
        for (int i =0; i<length; i++){
            segmentLocations.add(new Point(-10, -10));
        }
        snakeHeadLocation=segmentLocations.get(0);
    }
    /*
    Method that will add three body segments to the snake whenever it eats a grape (power-up)
     */
    public void addLength(int w, int h) {
        segmentLocations.add(new Point(w / 2, h / 2));
        segmentLocations.add(new Point(w / 2, h / 2));
    }

    void move() {
        // Move the body
        // Start at the back and move it
        // to the position of the segment in front of it
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            currentLocation=segmentLocations.get(i);
            previousLocation=segmentLocations.get(i - 1);

            // Make it the same value as the next segment
            // going forwards towards the head
            currentLocation.set(previousLocation.x,previousLocation.y);

        }

        // Move the head in the appropriate heading
        snakeHeading.movement(snakeHeadLocation);
    }

    boolean detectDeath() {
        // Hit any of the screen edges
        if (hitScreenEdge()) {
            dead = true;
        }

        // Eaten itself?
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            currentLocation=segmentLocations.get(i);
            // Have any of the sections collided with the head
            if (snakeHeadLocation.equals(currentLocation)){

                dead = true;
            }
        }
        return dead;
    }
    //Eaten the bad apple?
    void resetLength(){

    }
    // Eaten the spike?
    boolean detectDeath(Point l) {
        boolean dead = false;
        //if the head touches the spike then kill off the snake
        if(snakeHeadLocation.equals(l)){
            dead = true;
        }
        return dead;
    }

    boolean hitScreenEdge() {
       return snakeHeadLocation.x == -1
               || snakeHeadLocation.y == -1
               || snakeHeadLocation.x > mMoveRange.x
               || snakeHeadLocation.y > mMoveRange.y;
    }


    boolean checkDinner(Point l) {
        if (snakeHeadLocation.equals(l)){

            // Add a new Point to the list located off-screen.
            // This is OK because on the next call to move it will take the position of
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
            drawBit(direction, snakeHeadLocation, canvas, paint);

            // Draw the snake body one block at a time
            for (int i = 1; i < segmentLocations.size(); i++) {
                currentLocation=segmentLocations.get(i);
                drawBit(snakeBitmap.getmBitmapBody(), currentLocation, canvas, paint);
            }
        }
    }

    void drawBit(Bitmap headDirection, Point location, Canvas canvas, Paint paint){
        canvas.drawBitmap(headDirection, location.x * mSegmentSize,
                location.y * mSegmentSize, paint);
    }


    // Handle changing direction
    void changeDirection(MotionEvent motionEvent){
        snakeHeading.changeDirection(motionEvent, halfWayPoint);
    }
}
