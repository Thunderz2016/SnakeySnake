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
    private ArrayList<Rect> buttons;

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
                buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);

        buttons.add(pause);
    }

    void draw(Canvas c, Paint p, boolean manualPaused, boolean died) {
        p.setColor(Color.argb(255, 255, 255, 255));
        p.setTextSize(250);

        if(manualPaused) {
            c.drawText("PAUSED", mScreenWidth / 3, mScreenHeight / 2, p);
        }

        if(died) {
            c.drawText("Tap To Play!", 200, 700, p);
        }

        //drawControls(c, p);
    }

    void drawControls(Canvas c, Paint p) {
        for(Rect rect : buttons) {
            c.drawRect(rect.left, rect.top, rect.right, rect.bottom, p);
        }
    }

}
