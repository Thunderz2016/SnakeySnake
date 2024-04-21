package com.csc133.snakeysnake;

import android.content.Context;
import android.content.SharedPreferences;

public class HighScore {
    private int mHighScore;

    private final SharedPreferences.Editor mEditor;

    public HighScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("HiScore", Context.MODE_PRIVATE);
        mEditor = prefs.edit();
        mHighScore = prefs.getInt("hi_score", 0);
    }

    public void setHighScore(int score) {
        if(score > mHighScore) {
            mHighScore = score;
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    public int getHighScore() {
        return mHighScore;
    }
}
