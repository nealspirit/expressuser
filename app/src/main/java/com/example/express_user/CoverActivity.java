package com.example.express_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class CoverActivity extends AppCompatActivity {
    private ImageView packet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏系统状态栏

        packet = findViewById(R.id.packet);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1,18,1,18, Animation.RELATIVE_TO_SELF,0.3f,Animation.RELATIVE_TO_SELF,0.7f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new AccelerateInterpolator(7));
        packet.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(CoverActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_anim,R.anim.enter_anim);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
