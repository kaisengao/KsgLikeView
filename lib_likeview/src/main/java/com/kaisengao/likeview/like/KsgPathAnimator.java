package com.kaisengao.likeview.like;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

/**
 * @ClassName: KsgPathAnimator
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:45
 * @Description: 飘心路径缓存
 */
public class KsgPathAnimator extends BasePathAnimator {

    /**
     * 进入、曲线动画时长
     */
    private int mEnterDuration, mCurveDuration;
    /**
     * pic 宽 高
     */
    private int mPicWidth, mPicHeight;

    /**
     * View 宽 高
     */
    private int mViewWidth, mViewHeight;

    /**
     * 已经生成的路径数目
     */
    private int mCurrentPathCounts;

    /**
     * 已经生成的路径缓存
     */
    private SparseArray<CurveEvaluator> mPathArray;

    KsgPathAnimator(int enterDuration, int curveDuration) {

        this.mPathArray = new SparseArray<>();

        this.mEnterDuration = enterDuration;

        this.mCurveDuration = curveDuration;
    }

    void setPic(int picWidth, int picHeight) {

        this.mPicWidth = picWidth;

        this.mPicHeight = picHeight;
    }

    void setView(int viewWidth, int viewHeight) {

        this.mViewWidth = viewWidth;

        this.mViewHeight = viewHeight;
    }

    @Override
    public void start(View child, ViewGroup parent, RelativeLayout.LayoutParams layoutParams) {

        parent.addView(child, layoutParams);

        CurveEvaluator evaluator;

        // 记录已生成路径
        ++mCurrentPathCounts;

        // 如果已经生成的路径数目超过最大设定，就从路径缓存中随机取一个路径用于绘制，否则新生成一个
        if (mCurrentPathCounts > MAX_PATH_COUNTS) {
            evaluator = mPathArray.get(Math.abs(mRandom.nextInt() % MAX_PATH_COUNTS) + 1);
        } else {
            evaluator = createPath(getTogglePoint(1), getTogglePoint(2));
            mPathArray.put(mCurrentPathCounts, evaluator);
        }

        // 设置进入动画
        AnimatorSet enterAnimator = generateEnterAnimation(child);
        // 设置路径动画
        ValueAnimator curveAnimator = generateCurveAnimation(evaluator, child);

        // 执行动画集合
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(enterAnimator, curveAnimator);
        animatorSet.addListener(new AnimationEndListener(child, parent));
        animatorSet.start();
    }

    /**
     * 进入动画
     *
     * @return 动画集合
     */
    private AnimatorSet generateEnterAnimation(View target) {
        AnimatorSet enterAnimation = new AnimatorSet();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        // 加一些动画差值器
        enterAnimation.setInterpolator(new LinearInterpolator());
        enterAnimation.playTogether(alpha, scaleX, scaleY);
        enterAnimation.setDuration(mEnterDuration);

        return enterAnimation;
    }

    /**
     * 贝赛尔曲线动画
     *
     * @return 动画集合
     */
    private ValueAnimator generateCurveAnimation(CurveEvaluator evaluator, View target) {
        // 贝塞尔曲线动画
        PointF pointF1 = new PointF((float) (mViewWidth - mPicWidth) / 2, mViewHeight - mPicHeight);

        float endX = (float) ((mViewWidth - mPicWidth) / 2) + ((mRandom.nextBoolean() ? 1 : -1) * mRandom.nextInt(100));

        PointF pointF2 = new PointF(endX, 0);

        ValueAnimator curveAnimator = ValueAnimator.ofObject(evaluator, pointF1, pointF2);

        curveAnimator.addUpdateListener(new CurveUpdateLister(target));
        curveAnimator.setInterpolator(new LinearInterpolator());
        curveAnimator.setDuration(mCurveDuration);

        return curveAnimator;
    }

    private PointF getTogglePoint(int scale) {
        PointF pointf = new PointF();
        // 减去100 是为了控制 x轴活动范围
        pointf.x = mRandom.nextInt((mViewWidth - 100));
        // 再Y轴上 为了确保第二个控制点 在第一个点之上,我把Y分成了上下两半
        pointf.y = (float) mRandom.nextInt((mViewHeight - 100)) / scale;
        return pointf;
    }

    /**
     * 动画曲线路径更新监听器, 用于动态更新动画作用对象的位置
     */
    private class CurveUpdateLister implements ValueAnimator.AnimatorUpdateListener {

        private View mTarget;

        CurveUpdateLister(View target) {

            this.mTarget = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            // 获取当前动画运行的状态值, 使得动画作用对象沿着曲线(涉及贝塞儿曲线)运动
            PointF value = (PointF) animation.getAnimatedValue();
            mTarget.setX(value.x);
            mTarget.setY(value.y);
            // 改变对象的透明度
            mTarget.setAlpha(1 - animation.getAnimatedFraction());
        }
    }

    /**
     * 动画结束监听器,用于释放无用的资源
     */
    private class AnimationEndListener extends AnimatorListenerAdapter {

        private View mTarget;

        private ViewGroup mParent;

        AnimationEndListener(View target, ViewGroup parent) {

            this.mTarget = target;

            this.mParent = parent;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            mParent.removeView(mTarget);
        }
    }
}
