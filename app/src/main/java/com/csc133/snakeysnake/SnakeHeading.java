package com.csc133.snakeysnake;

import android.graphics.*;
import android.view.MotionEvent;
import java.util.ArrayList;

public class SnakeHeading{
    private enum Heading {
        UP(1), RIGHT(2), DOWN(3), LEFT(4);
        private int headingValue;

        Heading(int headingValue){
            this.headingValue=headingValue;
        }

        public int getHeadingValue() {
            return headingValue;
        }

    }

    private Heading heading;
    Heading[] headingDirection=heading.values();

    void reset(){
        heading=headingDirection[2];
    }

    // Move the head in the appropriate heading
    void movement(Point p){
        switch (heading) {
            case UP:
                p.y--;
                break;

            case RIGHT:
                p.x++;
                break;

            case DOWN:
                p.y++;
                break;

            case LEFT:
                p.x--;
                break;
        }
    }

    // Handle changing direction
    void changeDirection(MotionEvent motionEvent, int halfWayPoint) {
        int headingIndex = heading.getHeadingValue();
        int Headinglimit=headingDirection.length;

        // Is the tap on the right hand side of the screen?
        if (motionEvent.getX() >= halfWayPoint) {
            // Rotate right, cycle back to 0 if index reaches 4 (array size)
            if (headingIndex >= Headinglimit) {
                headingIndex = 0;
            }
        }else {
            // Rotate left, cycle back to 3 (Left) if index reaches -1 (-1 isn't an array position)
            headingIndex=headingIndex-2;

            if (headingIndex < 0) {
                headingIndex = Headinglimit - 1;
            }
        }

        heading=headingDirection[headingIndex];
    }

    Bitmap switchHeading(SnakeBitmap HeadBitmap) {
        // Draw the head
        switch (heading) {
            case RIGHT:
                return HeadBitmap.getmBitmapHeadRight();

            case LEFT:
                return HeadBitmap.getmBitmapHeadLeft();

            case UP:
                return HeadBitmap.getmBitmapHeadUp();

            case DOWN:
                return HeadBitmap.getmBitmapHeadDown();

        }
        return HeadBitmap.getmBitmapHeadRight();
    }

    public Heading getHeading() {
        return heading;
    }
}