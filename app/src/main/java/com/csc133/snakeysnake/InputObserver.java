package com.csc133.snakeysnake;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;


public interface
InputObserver {
    void handleInput(MotionEvent event, ArrayList<Rect> buttons);
}
