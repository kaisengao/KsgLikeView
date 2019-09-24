package com.kaisengao.likeview.like;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kaisengao.likeview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: KsgLikeView
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:23
 * @Description: 飘心View
 */
public class KsgLikeView extends RelativeLayout {

    private final String TAG = KsgLikeView.class.getName();

    private List<Integer> mLikeDrawables;

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

        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.KsgLikeView);
        // 默认图片 根据他获取飘心图的宽高 *重要
        int defaultFavor = typedArray.getResourceId(R.styleable.KsgLikeView_ksg_default_image, -1);
        // 进入动画时长
        int enterDuration = typedArray.getInteger(R.styleable.KsgLikeView_ksg_enter_duration, 1500);
        // 曲线动画时长
        int curveDuration = typedArray.getInteger(R.styleable.KsgLikeView_ksg_curve_duration, 4500);

        typedArray.recycle();

        init(defaultFavor, enterDuration, curveDuration);
    }

    private void init(int defaultFavor, int enterDuration, int curveDuration) {
        this.mLikeDrawables = new ArrayList<>();

        if (defaultFavor == -1) {

            defaultFavor = R.drawable.default_favor;

            Log.e(TAG, "please pass in the default image resource !");
        }

        // 获取图片资源
        Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), defaultFavor));

        // 获取图片的宽高
        int picWidth = drawable.getIntrinsicWidth();
        int picHeight = drawable.getIntrinsicHeight();

        // 初始化布局参数
        this.mLayoutParams = new RelativeLayout.LayoutParams(picWidth, picHeight);
        this.mLayoutParams.addRule(CENTER_HORIZONTAL);
        this.mLayoutParams.addRule(ALIGN_PARENT_BOTTOM);

        // 初始化飘心动画路径
        this.mPathAnimator = new KsgPathAnimator(enterDuration, curveDuration);
        this.mPathAnimator.setPic(picWidth, picHeight);
    }

    public void addLikeImage(int resId) {
        this.mLikeDrawables.add(resId);
    }

    public void addLikeImages(Integer[] resIds) {
        this.mLikeDrawables.addAll(Arrays.asList(resIds));
    }

    public void addLikeImages(List<Integer> resIds) {
        this.mLikeDrawables.addAll(resIds);
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

        ImageView favor = new ImageView(getContext());

        int position = Math.abs(mPathAnimator.mRandom.nextInt(mLikeDrawables.size()));

        favor.setImageResource(mLikeDrawables.get(position));

        this.mPathAnimator.start(favor, this, mLayoutParams);
    }
}
