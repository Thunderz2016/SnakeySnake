package com.csc133.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class HUD {
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;
    private ArrayList<Rect> buttons = new ArrayList<Rect>();

    //  Button index for pause button (Chapter 18)
    static final int PAUSE = 0;

    HUD(Point size) {
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;

        prepareControls();
    }

    private void prepareControls() {
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;

        Rect pause = new Rect(
                mScreenWidth - buttonPadding - buttonWidth,
                mScreenHeight - buttonHeight - buttonPadding,
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonPadding);

        buttons.add(pause);
    }

    void draw(Canvas c, Paint p, boolean manualPaused, boolean died) {

        p.setTextSize(250);
//        c.drawText("");

        drawControls(c, p);
    }

    void drawControls(Canvas c, Paint p) {
        p.setColor(Color.argb(255, 77, 77, 77));
        p.setTextSize(40);

        for(Rect rect : buttons) {
            c.drawRect(rect.left, rect.top, rect.right, rect.bottom, p);
            drawButtonText("Pause", c, p, rect);
        }
        p.setTextAlign(Paint.Align.LEFT);   // Reset alignment for all other text
    }

    private void drawButtonText(String text, Canvas c, Paint p, Rect r) {
        p.setTextSize(50);
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.argb(255, 255, 255, 255));
        int width = r.width();

        int charsCount = p.breakText(text, true, width, null);
        int start = (text.length()-charsCount)/2;
        c.drawText(text,start,start+charsCount,r.exactCenterX(),r.exactCenterY(),p);
    }

    public ArrayList<Rect> getButtons() {
        return buttons;
    }

}
