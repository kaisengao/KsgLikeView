package com.ksg.likeview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @ClassName: MainActivity
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:23
 * @Description: 啦啦啦啦啦 德玛西亚
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }

    public void intentNormal(View view) {
        this.startActivity(new Intent(this, NormalActivity.class));
    }

    public void intentRecycler(View view) {
        this.startActivity(new Intent(this, RecyclerActivity.class));
    }

    public void intentViewpager2(View view) {
        this.startActivity(new Intent(this, Viewpager2Activity.class));
    }
}
