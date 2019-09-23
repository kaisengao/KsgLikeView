package com.kaisengao.likeview.like;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * @ClassName: BasePathAnimator
 * @Author: KaiSenGao
 * @CreateDate: 2019-09-17 14:27
 * @Description: Base飘心缓存路径
 */
public abstract class BasePathAnimator {

    /**
     * 最多生成的路径与飘心Image
     */
    final int MAX_PATH_COUNTS = 10;

    Random mRandom;

    BasePathAnimator() {

        this.mRandom = new Random();
    }

    /**
     * 小心 开始漂流
     *
     * @param child        child
     * @param parent       parent
     * @param layoutParams layoutParams
     */
    public abstract void start(View child, ViewGroup parent, RelativeLayout.LayoutParams layoutParams);

    /**
     * 生成 贝塞尔路径
     */
    CurveEvaluator createPath(PointF pointF1, PointF pointF2) {
        return new CurveEvaluator(pointF1, pointF2);
    }


    /**
     * 三阶贝赛尔曲线（原理不知道，数学不好!）
     */
    protected class CurveEvaluator implements TypeEvaluator<PointF> {

        private PointF mControlP1;

        private PointF mControlP2;

        private CurveEvaluator(PointF pointF1, PointF pointF2) {

            this.mControlP1 = pointF1;

            this.mControlP2 = pointF2;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

            float leftTime = 1.0f - fraction;

            PointF point = new PointF();

            point.x = (float) Math.pow(leftTime, 3) * startValue.x
                    + 3 * (float) Math.pow(leftTime, 2) * fraction * mControlP1.x
                    + 3 * leftTime * (float) Math.pow(fraction, 2) * mControlP2.x
                    + (float) Math.pow(fraction, 3) * endValue.x;

            point.y = (float) Math.pow(leftTime, 3) * startValue.y
                    + 3 * (float) Math.pow(leftTime, 2) * fraction * mControlP1.y
                    + 3 * leftTime * fraction * fraction * mControlP2.y
                    + (float) Math.pow(fraction, 3) * endValue.y;

            return point;
        }
    }

}
