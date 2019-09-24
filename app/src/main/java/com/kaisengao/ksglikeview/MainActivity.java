package com.kaisengao.ksglikeview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.kaisengao.likeview.like.KsgLikeView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @ClassName: MainActivity
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:23
 * @Description: 啦啦啦啦啦 德玛西亚
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private KsgLikeView mLikeView;

    private Button mMore;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        mLikeView = findViewById(R.id.live_view);

        findViewById(R.id.live_view_single).setOnClickListener(this);

        mMore = findViewById(R.id.live_view_more);
        mMore.setOnClickListener(this);

        mLikeView.addLikeImage(R.drawable.heart0);
        mLikeView.addLikeImage(R.drawable.heart1);
        mLikeView.addLikeImage(R.drawable.heart2);
        mLikeView.addLikeImage(R.drawable.heart3);
        mLikeView.addLikeImage(R.drawable.heart4);
        mLikeView.addLikeImage(R.drawable.heart5);
        mLikeView.addLikeImage(R.drawable.heart6);
        mLikeView.addLikeImage(R.drawable.heart7);
        mLikeView.addLikeImage(R.drawable.heart8);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.live_view_single:

                mLikeView.addFavor();

                break;
            case R.id.live_view_more:

                boolean selected = mMore.isSelected();

                if (selected) {
                    mHandler.removeCallbacks(mRunnable);
                } else {
                    mHandler.postDelayed(mRunnable, 100);
                }

                mMore.setSelected(!selected);

                break;
            default:
                break;
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            mLikeView.addFavor();

            mHandler.postDelayed(mRunnable, 100);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }


}
