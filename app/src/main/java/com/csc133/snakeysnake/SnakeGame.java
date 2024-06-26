package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

class SnakeGame extends SurfaceView implements Runnable{

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    private volatile boolean mHomeScreen=true;

    private volatile boolean mDead=false;
    private volatile boolean mManualPaused = false;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;
    private int cycles;

    // How many points does the player have
    private int mScore;

    private int halfway;
    private HighScore mHighScore;

    // Objects for drawing
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;

    // A snake ssss
    private Snake mSnake;
    // And an apple
    private Apple mApple;
    //Grape
    private EnergyBoost mGrape;
    private Rotten_Apple mRotApple;
    private Charmer mCharmer;
    private Spike mSpike;
    private Context mContext;
    private Random mRandom = new Random();

    HUD mHUD;
    ButtonController mButtonController;


    // This is the constructor method that gets called
    // from SnakeActivity
    public SnakeGame(Context context, Point size) {
        super(context);

        this.mContext = context;



        mButtonController = new ButtonController(this);
        mHighScore = HighScore.getInstance(context);

        // Work out how many pixels each block is
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        Audio.initialize(context);

        // Initialize the drawing objects
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        Point p= new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        halfway = p.x*blockSize/ 2;


        //mHUD = new HUD(size);
        // Call the constructors of our game objects
        mApple = new Apple(context, p, blockSize);

        mGrape = new EnergyBoost(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);

        mSnake = new Snake(context,p, blockSize);

        mRotApple=new Rotten_Apple(context,p,blockSize);

        mCharmer= new Charmer(context,p, blockSize);

        mSpike = new Spike(context, p, blockSize);

        mHUD = new HUD(context,size, mCanvas, mPaint);
    }


    // Called to start a new game
    public void newGame() {

        // reset the snake
        mSnake.spawn(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        mSnake.setDead(false);

        // Get the apple ready for dinner
        mApple.spawn();

        //Grape
        mGrape.spawn();

        //Spike
        mSpike.spawn();

        //Bad Apple
        mRotApple.spawn();

        // Reset the mScore
        mScore = 0;

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();

    }


    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused && !mManualPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            draw(mContext);
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {
        cycles++;
        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }

    // Update all the game objects
    public void update() {
        // Move the snake
        mSnake.move();

        // Did the head of the snake eat the apple?
        if (mSnake.checkDinner(mApple.getLocation())) {
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();
            mSpike.spawn();
            mRotApple.spawn();
            // Add to  mScore
            mScore++;

            // Play a sound
            Audio.playEat(1, 1, 0, 0, 1);
        }
        Sabotage();

        //Has the Snake eaten the Rotten Apple?
        if (mSnake.checkDinner(mRotApple.getLocation())) {
            mRotApple.location(-10,0);

            //if score is >= 5 subtract 5 from the score
            //and shorten the length by 5 circles
            if (mScore >= 5) {
                mScore = mScore - 5;
//                mSnake.newLength(NUM_BLOCKS_WIDE, mNumBlocksHigh);
                mSnake.setDead(true);

            //if score is < 5 then set the snake's length and current score to zero
            }else{
                mSnake.setDead(true);
            }

            // Play a sound

        }
        if (mSnake.checkDinner(mCharmer.getLocation())) {
            mScore++;
            mCharmer.location(-10,0);
            Audio.playKill(1, 1, 0, 0, 1);
        }

        //check to see if the snake ate the grape
        if(mSnake.checkDinner(mGrape.getLocation())){
            mGrape.spawn();

            // Add to  mScore
            mScore = mScore + 3;
            mSnake.addLength(NUM_BLOCKS_WIDE, mNumBlocksHigh);

            // Play a sound
            Audio.playEat(1, 1, 0, 0, 1);
        }

        //if the snake touches the spike it'll die
        if(mSnake.checkDinner(mSpike.getLocation())){
            mSnake.setDead(true);
        }


        // Did the snake die?
        if (mSnake.detectDeath()) {
            mHighScore.setHighScore(mScore);
            mCharmer.reset();
            // Pause the game ready to start again
            Audio.playDead(1, 1, 0, 0, 1);

            mDead =true;  // Set to false for God mode ;)
            mPaused=true;
        }
    }

    public void Sabotage() {
        if(mScore>0 && mScore % 5 == 0) {
            if (mRotApple.getLocation().equals(-10, 0)) {
                mRotApple.spawn();
            }
            if (mScore == 10 && mCharmer.getLocation().equals(-10, 0)) {
                Audio.playTrumpet(1, 1, 0, 0, 1);
                mCharmer.spawn();
            }

            if (mScore > 10) {
                if (cycles%10==0) {
                    mRotApple.spawn();
                }
                if (mCharmer.getLocation().equals(-10, 0) && mRandom.nextInt(10) % 2 == 0) {
                    mCharmer.spawn();
                    Audio.playTrumpet(1, 1, 0, 0, 1);
                }
            }
        }
    }

        // Do all the drawing
    public void draw(Context context) {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mHUD.setmCanvas(mCanvas);


            mHUD.setFont(context);

            // Draw the start screen background bitmap
            if(mDead) {
                mHUD.drawText(mPaused, mDead, mManualPaused);
                mHUD.drawEndScreenBitmap();
            }else if(mPaused) {
                mHUD.drawHomeScreenBitmap();
            }else{
                mHUD.drawBackgroundBitmap();
            }

            // Draw the score
            mHUD.drawScore(mScore);

            mHUD.drawHighScore(mHighScore.getHighScore(), mScore);

            // Draw the objects
            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            mRotApple.draw(mCanvas,mPaint);
            mCharmer.draw(mCanvas,mPaint);
            mSpike.draw(mCanvas, mPaint);

            //draw the grape after every 7 points
            if(mScore % 7 == 0 && mScore != 0) {
                mGrape.draw(mCanvas, mPaint);
            }

            mHUD.setmDead(mDead);
            mHUD.draw(mCanvas, mPaint);
            //draw names using the method in HUD
            mHUD.drawText();

                // Draw some text while paused
                mHUD.drawText(mPaused, mDead, mManualPaused);

            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:

                mButtonController.handleInput(motionEvent, mHUD.getButtons());

                if(mHomeScreen) {
                    mHomeScreen = false;
                    mDead = false;
                    return true;
                }

                if (mPaused) {
                    mPaused = false;
                    mDead=false;
                    newGame();

                    // Don't want to process snake direction for this tap
                    return true;
                }



                if (!mManualPaused) {
                    if(!mCharmer.getLocation().equals(-10,0)){
                        float newX=motionEvent.getX();
                        float offset= halfway-newX;

                        if(motionEvent.getX()>halfway) {
                            newX = newX+offset-1;
                        }else{
                            newX=newX+offset+1;
                        }
                        motionEvent.setLocation(newX,motionEvent.getY());
                    }
                    // Let the Snake class handle the input
                    mSnake.changeDirection(motionEvent);
                }
                break;

            default:
                break;

        }
        return true;
    }


    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }


    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }

    public boolean getManualPaused() {
        return mManualPaused;
    }

    public boolean getPaused() {
        return mPaused;
    }

    public HighScore getHighScore() {
        return mHighScore;
    }
    public boolean getDead() {
        return mDead;
    }

    public void setmManualPaused(boolean mManualPaused) {
        this.mManualPaused = mManualPaused;
    }

    public void setmPaused(boolean mPaused) {
        this.mPaused = mPaused;
    }

    public void setmDead(boolean mDead) {
        this.mDead = mDead;
    }
    public void setmHomeScreen(boolean mHomeScreen) {
        this.mHomeScreen = mHomeScreen;
    }
}
