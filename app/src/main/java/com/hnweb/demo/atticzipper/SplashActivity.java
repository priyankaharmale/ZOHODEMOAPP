package com.hnweb.demo.atticzipper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by hnwebmarketing on 6/6/2017.
 */
public class SplashActivity extends Activity {

    Button GetStarted,RValueCalculator;
    SharedPreferences.Editor editor;
    String isfirstLogin,Internachinumber;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        pref= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        GetStarted = (Button)findViewById(R.id.startedBTN);
        RValueCalculator = (Button)findViewById(R.id.atticBTN);
        isfirstLogin = pref.getString("internachiFirstTime", null);

        Internachinumber = pref.getString("internachinumber", "internachinovalue");

        System.out.println("LoingIs"+isfirstLogin);
        GetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor = pref.edit();
                if(Internachinumber.equals("internachinovalue"))
                {

                    Intent i = new Intent(SplashActivity.this,InterNachiLoginActivity.class);
                    editor.putString("internachiFirstTime", "1");
                    editor.commit();
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(SplashActivity.this,InterNachiLoginActivity.class);
                    editor.putString("internachiFirstTime", "2");
                    editor.commit();
                    startActivity(i);
                    finish();
                }


            }
        });


        RValueCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashActivity.this,AttaicRCalculator.class);  //CalculatorActivity
                startActivity(i);
                finish();
                //Toast.makeText(SplashActivity.this, "Working On", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
