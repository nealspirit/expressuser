package com.example.express_user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private EditText phoneNum;
    private ImageView check_goto;
    private String PhoneNum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNum = findViewById(R.id.et_phonenum);
        check_goto = findViewById(R.id.iv_goto);

        check_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNum = phoneNum.getText().toString();
                if (PhoneNum == null || PhoneNum.length() == 0){
                    Toast.makeText(MainActivity.this, "手机号为空！", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this,ShowDeliveryActivity.class);
                    intent.putExtra("phoneNum",PhoneNum);
                    startActivity(intent);
                }
            }
        });
        //设置动画
        linearLayout = findViewById(R.id.linearLayout_main);
        AnimationSet animationSet = new AnimationSet(true);
        //设置位移动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(1000);
        animationSet.addAnimation(scaleAnimation);
        //设置透明动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);

        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animationSet,0.5f);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);//加载顺序：包括从上倒下(ORDER_NORMAL)、从下到上(ORDER_REVERSE)、随机(ORDER_RANDOM)
        linearLayout.setLayoutAnimation(layoutAnimationController);
    }
}
