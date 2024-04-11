package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.*;

public class SnakeBitmap {
    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;

    // A bitmap for the body
    private Bitmap mBitmapBody;

    Matrix matrix = new Matrix();

    SnakeBitmap(Context context, int ss){
        // Create and scale the bitmaps
        initializeBitmap(context);

        // Modify the bitmaps to face the snake head in the correct direction
        setmBitmapHeadRight(initializeMatrix(mBitmapHeadRight,ss,false));

        // A matrix for scaling
        matrix.preScale(-1, 1);
        setmBitmapHeadLeft(initializeMatrix(mBitmapHeadLeft,ss,true));

        // A matrix for rotating
        matrix.preRotate(-90);
        setmBitmapHeadUp(initializeMatrix(mBitmapHeadUp,ss,true));

        // Matrix operations are cumulative
        // so rotate by 180 to face down
        matrix.preRotate(180);
        setmBitmapHeadDown(initializeMatrix(mBitmapHeadDown,ss,true));


        mBitmapBody = Bitmap.createScaledBitmap(mBitmapBody,
                        ss, ss, false);


    }
    void initializeBitmap (Context context){
        mBitmapBody = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.body);

        setmBitmapHeadRight(initializeHead(context));
        setmBitmapHeadLeft(initializeHead(context));
        setmBitmapHeadUp(initializeHead(context));
        setmBitmapHeadDown(initializeHead(context));
    }

    Bitmap initializeHead(Context context){
        return BitmapFactory.decodeResource(
                context.getResources(), R.drawable.head);
    }

    Bitmap initializeMatrix(Bitmap direction, int ss, boolean used){
        if(direction == mBitmapHeadRight){
            return Bitmap.createScaledBitmap(mBitmapHeadRight,
                    ss, ss, false);
        }
        return Bitmap.createBitmap(mBitmapHeadRight,
                0, 0, ss, ss, matrix, used);
    }

    public Bitmap getmBitmapHeadRight() {
        return mBitmapHeadRight;
    }

    public void setmBitmapHeadRight(Bitmap mBitmapHeadRight) {
        this.mBitmapHeadRight = mBitmapHeadRight;
    }

    public Bitmap getmBitmapHeadLeft() {

        return mBitmapHeadLeft;
    }

    public void setmBitmapHeadLeft(Bitmap mBitmapHeadLeft) {
        this.mBitmapHeadLeft = mBitmapHeadLeft;
    }

    public Bitmap getmBitmapHeadUp() {
        return mBitmapHeadUp;
    }

    public void setmBitmapHeadUp(Bitmap mBitmapHeadUp) {
        this.mBitmapHeadUp = mBitmapHeadUp;
    }

    public Bitmap getmBitmapHeadDown() {
        return mBitmapHeadDown;
    }

    public void setmBitmapHeadDown(Bitmap mBitmapHeadDown) {
        this.mBitmapHeadDown = mBitmapHeadDown;
    }

    public Bitmap getmBitmapBody() {
        return mBitmapBody;
    }

    public void setmBitmapBody(Bitmap mBitmapBody) {
        this.mBitmapBody = mBitmapBody;
    }

}