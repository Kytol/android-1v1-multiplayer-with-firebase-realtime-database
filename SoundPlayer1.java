package com.example.omistaja.firebasecountertest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer1 {

    private SoundPool soundPool;
    private AudioAttributes audioAttributes;

    private int hit1;

    private int get_ultimate1;

    private int trump_ultimate;
    private int trump_winning;
    private int trump_losing;
    private int trump_ultimate_ready;

    private int kim_ultimate;
    private int kim_winning;
    private int kim_losing;

    private int pikachu_ultimate;
    private int pikachu_winning;
    private int pikachu_losing;

    private int arnold_ultimate;
    private int arnold_winning;
    private int arnold_losing;

    public SoundPlayer1(Context context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(25)
                    .build();
        } else {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }

        hit1 = soundPool.load(context, R.raw.hit1, 1);

        get_ultimate1 = soundPool.load(context, R.raw.get_ultimate1, 1);

        trump_ultimate = soundPool.load(context, R.raw.trump_ultimate, 1);
        trump_winning = soundPool.load(context, R.raw.trump_winning, 1);
        trump_losing = soundPool.load(context, R.raw.trump_losing, 1);
        trump_ultimate_ready = soundPool.load(context, R.raw.trump_ultimate_ready, 1);

        kim_ultimate = soundPool.load(context, R.raw.kim_ultimate, 1);
        kim_winning = soundPool.load(context, R.raw.kim_winning, 1);
        kim_losing = soundPool.load(context, R.raw.kim_losing, 1);

        pikachu_ultimate = soundPool.load(context, R.raw.pikachu_ultimate, 1);
        pikachu_winning = soundPool.load(context, R.raw.pikachu_winning, 1);
        pikachu_losing = soundPool.load(context, R.raw.pikachu_losing, 1);

        arnold_ultimate = soundPool.load(context, R.raw.arnold_ultimate, 1);
        arnold_winning = soundPool.load(context, R.raw.arnold_winning, 1);
        arnold_losing = soundPool.load(context, R.raw.arnold_losing, 1);
    }

    public void playHit1Sound(){
        soundPool.play(hit1, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playGetUltimate1Sound(){
        soundPool.play(get_ultimate1, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playTrumpUltimateSound(){
        soundPool.play(trump_ultimate, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playTrumpWinningSound(){
        soundPool.play(trump_winning, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playTrumpLosingSound(){
        soundPool.play(trump_losing, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playTrumpUltimateGetSound(){soundPool.play(trump_ultimate_ready, 1.0f, 1.0f, 1, 0, 1); }

    public void playKimUltimateSound(){
        soundPool.play(kim_ultimate, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playKimWinningSound(){
        soundPool.play(kim_winning, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playKimLosingSound(){
        soundPool.play(kim_losing, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playPikachuUltimateSound(){soundPool.play(pikachu_ultimate, 1.0f, 1.0f, 1, 0, 1); }

    public void playPikachuWinningSound(){
        soundPool.play(pikachu_winning, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playPikachuLosingSound(){
        soundPool.play(pikachu_losing, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playArnoldUltimateSound(){
        soundPool.play(arnold_ultimate, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playArnoldWinningSound(){
        soundPool.play(arnold_winning, 1.0f, 1.0f, 1, 0, 1);
    }

    public void playArnoldLosingSound(){
        soundPool.play(arnold_losing, 1.0f, 1.0f, 1, 0, 1);
    }

}
