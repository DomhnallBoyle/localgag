package com.example.domhnallboyle.localgag.Engine.Audio;

import android.media.SoundPool;

/**
 * Created by DomhnallBoyle on 07/05/2016.
 */
public class Sound {

    private boolean play;
    private int soundID;
    private SoundPool soundPool;
    private float volume;

    public Sound(SoundPool soundPool, int soundID)
    {
        this.soundID = soundID;
        this.soundPool = soundPool;
        this.volume = 20.0f;
        this.play = true;
    }

    public boolean getPlay(){return play;}
    public void setPlay(boolean play){this.play = play;}
    public void play()
    {
        if (play)
        {
            this.soundPool.play(this.soundID, this.volume, this.volume, 0, 0, 1);
        }
    }
    public void play(float volume)
    {
        if (play)
        {
            this.soundPool.play(this.soundID, volume, volume, 0, 0, 1);
        }
    }
    public void play(float leftVolume, float rightVolume)
    {
        if (play)
        {
            this.soundPool.play(this.soundID, leftVolume, rightVolume, 0, 0, 1);
        }
    }
    public void setVolume(float volume){this.volume = volume;}
    public void dispose(){this.soundPool.unload(this.soundID);}



}
