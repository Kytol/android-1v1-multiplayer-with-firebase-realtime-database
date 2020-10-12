package com.example.omistaja.firebasecountertest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class PlayerTwo extends AppCompatActivity {

    // ### Start of declarations ###

    // Character Select declarations
    private ImageView hahmo1;
    private ImageView hahmo2;
    private ImageView hahmo3;
    private ImageView hahmo4;

    // Selected Characters declarations
    private ImageView hahmo1_1;
    private ImageView hahmo2_1;
    private String character = "";
    private String character2 = "";

    // TextView declarations
    private TextView winningTV;
    private TextView hahmo1tv;
    private TextView hahmo2tv;
    private TextView hahmo3tv;
    private TextView hahmo4tv;

    // Local Player Stat Variable declarations
    private Integer player1health;
    private Integer player2health;
    private Integer player1mana;
    private Integer player2mana;

    // Stat TextView Variable declarations
    private TextView health1TV;
    private TextView health2TV;
    private TextView mana1TV;
    private TextView mana2TV;

    // Bar declarations
    private UIBarDraw hp1;
    private UIBarDraw mp1;
    private UIBarDraw hp2;
    private UIBarDraw mp2;

    // Flying Blob declarations
    private ImageView manaImageView;
    private ImageView mana_2ImageView;
    private ImageView mana5ImageView;

    // Attacking Button declarations
    private Button attackButton;
    private Button ultimateButton;
    private Integer ultCounter = 0;
    private Integer randomNumber;
    private Integer randomNumber2;

    // Sound declaration
    private SoundPlayer2 sound;

    // Miscellaneous declarations
    private ImageView bonusHealth;
    private Integer myBonusHealth;
    private Integer enemyBonusHealth;
    private Integer oldHealth = 30; //TODO: laita lopullinen health arvo
    private Animation shakeAnim;
    private ImageView ultimateImageView;
    private ImageView ultimateAttack;
    private ImageView ultimateAttack2;
    private Integer ultimateStance = 0;
    private pl.droidsonroids.gif.GifTextView gifTextView2;
    private pl.droidsonroids.gif.GifTextView gifTextView3;
    private pl.droidsonroids.gif.GifTextView gifTextView4;
    private Integer player1choice;
    private Boolean UDO = true;

    // ### End of declarations ###

    //Doesn't have interval2                        //ULTIMATE APPEARANCE
    private Handler handler2 = new Handler();
    private Runnable runnable2 = new Runnable(){
        public void run() {
            ultimateImageView.setVisibility(View.VISIBLE);

            //ULT CHANGES
            ultimateImageView.setTranslationZ(11);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Integer height = displayMetrics.heightPixels;
            Integer width = displayMetrics.widthPixels;

            Random oneOfFour = new Random();
            Integer oneToFour = oneOfFour.nextInt(4) + 1;   // 1 is minimum, 4 is maximum

            if(oneToFour == 1)          //spawn on left side
            {
                ultimateImageView.setX(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height-200);   // 0 is minimum, maxHeight is maximum
                ultimateImageView.setY(n);
                ultimateImageView.animate().xBy(width).setDuration(3000);
            }
            else if (oneToFour == 2)    //spawn on top
            {
                ultimateImageView.setY(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width-100);   // 0 is minimum, maxWidth is maximum
                ultimateImageView.setX(n);
                ultimateImageView.animate().yBy(height-50).setDuration(5000);
            }
            else if (oneToFour == 3)    //spawn on right side
            {
                ultimateImageView.setX(width + 50);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height-200);   // 0 is minimum, maxHeight is maximum
                ultimateImageView.setY(n);
                ultimateImageView.animate().xBy(-(width)).setDuration(3000);
            }
            else if (oneToFour == 4)    //spawn on bottom
            {
                ultimateImageView.setY(height + 50);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width-100);   // 0 is minimum, maxWidth is maximum
                ultimateImageView.setX(n);
                ultimateImageView.animate().yBy(-(height)).setDuration(5000);
            }
            ///ULT CHANGES
            //ultimateImageView.animate().xBy(750).yBy(800).setDuration(5000);
        }
    };

    private int interval3 = 500;                   //PLAYER CHARACTER(2)
    private Handler handler3 = new Handler();
    private Runnable runnable3 = new Runnable(){
        public void run() {
            if(character2 == "donald") {
                hahmo1_1.setImageResource(R.drawable.donald_idle);
            } else if(character2 == "kim") {
                hahmo1_1.setImageResource(R.drawable.kim_idle);
            } else if(character2 == "pikachu") {
                hahmo1_1.setImageResource(R.drawable.pikachu_idle);
            } else if(character2 == "arnold") {
                hahmo1_1.setImageResource(R.drawable.arnold_idle);
            }
        }
    };

    private int interval4 = 500;                   //ENEMY CHARACTER(1)
    private Handler handler4 = new Handler();
    private Runnable runnable4 = new Runnable(){
        public void run() {
            if(character == "donald") {
                hahmo2_1.setImageResource(R.drawable.donald_idle);
            } else if(character == "kim") {
                hahmo2_1.setImageResource(R.drawable.kim_idle);
            } else if(character == "pikachu") {
                hahmo2_1.setImageResource(R.drawable.pikachu_idle);
            } else if(character == "arnold") {
                hahmo2_1.setImageResource(R.drawable.arnold_idle);
            }
        }
    };


    private int interval = 3500;                     //ATTACK BUTTON ENABLES
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            gifTextView2.setVisibility(View.INVISIBLE);
            attackButton.setEnabled(true);
            attackButton.setText("Attack");
        }
    };

    private int interval5;                    //ULTIMATE GIF
    private Handler handler5 = new Handler();
    private Runnable runnable5 = new Runnable(){
        public void run() {
            gifTextView3.setVisibility(View.INVISIBLE);
            ultimateAttack.setVisibility(View.VISIBLE);
            ultimateAttack.animate().xBy(700).yBy(-1100).setDuration(4000).rotationX(200);
            hahmo2_1.startAnimation(shakeAnim);
            handler6.postAtTime(runnable6, System.currentTimeMillis()+interval6);
            handler6.postDelayed(runnable6, interval6);

        }
    };

    private int interval6 = 5000;                    //ULTIMATE ATTACK
    private Handler handler6 = new Handler();
    private Runnable runnable6 = new Runnable(){
        public void run() {
            ultimateAttack.setVisibility(View.INVISIBLE);

            if(player2mana > 0) {
                attackButton.setEnabled(true);
                attackButton.setText("Attack");
            }

            if(character2 == "donald") {
                hahmo1_1.setImageResource(R.drawable.donald_idle);
            } else if(character2 == "kim") {
                hahmo1_1.setImageResource(R.drawable.kim_idle);
            } else if(character2 == "pikachu") {
                hahmo1_1.setImageResource(R.drawable.pikachu_idle);
            } else if(character2 == "arnold") {
                hahmo1_1.setImageResource(R.drawable.arnold_idle);
            }

            handler9.postAtTime(runnable9, System.currentTimeMillis()+2000);
            handler9.postDelayed(runnable9, 2000);
            handler9_2.postAtTime(runnable9_2, System.currentTimeMillis()+2000);
            handler9_2.postDelayed(runnable9_2, 2000);
            handler11.postAtTime(runnable11, System.currentTimeMillis()+4000);
            handler11.postDelayed(runnable11, 4000);
            handler14.postAtTime(runnable14, System.currentTimeMillis()+4000);
            handler14.postDelayed(runnable14, 4000);

            if(UDO == true) {
                mHealth1Db.setValue(player1health - 10);    //Ultimaten damage on 10
                UDO = false;
            }
        }
    };

    private int ultimateProjectileInterval;                    //ULTIMATE2 GIF
    private Handler ultimateProjectileHandler = new Handler();
    private Runnable runnable7 = new Runnable(){
        public void run() {
            gifTextView3.setVisibility(View.INVISIBLE);
            ultimateAttack2.setVisibility(View.VISIBLE);
            ultimateAttack2.animate().xBy(-600).yBy(1100).setDuration(4000).rotationX(-200);
            hahmo1_1.startAnimation(shakeAnim);
            handler8.postAtTime(runnable8, System.currentTimeMillis()+interval8);
            handler8.postDelayed(runnable8, interval8);

        }
    };

    private int interval8 = 5000;                    //ULTIMATE2 ATTACK
    private Handler handler8 = new Handler();
    private Runnable runnable8 = new Runnable(){
        public void run() {
            ultimateAttack2.setVisibility(View.INVISIBLE);

            if(player2mana > 0) {
                attackButton.setEnabled(true);
                attackButton.setText("Attack");
            }

            if(character == "donald") {
                hahmo2_1.setImageResource(R.drawable.donald_idle);
            } else if(character == "kim") {
                hahmo2_1.setImageResource(R.drawable.kim_idle);
            } else if(character == "pikachu") {
                hahmo2_1.setImageResource(R.drawable.pikachu_idle);
            } else if(character == "arnold") {
                hahmo2_1.setImageResource(R.drawable.arnold_idle);
            }

            handler9.postAtTime(runnable9, System.currentTimeMillis()+2000);
            handler9.postDelayed(runnable9, 2000);
            handler9_2.postAtTime(runnable9_2, System.currentTimeMillis()+2000);
            handler9_2.postDelayed(runnable9_2, 2000);
            handler11.postAtTime(runnable11, System.currentTimeMillis()+6000);
            handler11.postDelayed(runnable11, 6000);
            handler14.postAtTime(runnable14, System.currentTimeMillis()+4000);
            handler14.postDelayed(runnable14, 4000);

        }
    };

    private int interval9 = 4000;                    //MANA APPEARANCE
    private Handler handler9 = new Handler();
    private Runnable runnable9 = new Runnable(){
        public void run() {
            manaImageView.setVisibility(View.VISIBLE);
            manaImageView.setTranslationZ(10);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Integer height = displayMetrics.heightPixels;
            Integer width = displayMetrics.widthPixels;

            Random oneOfFour = new Random();
            Integer oneToFour = oneOfFour.nextInt(4) + 1;   // 1 is minimum, 4 is maximum

            if(oneToFour == 1)          //spawn on left side
            {
                manaImageView.setX(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                manaImageView.setY(n);
                manaImageView.animate().xBy(width + 300).yBy(height - 2 * n).setDuration(5000);
            }
            else if (oneToFour == 2)    //spawn on top
            {
                manaImageView.setY(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                manaImageView.setX(n);
                manaImageView.animate().xBy(width - 2 * n).yBy(height + 300).setDuration(5000);
            }
            else if (oneToFour == 3)    //spawn on right side
            {
                manaImageView.setX(width + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                manaImageView.setY(n);
                manaImageView.animate().xBy(-(width + 300)).yBy(height - 2 * n).setDuration(5000);
            }
            else if (oneToFour == 4)    //spawn on bottom
            {
                manaImageView.setY(height + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                manaImageView.setX(n);
                manaImageView.animate().xBy(width - 2 * n).yBy(-(height + 300)).setDuration(5000);
            }

            handler10.postAtTime(runnable10, System.currentTimeMillis()+interval10);
            handler10.postDelayed(runnable10, interval10);
        }
    };

    private int interval10 = 1000;
    Random rechargeMana2 = new Random();                        //MANA RE-APPEARS IF PLAYER DOESN'T CLICK IT
    Integer rechargeManaInt2 = rechargeMana2.nextInt(2500) + 5500;   // 5500 is minimum, 2500+5500 is maximum
    private Handler handler10 = new Handler();
    private Runnable runnable10 = new Runnable(){
        public void run() {
            handler9.postAtTime(runnable9, System.currentTimeMillis()+rechargeManaInt2);
            handler9.postDelayed(runnable9, rechargeManaInt2);
        }
    };

    //MANA_2
    private int interval9_2 = 4000;                    //MANA_2 APPEARANCE
    private Handler handler9_2 = new Handler();
    private Runnable runnable9_2 = new Runnable(){
        public void run() {
            mana_2ImageView.setVisibility(View.VISIBLE);
            mana_2ImageView.setTranslationZ(10);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Integer height = displayMetrics.heightPixels;
            Integer width = displayMetrics.widthPixels;

            Random oneOfFour = new Random();
            Integer oneToFour = oneOfFour.nextInt(4) + 1;   // 1 is minimum, 4 is maximum

            if(oneToFour == 1)          //spawn on left side
            {
                mana_2ImageView.setX(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                mana_2ImageView.setY(n);
                mana_2ImageView.animate().xBy(width + 300).yBy(height - 4 * n).setDuration(5000);
            }
            else if (oneToFour == 2)    //spawn on top
            {
                mana_2ImageView.setY(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                mana_2ImageView.setX(n);
                mana_2ImageView.animate().xBy(width - 4 * n).yBy(height + 300).setDuration(5000);
            }
            else if (oneToFour == 3)    //spawn on right side
            {
                mana_2ImageView.setX(width + 50);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                mana_2ImageView.setY(n);
                mana_2ImageView.animate().xBy(-(width + 300)).yBy(height - 4 * n).setDuration(5000);
            }
            else if (oneToFour == 4)    //spawn on bottom
            {
                mana_2ImageView.setY(height + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                mana_2ImageView.setX(n);
                mana_2ImageView.animate().xBy(width - 4 * n).yBy(-(height + 300)).setDuration(5000);
            }

            handler10_2.postAtTime(runnable10_2, System.currentTimeMillis()+interval10_2);
            handler10_2.postDelayed(runnable10_2, interval10_2);
        }
    };

    private int interval10_2 = 1000;
    Random rechargeMana2_2 = new Random();                        //MANA_2 RE-APPEARS IF PLAYER DOESN'T CLICK IT
    Integer rechargeManaInt2_2 = rechargeMana2_2.nextInt(2500) + 5500;   // 5500 is minimum, 2500+5500 is maximum
    private Handler handler10_2 = new Handler();
    private Runnable runnable10_2 = new Runnable(){
        public void run() {
            handler9_2.postAtTime(runnable9_2, System.currentTimeMillis()+rechargeManaInt2_2);
            handler9_2.postDelayed(runnable9_2, rechargeManaInt2_2);
        }
    };
    ///MANA_2


    //MANA5
    private int interval11 = 6000;                    //MANA5 APPEARANCE
    private Handler handler11 = new Handler();
    private Runnable runnable11 = new Runnable(){
        public void run() {
            mana5ImageView.setVisibility(View.VISIBLE);
            mana5ImageView.setTranslationZ(10);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Integer height = displayMetrics.heightPixels;
            Integer width = displayMetrics.widthPixels;

            Random oneOfFour = new Random();
            Integer oneToFour = oneOfFour.nextInt(4) + 1;   // 1 is minimum, 4 is maximum

            if(oneToFour == 1)          //spawn on left side
            {
                mana5ImageView.setX(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                mana5ImageView.setY(n);
                mana5ImageView.animate().xBy(width + 300).yBy(height - 2 * n).setDuration(3000);
            }
            else if (oneToFour == 2)    //spawn on top
            {
                mana5ImageView.setY(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                mana5ImageView.setX(n);
                mana5ImageView.animate().xBy(width - 2 * n).yBy(height + 300).setDuration(3000);
            }
            else if (oneToFour == 3)    //spawn on right side
            {
                mana5ImageView.setX(width + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                mana5ImageView.setY(n);
                mana5ImageView.animate().xBy(-(width + 300)).yBy(height - 2 * n).setDuration(3000);
            }
            else if (oneToFour == 4)    //spawn on bottom
            {
                mana5ImageView.setY(height + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                mana5ImageView.setX(n);
                mana5ImageView.animate().xBy(width - 2 * n).yBy(-(height + 300)).setDuration(3000);
            }

            handler12.postAtTime(runnable12, System.currentTimeMillis()+interval12);
            handler12.postDelayed(runnable12, interval12);
        }
    };

    private int interval12 = 1000;
    Random rechargeMana5 = new Random();                        //MANA5 RE-APPEARS IF PLAYER DOESN'T CLICK IT
    Integer rechargeManaInt5 = rechargeMana5.nextInt(4500) + 12500;   // 12500 is minimum, 4500+12500 is maximum
    private Handler handler12 = new Handler();
    private Runnable runnable12 = new Runnable(){
        public void run() {
            handler11.postAtTime(runnable11, System.currentTimeMillis()+rechargeManaInt5);
            handler11.postDelayed(runnable11, rechargeManaInt5);
        }
    };
    ///MANA5

    private int interval13 = 1500;          //Sets winningTV to true
    private Handler handler13 = new Handler();
    private Runnable runnable13 = new Runnable(){
        public void run() {
            winningTV.setEnabled(true);
            if(player1health <= 0){
                winningTV.setText("Victory, click anywhere to play again!");
            }
            else if(player2health <= 0) {
                winningTV.setText("Defeat, click anywhere to play again!");
            }
        }
    };

    //private int interval14 = 500;                   //BONUS HEALTH APPEARANCE
    private Handler handler14 = new Handler();
    private Runnable runnable14 = new Runnable(){
        public void run() {
            bonusHealth.setVisibility(View.VISIBLE);
            bonusHealth.setTranslationZ(11);

            //copy
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Integer height = displayMetrics.heightPixels;
            Integer width = displayMetrics.widthPixels;

            Random oneOfFour = new Random();
            Integer oneToFour = oneOfFour.nextInt(4) + 1;   // 1 is minimum, 4 is maximum

            if(oneToFour == 1)          //spawn on left side
            {
                bonusHealth.setX(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                bonusHealth.setY(n);
                bonusHealth.animate().xBy(width + 300).yBy(height - 3 * n).setDuration(5000);
            }
            else if (oneToFour == 2)    //spawn on top
            {
                bonusHealth.setY(-150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                bonusHealth.setX(n);
                bonusHealth.animate().xBy(width - 3 * n).yBy(height + 300).setDuration(5000);
            }
            else if (oneToFour == 3)    //spawn on right side
            {
                bonusHealth.setX(width + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(height);   // 0 is minimum, maxHeight is maximum
                bonusHealth.setY(n);
                bonusHealth.animate().xBy(-(width + 300)).yBy(height - 3 * n).setDuration(5000);
            }
            else if (oneToFour == 4)    //spawn on bottom
            {
                bonusHealth.setY(height + 150);
                Random manaRandomNumber = new Random();
                Integer n = manaRandomNumber.nextInt(width);   // 0 is minimum, maxWidth is maximum
                bonusHealth.setX(n);
                bonusHealth.animate().xBy(width - 3 * n).yBy(-(height + 300)).setDuration(5000);
            }
            ///copy
            handler15.postAtTime(runnable15, System.currentTimeMillis()+interval15);
            handler15.postDelayed(runnable15, interval15);
        }
    };

    private int interval15 = 5000;
    //Random rechargeBonusHealth = new Random();               //BONUS HEALTH RE-APPEARS IF PLAYER DOESN'T CLICK IT
    //Integer rechargeBonusHealthInt = rechargeBonusHealth.nextInt(4500) + 12500;   // 12500 is minimum, 4500+12500 is maximum
    private Handler handler15 = new Handler();
    private Runnable runnable15 = new Runnable(){
        public void run() {
            handler14.postAtTime(runnable14, System.currentTimeMillis()+randomNumber2);
            handler14.postDelayed(runnable14, randomNumber2);
        }
    };


    // Database Reference declarations
    private DatabaseReference mP1Db;
    private DatabaseReference mChar1Db;
    private DatabaseReference mChar2Db;
    private DatabaseReference mHealth1Db;
    private DatabaseReference mHealth2Db;
    private DatabaseReference mUltimate1Db;
    private DatabaseReference mUltimate2Db;
    private DatabaseReference mRndAikaDb;
    private DatabaseReference mRndAika2Db;
    private DatabaseReference mMana2Db;
    private DatabaseReference mMana1Db;
    private DatabaseReference mBonusHealth1Db;
    private DatabaseReference mBonusHealth2Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_player_two);

        mP1Db = FirebaseDatabase.getInstance().getReference().child("Message/P1");
        mChar1Db = FirebaseDatabase.getInstance().getReference().child("Message/Char1");
        mChar2Db = FirebaseDatabase.getInstance().getReference().child("Message/Char2");
        mUltimate1Db = FirebaseDatabase.getInstance().getReference().child("Counter/Ultimate1");
        mUltimate2Db = FirebaseDatabase.getInstance().getReference().child("Counter/Ultimate2");
        mRndAikaDb = FirebaseDatabase.getInstance().getReference().child("Counter/rndAika1");
        mRndAika2Db = FirebaseDatabase.getInstance().getReference().child("Counter/rndAika2");
        mHealth1Db = FirebaseDatabase.getInstance().getReference().child("Counter/Health1");
        mHealth2Db = FirebaseDatabase.getInstance().getReference().child("Counter/Health2");
        mMana1Db = FirebaseDatabase.getInstance().getReference().child("Counter/Mana2");     //Huom: eripäin 1 ja 2 aktivityssä
        mMana2Db = FirebaseDatabase.getInstance().getReference().child("Counter/Mana1");      //Huom: eripäin 1 ja 2 aktivityssä
        mBonusHealth1Db = FirebaseDatabase.getInstance().getReference().child("Counter/BonusHealth1");
        mBonusHealth2Db = FirebaseDatabase.getInstance().getReference().child("Counter/BonusHealth2");

        hahmo1 = findViewById(R.id.hahmo1);
        hahmo2 = findViewById(R.id.hahmo2);
        hahmo3 = findViewById(R.id.hahmo3);
        hahmo4 = findViewById(R.id.hahmo4);

        hahmo1_1 = findViewById(R.id.hahmo1_1);
        hahmo2_1 = findViewById(R.id.hahmo2_1);

        attackButton = findViewById(R.id.attackButton);
        ultimateButton = findViewById(R.id.ultimateButton);

        winningTV = findViewById(R.id.winningTV);
        hahmo1tv = findViewById(R.id.hahmo1tv);
        hahmo2tv = findViewById(R.id.hahmo2tv);
        hahmo3tv = findViewById(R.id.hahmo3tv);
        hahmo4tv = findViewById(R.id.hahmo4tv);

        health1TV = findViewById(R.id.health1TV);
        health2TV = findViewById(R.id.health2TV);
        mana1TV = findViewById(R.id.mana1TV);
        mana2TV = findViewById(R.id.mana2TV);

        bonusHealth = findViewById(R.id.bonusHealth);
        ultimateImageView = findViewById(R.id.ultimateImageView);
        ultimateAttack = findViewById(R.id.ultimateAttack);
        ultimateAttack2 = findViewById(R.id.ultimateAttack2);
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.animshake);
        gifTextView2 = findViewById(R.id.gifTextView2);
        gifTextView3 = findViewById(R.id.gifTextView3);
        gifTextView4 = findViewById(R.id.gifTextView4);
        manaImageView = findViewById(R.id.manaImageView);
        mana_2ImageView = findViewById(R.id.mana_2ImageView);
        mana5ImageView = findViewById(R.id.mana5ImageView);

        hp1 = findViewById(R.id.HealthBar1);
        mp1 = findViewById(R.id.ManaBar1);
        hp2 = findViewById(R.id.HealthBar2);
        mp2 = findViewById(R.id.ManaBar2);

        sound = new SoundPlayer2(this);

        attackButton.setEnabled(false);

        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHealth1Db.setValue(player1health - 1);
                mMana1Db.setValue(player2mana - 1);
                //mp1.changeBar(-1);
                handler3.postAtTime(runnable3, System.currentTimeMillis()+interval3);
                handler3.postDelayed(runnable3, interval3);
                if(character2 == "donald") {
                    hahmo1_1.setImageResource(R.drawable.donald_attack);
                    playHitSound();
                } else if(character2 == "kim") {
                    hahmo1_1.setImageResource(R.drawable.kim_attack);
                    playHitSound();
                } else if(character2 == "pikachu") {
                    hahmo1_1.setImageResource(R.drawable.pikachu_attack);
                    playHitSound();
                } else if(character2 == "arnold") {
                    hahmo1_1.setImageResource(R.drawable.arnold_attack);
                    playHitSound();
                }
            }
        });

        ultimateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ultCounter++;
                if(ultCounter == 5) {
                    mUltimate2Db.setValue(1);
                    ultimateImageView.setVisibility(View.INVISIBLE);
                    ultimateButton.setVisibility(View.VISIBLE);
                    if (character == "donald") {
                        sound.playTrumpUltimateGetSound();
                    } else {
                        sound.playGetUltimate1Sound();
                    }
                    if(player2mana < 5) {
                        ultimateButton.setEnabled(false);
                        ultimateButton.setText("Out of mana");
                    }
                    else if(player2mana >= 5) {
                        ultimateButton.setEnabled(true);
                        ultimateButton.setText("Ultimate");
                    }
                }
            }
        });

        ultimateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUltimate2Db.setValue(2);
                mMana1Db.setValue(player2mana - 5);
                //mp1.changeBar(-5);
                manaImageView.setVisibility(View.INVISIBLE);
                mana_2ImageView.setVisibility(View.INVISIBLE);
                mana5ImageView.setVisibility(View.INVISIBLE);
                bonusHealth.setVisibility(View.INVISIBLE);
                handler9.removeCallbacks(runnable9);
                handler9_2.removeCallbacks(runnable9_2);
                handler10.removeCallbacks(runnable10);
                handler10_2.removeCallbacks(runnable10_2);
                handler11.removeCallbacks(runnable11);
                handler12.removeCallbacks(runnable12);
                handler14.removeCallbacks(runnable14);
                handler15.removeCallbacks(runnable15);
                ultimateButton.setEnabled(false);
                ultimateButton.setVisibility(View.INVISIBLE);
                attackButton.setEnabled(false);

                if(character2 == "donald") {
                    hahmo1_1.setImageResource(R.drawable.donald_attack);
                    interval5 = 8000;
                    gifTextView3.setBackgroundResource(R.drawable.donald_gif);
                    ultimateAttack.setImageResource(R.drawable.brick1);
                    sound.playTrumpUltimateSound();
                } else if(character2 == "kim") {
                    hahmo1_1.setImageResource(R.drawable.kim_attack);
                    interval5 = 11000;
                    gifTextView3.setBackgroundResource(R.drawable.kim_gif);
                    ultimateAttack.setImageResource(R.drawable.bomb1);
                    sound.playKimUltimateSound();
                } else if(character2 == "pikachu") {
                    interval5 = 8000;
                    hahmo1_1.setImageResource(R.drawable.pikachu_attack);
                    gifTextView3.setBackgroundResource(R.drawable.pikachu_gif);
                    ultimateAttack.setImageResource(R.drawable.thunder);
                    sound.playPikachuUltimateSound();
                } else if(character2 == "arnold") {
                    interval5 = 8000;
                    hahmo1_1.setImageResource(R.drawable.arnold_attack);
                    gifTextView3.setBackgroundResource(R.drawable.arnold_gif);
                    ultimateAttack.setImageResource(R.drawable.knife);
                    sound.playArnoldUltimateSound();
                }

                gifTextView3.setVisibility(View.VISIBLE);       //TODO: Tulee laittaa "oikeat" gifit
                handler5.postAtTime(runnable5, System.currentTimeMillis()+interval5);
                handler5.postDelayed(runnable5, interval5);
                handler3.removeCallbacks(runnable3);
            }
        });



        hahmo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character2 = "donald";
                mChar2Db.setValue(1);     //hahmo 1 valittu
                hahmo1_1.setImageResource(R.drawable.donald_idle);

                HideSelectScreen();
                setPlayer1();
            }
        });

        hahmo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character2 = "kim";
                mChar2Db.setValue(2);     //hahmo 2 valittu
                hahmo1_1.setImageResource(R.drawable.kim_idle);

                HideSelectScreen();
                setPlayer1();
            }
        });

        hahmo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character2 = "pikachu";
                mChar2Db.setValue(3);     //hahmo 3 valittu
                hahmo1_1.setImageResource(R.drawable.pikachu_idle);

                HideSelectScreen();
                setPlayer1();
            }
        });

        hahmo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                character2 = "arnold";
                mChar2Db.setValue(4);     //hahmo 4 valittu
                hahmo1_1.setImageResource(R.drawable.arnold_idle);

                HideSelectScreen();
                setPlayer1();
            }
        });

        manaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMana1Db.setValue(player2mana + 2);
                //mp1.changeBar(2);
                manaImageView.setVisibility(View.INVISIBLE);
                attackButton.setEnabled(true);
                attackButton.setText("Attack");

                handler9.removeCallbacks(runnable9);
                handler10.removeCallbacks(runnable10);

                Random rechargeMana = new Random();
                Integer rechargeManaInt = rechargeMana.nextInt(2500) + 500;   // 500 is minimum, 2500+500 is maximum

                handler9.postAtTime(runnable9, System.currentTimeMillis()+rechargeManaInt);
                handler9.postDelayed(runnable9, rechargeManaInt);
            }
        });

        mana_2ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMana1Db.setValue(player2mana + 2);
                //mp1.changeBar(2);
                mana_2ImageView.setVisibility(View.INVISIBLE);
                attackButton.setEnabled(true);
                attackButton.setText("Attack");

                handler9_2.removeCallbacks(runnable9_2);
                handler10_2.removeCallbacks(runnable10_2);

                Random rechargeMana_2 = new Random();
                Integer rechargeMana_2Int = rechargeMana_2.nextInt(2500) + 500;   // 500 is minimum, 2500+500 is maximum

                handler9_2.postAtTime(runnable9_2, System.currentTimeMillis()+rechargeMana_2Int);
                handler9_2.postDelayed(runnable9_2, rechargeMana_2Int);
            }
        });

        mana5ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMana1Db.setValue(player2mana + 5);
                //mp1.changeBar(5);
                mana5ImageView.setVisibility(View.INVISIBLE);
                attackButton.setEnabled(true);
                attackButton.setText("Attack");

                handler11.removeCallbacks(runnable11);
                handler12.removeCallbacks(runnable12);

                Random rechargeMana5 = new Random();
                Integer rechargeMana5Int = rechargeMana5.nextInt(3500) + 5500;   // 5500 is minimum, 3500+5500 is maximum

                handler11.postAtTime(runnable11, System.currentTimeMillis()+rechargeMana5Int);
                handler11.postDelayed(runnable11, rechargeMana5Int);
            }
        });

        bonusHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bonusHealth.setVisibility(View.INVISIBLE);
                mHealth2Db.setValue(player2health + 5);
                mBonusHealth2Db.setValue(myBonusHealth+1);

                handler14.removeCallbacks(runnable14);
                handler15.removeCallbacks(runnable15);

                handler14.postAtTime(runnable14, System.currentTimeMillis()+randomNumber2);
                handler14.postDelayed(runnable14, randomNumber2);
            }
        });


        mP1Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);

                if(stringi == 3)
                {
                    hp1.setVisibility(View.VISIBLE);
                    mp1.setVisibility(View.VISIBLE);
                    hp2.setVisibility(View.VISIBLE);
                    mp2.setVisibility(View.VISIBLE);

                    gifTextView2.setVisibility(View.VISIBLE);
                    gifTextView2.setBackgroundResource(R.drawable.asdf);
                    gifTextView4.setVisibility(View.VISIBLE);
                    handler.postAtTime(runnable, System.currentTimeMillis()+interval);
                    handler.postDelayed(runnable, interval);
                    handler2.postAtTime(runnable2, System.currentTimeMillis()+randomNumber);
                    handler2.postDelayed(runnable2, randomNumber);
                    handler9.postAtTime(runnable9, System.currentTimeMillis()+interval9);
                    handler9.postDelayed(runnable9, interval9);
                    handler9_2.postAtTime(runnable9_2, System.currentTimeMillis()+interval9_2);
                    handler9_2.postDelayed(runnable9_2, interval9_2);
                    handler11.postAtTime(runnable11, System.currentTimeMillis()+interval11);
                    handler11.postDelayed(runnable11, interval11);
                    handler14.postAtTime(runnable14, System.currentTimeMillis()+randomNumber2);
                    handler14.postDelayed(runnable14, randomNumber2);


                    health1TV.setVisibility(View.VISIBLE);
                    health2TV.setVisibility(View.VISIBLE);
                    attackButton.setVisibility(View.VISIBLE);
                    mana1TV.setVisibility(View.VISIBLE);
                    mana2TV.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mChar1Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                player1choice = stringi;

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mChar2Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mHealth1Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                player1health = stringi;
                health2TV.setText(stringi.toString());
                hp2.setBar(stringi);

                if(stringi<=0){
                    attackButton.setEnabled(false);
                    winningTV.setVisibility(View.VISIBLE);
                    winningTV.setEnabled(false);
                    handler13.postAtTime(runnable13, System.currentTimeMillis()+interval13);
                    handler13.postDelayed(runnable13, interval13);
                    ultimateImageView.setVisibility(View.INVISIBLE);
                    ultimateButton.setEnabled(false);
                    handler2.removeCallbacks(runnable2);    //Stops the ultimate from appearing if the game is already over
                    handler4.removeCallbacks(runnable4);
                    handler9.removeCallbacks(runnable9);
                    handler9_2.removeCallbacks(runnable9_2);
                    handler10.removeCallbacks(runnable10);
                    handler10_2.removeCallbacks(runnable10_2);
                    manaImageView.setVisibility(View.INVISIBLE);
                    mana_2ImageView.setVisibility(View.INVISIBLE);
                    handler11.removeCallbacks(runnable11);
                    handler12.removeCallbacks(runnable12);
                    mana5ImageView.setVisibility(View.INVISIBLE);
                    bonusHealth.setVisibility(View.INVISIBLE);
                    handler14.removeCallbacks(runnable14);
                    handler15.removeCallbacks(runnable15);

                    if(character == "donald") {
                        hahmo2_1.setImageResource(R.drawable.donald_defeat);
                        sound.playTrumpWinningSound();
                    } else if (character == "kim") {
                        hahmo2_1.setImageResource(R.drawable.kim_defeat);
                        sound.playKimWinningSound();
                    } else if (character == "pikachu") {
                        hahmo2_1.setImageResource(R.drawable.pikachu_defeat);
                        sound.playPikachuWinningSound();
                    } else if (character == "arnold") {
                        hahmo2_1.setImageResource(R.drawable.arnold_defeat);
                        sound.playArnoldWinningSound();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mHealth2Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                player2health = stringi;
                health1TV.setText(stringi.toString());
                hp1.setBar(stringi);
                if(oldHealth > player2health) {
                    playHitSound();


                    if (character == "donald") {
                        hahmo2_1.setImageResource(R.drawable.donald_attack);
                    } else if (character == "kim") {
                        hahmo2_1.setImageResource(R.drawable.kim_attack);
                    } else if (character == "pikachu") {
                        hahmo2_1.setImageResource(R.drawable.pikachu_attack);
                    } else if (character == "arnold") {
                        hahmo2_1.setImageResource(R.drawable.arnold_attack);
                    }

                    handler4.postAtTime(runnable4, System.currentTimeMillis() + interval4);
                    handler4.postDelayed(runnable4, interval4);
                }

                oldHealth = player2health;

                if(stringi<=0){
                    attackButton.setEnabled(false);
                    winningTV.setText("Defeat");
                    winningTV.setVisibility(View.VISIBLE);
                    winningTV.setEnabled(false);
                    handler13.postAtTime(runnable13, System.currentTimeMillis()+interval13);
                    handler13.postDelayed(runnable13, interval13);
                    ultimateImageView.setVisibility(View.INVISIBLE);
                    ultimateButton.setEnabled(false);
                    handler2.removeCallbacks(runnable2);    //Stops the ultimate from appearing if the game is already over
                    handler4.removeCallbacks(runnable4);
                    handler9.removeCallbacks(runnable9);
                    handler9_2.removeCallbacks(runnable9_2);
                    handler10.removeCallbacks(runnable10);
                    handler10_2.removeCallbacks(runnable10_2);
                    manaImageView.setVisibility(View.INVISIBLE);
                    mana_2ImageView.setVisibility(View.INVISIBLE);
                    handler11.removeCallbacks(runnable11);
                    handler12.removeCallbacks(runnable12);
                    mana5ImageView.setVisibility(View.INVISIBLE);
                    bonusHealth.setVisibility(View.INVISIBLE);
                    handler14.removeCallbacks(runnable14);
                    handler15.removeCallbacks(runnable15);

                    if(character2 == "donald") {
                        hahmo1_1.setImageResource(R.drawable.donald_defeat);
                        sound.playTrumpLosingSound();
                    } else if (character2 == "kim") {
                        hahmo1_1.setImageResource(R.drawable.kim_defeat);
                        sound.playKimLosingSound();
                    } else if (character2 == "pikachu") {
                        hahmo1_1.setImageResource(R.drawable.pikachu_defeat);
                        sound.playPikachuLosingSound();
                    } else if (character2 == "arnold") {
                        hahmo1_1.setImageResource(R.drawable.arnold_defeat);
                        sound.playArnoldLosingSound();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mUltimate1Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                if(stringi == 1) {
                    ultimateImageView.setVisibility(View.INVISIBLE);
                }
                if(stringi == 2) {
                    attackButton.setEnabled(false);
                    //ultimateButton.setEnabled(false);
                    manaImageView.setVisibility(View.INVISIBLE);
                    mana_2ImageView.setVisibility(View.INVISIBLE);
                    mana5ImageView.setVisibility(View.INVISIBLE);
                    handler9.removeCallbacks(runnable9);
                    handler9_2.removeCallbacks(runnable9_2);
                    handler10.removeCallbacks(runnable10);
                    handler10_2.removeCallbacks(runnable10_2);
                    handler11.removeCallbacks(runnable11);
                    handler12.removeCallbacks(runnable12);
                    bonusHealth.setVisibility(View.INVISIBLE);
                    handler14.removeCallbacks(runnable14);
                    handler15.removeCallbacks(runnable15);

                    if(character == "donald") {
                        hahmo2_1.setImageResource(R.drawable.donald_attack);
                        ultimateProjectileInterval = 8000;
                        gifTextView3.setBackgroundResource(R.drawable.donald_gif);
                        ultimateAttack2.setImageResource(R.drawable.brick1);
                        sound.playTrumpUltimateSound();
                    } else if(character == "kim") {
                        ultimateProjectileInterval = 11000;
                        hahmo2_1.setImageResource(R.drawable.kim_attack);
                        gifTextView3.setBackgroundResource(R.drawable.kim_gif);
                        ultimateAttack2.setImageResource(R.drawable.bomb1);
                        sound.playKimUltimateSound();
                    } else if(character == "pikachu") {
                        ultimateProjectileInterval = 8000;
                        hahmo2_1.setImageResource(R.drawable.pikachu_attack);
                        gifTextView3.setBackgroundResource(R.drawable.pikachu_gif);
                        ultimateAttack2.setImageResource(R.drawable.thunder);
                        sound.playPikachuUltimateSound();
                    } else if(character == "arnold") {
                        ultimateProjectileInterval = 8000;
                        hahmo2_1.setImageResource(R.drawable.arnold_attack);
                        gifTextView3.setBackgroundResource(R.drawable.arnold_gif);
                        ultimateAttack2.setImageResource(R.drawable.knife);
                        sound.playArnoldUltimateSound();
                    }

                    gifTextView3.setVisibility(View.VISIBLE);       //TODO: Tulee laittaa "oikeat" gifit

                    ultimateProjectileHandler.postAtTime(runnable7, System.currentTimeMillis()+ ultimateProjectileInterval);
                    ultimateProjectileHandler.postDelayed(runnable7, ultimateProjectileInterval);
                    handler4.removeCallbacks(runnable4);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mUltimate2Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                ultimateStance = stringi;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mRndAikaDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                randomNumber = stringi;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mRndAika2Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                randomNumber2 = stringi;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mMana1Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                player2mana = stringi;
                mana1TV.setText(stringi.toString());
                mp1.setBar(stringi);

                if(player2mana == 0) {
                    attackButton.setEnabled(false);
                    attackButton.setText("Out of mana");
                }
                if(player2mana < 5 ) {
                    ultimateButton.setEnabled(false);
                    if(ultimateStance != 2) {
                        ultimateButton.setText("Out of mana");
                    }
                }
                if(player2mana >= 5 && ultimateStance == 1) {
                    ultimateButton.setEnabled(true);
                    ultimateButton.setText("Ultimate");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mMana2Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                player1mana = stringi;
                mana2TV.setText(stringi.toString());
                mp2.setBar(stringi);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mBonusHealth1Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                enemyBonusHealth = stringi;

                bonusHealth.setVisibility(View.INVISIBLE);
                handler14.removeCallbacks(runnable14);
                handler15.removeCallbacks(runnable15);

                handler14.postAtTime(runnable14, System.currentTimeMillis()+randomNumber2);
                handler14.postDelayed(runnable14, randomNumber2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mBonusHealth2Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer stringi = dataSnapshot.getValue(Integer.class);
                myBonusHealth = stringi;

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onClick(View v) {
        mP1Db.setValue(1);
        mChar1Db.setValue(0);
        mChar2Db.setValue(0);
        mHealth1Db.setValue(30);
        mHealth2Db.setValue(30);
        mUltimate1Db.setValue(0);
        mUltimate2Db.setValue(0);
        mMana2Db.setValue(3);
        mMana1Db.setValue(3);
        finish();
    }

    public void setPlayer1(){
        if(player1choice == 1) {
            hahmo2_1.setImageResource(R.drawable.donald_idle);
            hahmo2_1.setVisibility(View.VISIBLE);
            character = "donald";
        }
        else if(player1choice == 2) {
            hahmo2_1.setImageResource(R.drawable.kim_idle);
            hahmo2_1.setVisibility(View.VISIBLE);
            character = "kim";
        }
        else if(player1choice == 3) {
            hahmo2_1.setImageResource(R.drawable.pikachu_idle);
            hahmo2_1.setVisibility(View.VISIBLE);
            character = "pikachu";
        }
        else if(player1choice == 4) {
            hahmo2_1.setImageResource(R.drawable.arnold_idle);
            hahmo2_1.setVisibility(View.VISIBLE);
            character = "arnold";
        }
    }

    public void HideSelectScreen(){
        mP1Db.setValue(3);

        hahmo1.setVisibility(View.INVISIBLE);
        hahmo2.setVisibility(View.INVISIBLE);
        hahmo3.setVisibility(View.INVISIBLE);
        hahmo4.setVisibility(View.INVISIBLE);
        hahmo1tv.setVisibility(View.INVISIBLE);
        hahmo2tv.setVisibility(View.INVISIBLE);
        hahmo3tv.setVisibility(View.INVISIBLE);
        hahmo4tv.setVisibility(View.INVISIBLE);

        hahmo1_1.setVisibility(View.VISIBLE);
    }

    public void playHitSound(){
        sound.playHit1Sound();
    }
}
