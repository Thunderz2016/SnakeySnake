package com.csc133.snakeysnake;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

public class ButtonController implements InputObserver {
    private SnakeGame game;

    public ButtonController(SnakeGame game) {
        this.game = game;
    }

    public void handleInput(MotionEvent event, ArrayList<Rect> buttons) {
        int i = event.getActionIndex();
        int x = (int) event.getX(i);
        int y = (int) event.getY(i);

        int eventType = event.getAction() & MotionEvent.ACTION_MASK;

        if (eventType == MotionEvent.ACTION_DOWN ||
                eventType == MotionEvent.ACTION_POINTER_UP) {

        }
            if (buttons.get(HUD.HOME_SCREEN).contains(x, y)) {
                game.setmHomeScreen(true);
                game.setmDead(false);
                game.setmPaused(true);
            }

            if (buttons.get(HUD.PAUSE).contains(x, y)) {
                if (!game.getManualPaused() && !game.getPaused()) {
                    game.setmManualPaused(true);
                }
                else if (game.getManualPaused()) {
                    game.setmManualPaused(false);
                }

            } else if (buttons.get(HUD.RESET_HS).contains(x, y)) {
                game.setmManualPaused(true);
                showResetDialog();
            }
        }

    public void showResetDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(game.getContext(), android.R.style.Theme_DeviceDefault_Dialog_Alert);
        builder1.setTitle("Reset High Score");
        builder1.setMessage("Reset your high score and quit game?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        game.getHighScore().resetHighScore();
                        System.exit(0);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        game.setmManualPaused(false);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
