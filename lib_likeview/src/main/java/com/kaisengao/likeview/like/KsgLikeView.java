package com.kaisengao.likeview.like;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kaisengao.likeview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

/**
 * @ClassName: KsgLikeView
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:23
 * @Description: 飘心View
 */
public class KsgLikeView extends RelativeLayout {

    private List<Drawable> mLikeDrawables;

    private LayoutParams mLayoutParams;

    private KsgPathAnimator mPathAnimator;

    public KsgLikeView(Context context) {
        this(context, null);
    }

    public KsgLikeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KsgLikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initParams();
    }

    private void initParams() {
        this.mLikeDrawables = new ArrayList<>();
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart0));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart1));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart2));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart3));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart4));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart5));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart6));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart7));
        this.mLikeDrawables.add(generateDrawable(R.drawable.heart8));

        // 获取图片的宽高, 由于图片大小一致,故直接获取第一张图片的宽高
        int picWidth = mLikeDrawables.get(0).getIntrinsicWidth();
        int picHeight = mLikeDrawables.get(0).getIntrinsicHeight();

        // 初始化布局参数
        this.mLayoutParams = new RelativeLayout.LayoutParams(picWidth, picHeight);
        this.mLayoutParams.addRule(CENTER_HORIZONTAL);
        this.mLayoutParams.addRule(ALIGN_PARENT_BOTTOM);

        // 初始化飘心动画路径
        this.mPathAnimator = new KsgPathAnimator();
        this.mPathAnimator.setPic(picWidth, picHeight);
    }

    /**
     * 图片资源转Drawable
     *
     * @param resId R...icon...
     * @return Drawable
     */
    private Drawable generateDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mPathAnimator.setView(getWidth(), getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mPathAnimator.setView(getWidth(), getHeight());
    }

    /**
     * 添加一个 飘心
     */
    public void addFavor() {

        ImageView imageView = new ImageView(getContext());

        int position = Math.abs(mPathAnimator.mRandom.nextInt(mLikeDrawables.size()));

        imageView.setImageDrawable(mLikeDrawables.get(position));

        this.mPathAnimator.start(imageView, this, mLayoutParams);
    }
}
