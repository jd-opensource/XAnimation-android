package com.jingdong.app.mall.bundle.xanimation.activity;

import static com.jingdong.app.mall.bundle.xanimation.XAnimationConstants.ASSETS_FADE_IN_ANI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieDrawable;
import com.jingdong.app.mall.bundle.xanimation.XAnimation;
import com.jingdong.app.mall.bundle.xanimation.XAnimationOptions;
import com.jingdong.app.mall.bundle.xanimation.app.R;

public class BlankActivity extends AppCompatActivity {
    private ImageView imageView;
    private RelativeLayout rlRoot;

    public static void start(Context context) {
        Intent starter = new Intent(context, BlankActivity.class);
        //starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        imageView = findViewById(R.id.imageView);
        rlRoot = findViewById(R.id.rl_root);
        ImageView imageView1 = new ImageView(this);
        imageView1.setBackgroundResource(R.mipmap.ic_launcher);
        rlRoot.addView(imageView1);
        XAnimationOptions xAnimationOptions = new XAnimationOptions()
                .withLottieUrl(ASSETS_FADE_IN_ANI)
                .withTargetView(imageView1)
                .withRepeatCount(LottieDrawable.INFINITE);
        XAnimation xAnimation = new XAnimation();
        xAnimation.startViewAnimation(BlankActivity.this, xAnimationOptions);
    }
}