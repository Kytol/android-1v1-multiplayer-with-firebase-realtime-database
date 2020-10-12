package com.example.omistaja.firebasecountertest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


public class TitleScreen extends AppCompatActivity {

    private TextView textViewMove;
    private TextView textView2;
    private Animation move;
    private Animation move2;
    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ( android.os.Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorBlack));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewMove = findViewById(R.id.textViewMove);
        textView2 = findViewById(R.id.textView2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeIntent = new Intent(TitleScreen.this, MainMenu.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            move = AnimationUtils.loadAnimation(this,R.anim.move);
            textViewMove.setAnimation(move);

            move2 = AnimationUtils.loadAnimation(this,R.anim.move2);
            textView2.setAnimation(move2);
        }
    }
}