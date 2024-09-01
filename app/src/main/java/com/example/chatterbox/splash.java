package com.example.chatterbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    ImageView logo;
    TextView name,own1,own2;
    Animation topAnim,bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        logo=findViewById(R.id.logoimg);
        name=findViewById(R.id.logoNameimg);
        own1=findViewById(R.id.ownOne);
        own2=findViewById(R.id.ownTwo);


        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo.setAnimation(topAnim); //apply the animation to respective UI components
        name.setAnimation(bottomAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);

        //Handler to handle the timed transitions
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splash.this, registration.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}