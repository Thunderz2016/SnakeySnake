package com.csc133.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class HUD extends Drawable {
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<Rect> buttons = new ArrayList<Rect>();
    private Canvas mCanvas;
    private Paint mPaint;

    //  Button index for pause button (Chapter 18)
    static final int PAUSE = 0;
    static final int RESET_HS = 1;
    static final int VALUE_FOR_VARYING_SCREEN_SIZES = 50;
    static final int NUMBER_IN_PROPORTION_TO_SCREEN_WIDTH = 10;
    static final int NUMBER_IN_PROPORTION_TO_SCREEN_HEIGHT = 12;
    static final int NUMBER_IN_PROPORTION_TO_PADDING = 90;


    HUD(Point size, Canvas mCanvas, Paint mPaint) {
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / VALUE_FOR_VARYING_SCREEN_SIZES;

        this.mCanvas = mCanvas;
        this.mPaint = mPaint;

        prepareControls();
    }

    private void prepareControls() {
        int buttonWidth = mScreenWidth / NUMBER_IN_PROPORTION_TO_SCREEN_WIDTH;
        int buttonHeight = mScreenHeight / NUMBER_IN_PROPORTION_TO_SCREEN_HEIGHT;
        int buttonPadding = mScreenWidth / NUMBER_IN_PROPORTION_TO_PADDING;

        //  Bottom right: Pause button
        Rect pause = new Rect(
                mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonPadding);

        Rect resetHighScore = new Rect(
                buttonPadding,
                mScreenHeight - buttonHeight - buttonPadding,
                buttonWidth + buttonPadding * 6,
                mScreenHeight - buttonPadding);;

        buttons.add(PAUSE, pause);
        buttons.add(RESET_HS, resetHighScore);
    }
    // Draws the rectangular box for the pause button
    // As well as the
    @Override
    public void draw(Canvas mCanvas, Paint mPaint) {
        mPaint.setColor(Color.argb(150, 77, 77, 77));
        mPaint.setTextSize(5);

        for(int i = 0; i < buttons.size(); i++) {
            Rect button = buttons.get(i);
            mPaint.setColor(Color.argb(150, 77, 77, 77));
            mCanvas.drawRect(button.left, button.top, button.right, button.bottom, mPaint);
            drawButtonText(i, button);
        }
        mPaint.setTextAlign(Paint.Align.LEFT);   // Reset alignment for all other text
    }

    void setFont(Context context) {
        Typeface nes = ResourcesCompat.getFont(context, R.font.nes);
        mPaint.setTypeface(nes);
    }

    void drawBackgroundBitmap(Context context) {
        // Create a background bitmap
        Bitmap mBitmapBackground = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.background);

        // Draw the background bitmap
        mCanvas.drawBitmap(mBitmapBackground, -400, -400, mPaint);
    }

    void drawScore(int score) {
        // Set the size and color of the mPaint for the text
        colorwhite();
        mPaint.setTextSize(120);

        // Draw the score
        mCanvas.drawText("" + score, 20, 120, mPaint);
    }

    void drawHighScore(int highScore, int currentScore) {
        int currentHigh = highScore;
        mPaint.setTextSize(60);
        if(currentScore >= highScore) {
            // If high score is beaten, display the high score in green
            // and sync high score with current score
            mPaint.setColor(Color.argb(255, 0, 255, 0));
            mCanvas.drawText("High Score: " + currentScore, 20, 200, mPaint);
        } else {
            mCanvas.drawText("High Score: " + highScore, 20, 200, mPaint);
        }

        // Draw the score

    }

    void drawText() {
        int namesWidth = mScreenWidth / 150;
        int namesPadding = mScreenWidth / 35;

        mPaint.setColor(Color.argb(255, 77, 77, 77));
        mPaint.setTextSize(60);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        mCanvas.drawText("Modified By: Hadia Amiri & Wenshen Zhong", mScreenWidth - namesPadding - namesWidth, namesPadding, mPaint);
        mCanvas.drawText("& Josh Dye & Corliss Yang", mScreenWidth - namesPadding - namesWidth, namesPadding*2, mPaint);


        mPaint.setTextAlign(Paint.Align.LEFT);   // Reset text alignment to LEFT for all other text following
    }

    void drawText(boolean gameOver, boolean manualPaused) {
        if(gameOver){

            // Set the size and color of the mPaint for the text
            colorwhite();
            mPaint.setTextSize(170);

            // Draw the message
            // We will give this an international upgrade soon
            mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        }

        // Draw text while MANUALLY paused
        if(manualPaused) {
            // Set the size and color of the mPaint for the text
            colorwhite();
            mPaint.setTextSize(250);

            // Draw the message
            // We will give this an international upgrade soon
            //mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
            mCanvas.drawText("PAUSED", 200, 700, mPaint);
        }
    }

    private void drawButtonText(int index, Rect r) {
        mPaint.setTextSize(50);
        mPaint.setTextAlign(Paint.Align.CENTER);
        colorwhite();
        int width = r.width();

        String text;
        switch(index) {
            case PAUSE:
                text = "PAUSE";
                break;
            case RESET_HS:
                text = "Reset HS";
                break;
            default:
                return;
        }

        int charsCount = mPaint.breakText(text, true, width, null);
        int start = (text.length()-charsCount)/2;
        mCanvas.drawText(text,start,start+charsCount,r.exactCenterX(),r.exactCenterY(),mPaint);
    }

    public ArrayList<Rect> getButtons() {
        return buttons;
    }

    public void setmCanvas(Canvas mCanvas) {
        this.mCanvas = mCanvas;
    }

    public void colorwhite(){
        mPaint.setColor(Color.argb(255, 255, 255, 255));
    }
}
