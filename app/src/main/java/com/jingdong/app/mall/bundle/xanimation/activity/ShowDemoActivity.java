package com.jingdong.app.mall.bundle.xanimation.activity;

import static com.jingdong.app.mall.bundle.xanimation.XAnimationConstants.ASSETS_FADE_IN_ANI;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.jingdong.app.mall.bundle.xanimation.XAnimation;
import com.jingdong.app.mall.bundle.xanimation.XAnimationOptions;
import com.jingdong.app.mall.bundle.xanimation.app.R;
import com.jingdong.app.mall.bundle.xanimation.bean.LottieDemo;
import com.jingdong.app.mall.bundle.xanimation.interfaces.IXAnimationListener;
import com.jingdong.app.mall.bundle.xanimation.interfaces.XAnimationType;

public class ShowDemoActivity extends AppCompatActivity {
    private TextView tvTitle, tvBack, tvLog, tvProgress;
    private LinearLayout llContent;
    private Button btnPlay, btnPause, btnResume;
    private ImageView imageLeft;
    private EditText edInput;
    private LottieAnimationView mLottieAnimationView;
    private SeekBar mSeekBar;
    private LottieDemo mLottieDemo;
    private XAnimation xAnimation = new XAnimation();

    public static void start(Context context, LottieDemo lottieDemo) {
        Intent starter = new Intent(context, ShowDemoActivity.class);
        starter.putExtra("lottieDemo", lottieDemo);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_demo);
        mLottieDemo = (LottieDemo) getIntent().getSerializableExtra("lottieDemo");
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(v -> finish());
        tvLog = findViewById(R.id.tv_log);
        tvProgress = findViewById(R.id.tv_progress);
        llContent = findViewById(R.id.ll_content);
        imageLeft = findViewById(R.id.img_left);
        edInput = findViewById(R.id.ed_input);
        btnPlay = findViewById(R.id.btn_play);
        mLottieAnimationView = findViewById(R.id.lottieAnimationView);
        mLottieAnimationView.setImageAssetsFolder("images");
        mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        mLottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        btnPlay.setOnClickListener(v -> play());
        btnPause = findViewById(R.id.btn_pause);
        btnPause.setAlpha(0f);
        btnPause.setOnClickListener(v -> {
            mLottieAnimationView.pauseAnimation();
            xAnimation.pauseViewAnimation();
        });
        btnResume = findViewById(R.id.btn_resume);

        btnResume.setOnClickListener(v -> {
            mLottieAnimationView.resumeAnimation();
            xAnimation.resumeViewAnimation();
        });
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float p = progress / 100f;
                if (fromUser) {
                    xAnimation.setViewAnimationProgress(p);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (mLottieDemo != null) {
            switch (mLottieDemo.getType()) {
                case 1:
                    tvTitle.setText("绑定（旋转、平移、缩放）");
                    edInput.setText("card.json");
                    break;
                case 2:
                    tvTitle.setText("绑定（呼吸效果）");
                    edInput.setText("heart.json");
                    break;
                case 3:
                    tvTitle.setText("2个图层联动");
                    edInput.setText("windmill.json");
                    break;
                case 4:
                    tvTitle.setText("播放控制");
                    edInput.setText("windmill.json");
                    break;
                case 5:
                    tvTitle.setText("动态更换内容");
                    edInput.setText("breath.json");
                    break;
                case 6:
                    tvTitle.setText("渐变");
                    edInput.setText(ASSETS_FADE_IN_ANI);
                    break;
            }
        }
        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankActivity.start(ShowDemoActivity.this);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void play() {
        String jsonFileName = edInput.getText().toString();
        if (mLottieDemo != null && !TextUtils.isEmpty(jsonFileName)) {
            final int[] layerCounter = {0};
            StringBuffer stringBuffer = new StringBuffer();
            xAnimation.setXAnimationListener(new IXAnimationListener() {
                @Override
                public void onLottieLoadResult(LottieComposition composition) {
                    mLottieAnimationView.setComposition(composition);
                    mLottieAnimationView.playAnimation();
                }

                @Override
                public void onLayerStatusListener(String layerName, int status, int layerCount) {
                    //Log.d("XAnimation",  layerName + " floatValue: " + status);
                    stringBuffer.append(layerName).append(" : ").append(status == 1 ? "进入 " : "退出 ");
                    layerCounter[0]++;
                    if (layerCounter[0] == layerCount) {
                        tvLog.setText(stringBuffer);
                        stringBuffer.setLength(0);
                        layerCounter[0] = 0;
                    }
                }

                @Override
                public void onAnimationUpdate(float value) {
                    tvProgress.setText("进度：" + value * 100 + "%");

                    if (mLottieDemo.getType() == 5) {
                        int floor = (int) Math.floor(value * 10);
                        if (floor / 2 == 0) {
                            imageLeft.setImageResource(R.mipmap.wind);
                        } else {
                            imageLeft.setImageResource(R.mipmap.ic_launcher);
                        }
                    }
                }
            });
            XAnimationOptions xAnimationOptions = new XAnimationOptions()
                    //.withSpeed(0.1f)
                    .withRepeatCount(LottieDrawable.INFINITE);
            switch (mLottieDemo.getType()) {
                case 1:
                    xAnimationOptions.withLottieUrl(jsonFileName)
                            .withAnimationType(XAnimationType.TYPE_ASSIGN_VIEW_GROUP)
                            .withResourceEntryName("ll_content")
                            .withLayerName("牌子");
                    break;
                case 2:
                    xAnimationOptions.withLottieUrl(jsonFileName)
                            .withAnimationType(XAnimationType.TYPE_ASSIGN_VIEW)
                            .withResourceEntryName("img_left");
                    break;
                case 3:
                case 4:
                    xAnimationOptions.withLottieUrl(jsonFileName)
                            .withAnimationType(XAnimationType.TYPE_ASSIGN_VIEW_GROUP)
                            .withResourceEntryName("ll_content");
                    break;
                case 5:
                    xAnimationOptions.withLottieUrl(jsonFileName)
                            .withAnimationType(XAnimationType.TYPE_ASSIGN_VIEW)
                            .withLayerName("预合成 1")
                            .withResourceEntryName("img_left");
                    break;
                case 6:
                    xAnimationOptions.withLottieUrl(jsonFileName)
                            .withAnimationType(XAnimationType.TYPE_ASSIGN_VIEW)
                            .withLayerName("logo192.png")
                            .withResourceEntryName("img_left");

                    break;
            }
            xAnimation.startViewAnimation(ShowDemoActivity.this, xAnimationOptions);
        }
    }
}