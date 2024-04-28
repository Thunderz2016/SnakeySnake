package com.csc133.snakeysnake;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScore {
    private int mHighScore;

    private final SharedPreferences.Editor mEditor;

    public HighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("HiScore", Context.MODE_PRIVATE);
        mEditor = prefs.edit();
        mHighScore = prefs.getInt("HiScore", 0);
    }

    public void setHighScore(int score) {
        if(score > mHighScore) {
            mHighScore = score;
            mEditor.putInt("HiScore", mHighScore);
            mEditor.commit();
        }
    }

    public int getHighScore() {
        return mHighScore;
    }

    public void resetHighScore() {
        mEditor.putInt("HiScore", 0);
        mEditor.commit();
    }
}
