package com.csc133.snakeysnake;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public class ButtonController implements InputObserver {
    private SnakeGame game;

    public ButtonController(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void handleInput(MotionEvent event, ArrayList<Rect> buttons) {
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        if (eventType == MotionEvent.ACTION_UP ||
                eventType == MotionEvent.ACTION_POINTER_UP) {

            if (buttons.get(HUD.PAUSE).contains(x, y)) {
                if (!game.getManualPaused() && !game.getPaused()) {
                    game.setmManualPaused(true);
                }
                else if (game.getManualPaused()) {
                    game.setmManualPaused(false);
                }
            }
        }
    }
}
