package com.ksg.likeview;

import android.os.Bundle;

import com.ksg.likeview.adapter.Viewpager2Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @ClassName: Viewpager2Activity
 * @Author: KaiSenGao
 * @CreateDate: 8/5/21 10:25 PM
 * @Description: Viewpager2 列表模式
 */
public class Viewpager2Activity extends AppCompatActivity {

    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_viewpager2);
        // InitView
        this.initView();
    }

    /**
     * InitView
     */
    private void initView() {
        this.mViewPager2 = findViewById(R.id.viewPager2);
        // Init Adapter
        this.initAdapter();
    }

    /**
     * Init Adapter
     */
    private void initAdapter() {
        // Adapter
        Viewpager2Adapter adapter = new Viewpager2Adapter();
        // ViewPager
        this.mViewPager2.setAdapter(adapter);
    }
}