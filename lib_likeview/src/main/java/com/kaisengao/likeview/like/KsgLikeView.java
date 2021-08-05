package com.kaisengao.likeview.like;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.kaisengao.likeview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @ClassName: KsgLikeView
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:23
 * @Description: 飘心View
 */
public class KsgLikeView extends AnimationLayout {

    private int mEnterDuration;

    private int mCurveDuration;

    private final List<Integer> mLikeRes;

    public KsgLikeView(Context context) {
        this(context, null);
    }

    public KsgLikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Init TypedArray
        this.initTypedArray(attrs);
        // Create Resource List
        this.mLikeRes = new ArrayList<>();
    }

    /**
     * Init TypedArray
     */
    private void initTypedArray(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.KsgLikeView);
        // 进入动画时长
        this.mEnterDuration = typedArray.getInteger(R.styleable.KsgLikeView_ksg_enter_duration, 1500);
        // 路径动画时长
        this.mCurveDuration = typedArray.getInteger(R.styleable.KsgLikeView_ksg_curve_duration, 4500);
        // 回收
        typedArray.recycle();
    }

    /**
     * 添加 资源文件
     *
     * @param resId resId
     */
    @Override
    public void addLikeImage(int resId) {
        this.addLikeImages(resId);
    }

    /**
     * 添加 资源文件组
     *
     * @param resIds resIds
     */
    @Override
    public void addLikeImages(Integer... resIds) {
        this.addLikeImages(Arrays.asList(resIds));
    }

    /**
     * 添加 资源文件列表
     *
     * @param resIds resIds
     */
    @Override
    public void addLikeImages(List<Integer> resIds) {
        this.mLikeRes.addAll(resIds);
    }

    /**
     * 添加 发送
     */
    @Override
    public void addFavor() {
        // 非空验证
        if (mLikeRes.isEmpty()) {
            throw new NullPointerException("Missing resource file！");
        }
        // 随机获取一个资源
        int favorRes = Math.abs(mLikeRes.get(mRandom.nextInt(mLikeRes.size())));
        // 生成 配置参数
        LayoutParams layoutParams = createLayoutParams(favorRes);
        // 创建一个资源View
        AppCompatImageView favorView = new AppCompatImageView(getContext());
        favorView.setImageResource(favorRes);
        // 开始执行动画
        this.start(favorView, layoutParams);
    }

    /**
     * 生成 配置参数
     */
    private LayoutParams createLayoutParams(int crystalLeaf) {
        // 获取图片信息
        this.getPictureInfo(crystalLeaf);
        // 初始化布局参数
        return new LayoutParams((int) mPicWidth, (int) mPicHeight, Gravity.BOTTOM | Gravity.CENTER);
    }

    /**
     * 开始执行动画
     *
     * @param child        child
     * @param layoutParams layoutParams
     */
    private void start(View child, LayoutParams layoutParams) {
        // 设置进入动画
        AnimatorSet enterAnimator = generateEnterAnimation(child);
        // 设置路径动画
        ValueAnimator curveAnimator = generateCurveAnimation(child);
        // 执行动画集合
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(curveAnimator, enterAnimator);
        animatorSet.addListener(new AnimationEndListener(child, animatorSet));
        animatorSet.start();
        // add父布局
        super.addView(child, layoutParams);
    }

    /**
     * 进入动画
     *
     * @return 动画集合
     */
    private AnimatorSet generateEnterAnimation(View child) {
        AnimatorSet enterAnimation = new AnimatorSet();
        enterAnimation.playTogether(
                ObjectAnimator.ofFloat(child, View.ALPHA, 0.2f, 1f),
                ObjectAnimator.ofFloat(child, View.SCALE_X, 0.2f, 1f),
                ObjectAnimator.ofFloat(child, View.SCALE_Y, 0.2f, 1f));
        // 加一些动画差值器
        enterAnimation.setInterpolator(new LinearInterpolator());
        return enterAnimation.setDuration(mEnterDuration);
    }

    /**
     * 贝赛尔曲线动画
     *
     * @return 动画集合
     */
    private ValueAnimator generateCurveAnimation(View child) {
        // 起点 坐标
        PointF pointStart = new PointF((mViewWidth - mPicWidth) / 2, mViewHeight - mPicHeight);
        // 终点 坐标
        PointF pointEnd = new PointF(((mViewWidth - mPicWidth) / 2) + ((mRandom.nextBoolean() ? 1 : -1) * mRandom.nextInt(100)), 0);
        // 属性动画
        PointF pointF1 = getTogglePoint(1);
        PointF pointF2 = getTogglePoint(2);
        ValueAnimator curveAnimator = ValueAnimator.ofObject(mEvaluatorRecord.getCurrentPath(pointF1, pointF2), pointStart, pointEnd);
        curveAnimator.addUpdateListener(new CurveUpdateLister(child));
        curveAnimator.setInterpolator(new LinearInterpolator());
        return curveAnimator.setDuration(mCurveDuration);
    }

    private PointF getTogglePoint(int scale) {
        PointF pointf = new PointF();
        // 减去100 是为了控制 x轴活动范围
        pointf.x = mRandom.nextInt((mViewWidth - 100));
        // 再Y轴上 为了确保第二个控制点 在第一个点之上,我把Y分成了上下两半
        pointf.y = (float) mRandom.nextInt((mViewHeight - 100)) / scale;
        return pointf;
    }
}
