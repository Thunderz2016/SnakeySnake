package com.csc133.snakeysnake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

public class Audio{
    // for playing sound effects
    private static SoundPool mSP;
    private static int mEat_ID = -1;
    private static int mCrashID = -1;



    public static void initialize(Context context) {
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

//            descriptor = assetManager.openFd("snake_death.ogg");
            descriptor = assetManager.openFd("death3.wav");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }
    }

    public static void playEat(int i, int j, int k, int l, int m){
        mSP.play(mEat_ID, i, j, k, l, m);
    }

    public static void playDead(int i, int j, int k, int l, int m){
        mSP.play(mCrashID, i, j, k, l, m);
    }
}
