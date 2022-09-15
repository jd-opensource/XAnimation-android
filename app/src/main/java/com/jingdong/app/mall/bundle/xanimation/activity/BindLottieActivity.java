package com.jingdong.app.mall.bundle.xanimation.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingdong.app.mall.bundle.xanimation.adapter.LottieDemoAdapter;
import com.jingdong.app.mall.bundle.xanimation.app.R;
import com.jingdong.app.mall.bundle.xanimation.bean.LottieDemo;

import java.util.ArrayList;
import java.util.List;

public class BindLottieActivity extends AppCompatActivity {
    private RecyclerView rvDemos;
    private List<LottieDemo> lottieDemoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_lottie);

        rvDemos = findViewById(R.id.rv_demos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvDemos.setLayoutManager(linearLayoutManager);
        LottieDemoAdapter lottieDemoAdapter = new LottieDemoAdapter(this);
        lottieDemoAdapter.setOnItemClickListener(position -> {
            LottieDemo lottieDemo = lottieDemoList.get(position);
            ShowDemoActivity.start(BindLottieActivity.this, lottieDemo);
        });
        rvDemos.setAdapter(lottieDemoAdapter);
        lottieDemoList = new ArrayList<>();
        lottieDemoList.add(new LottieDemo(1, "演示1：绑定（旋转、平移、缩放）"));
        lottieDemoList.add(new LottieDemo(2, "演示2：绑定（呼吸效果）"));
        lottieDemoList.add(new LottieDemo(3, "演示3：2个图层联动"));
        lottieDemoList.add(new LottieDemo(4, "演示4：播放控制"));
        lottieDemoList.add(new LottieDemo(5, "演示5：动态更换内容"));
        lottieDemoList.add(new LottieDemo(6, "演示6：渐变"));
        lottieDemoAdapter.setData(lottieDemoList);
    }
}