package com.earnrecord.android;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;


public class BoderLayout extends FrameLayout {
    private int width, height;
    private Paint paint;
    private Paint bgPaint;
    private int currentLen;
    private int currentAlpha = 255;
    private static final String TAG = "BoderLayout";


    public BoderLayout(@NonNull Context context) {
        this(context, null);
    }

    public BoderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoderLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(15);
        paint.setColor(Color.argb(255, 255, 0, 0));
        paint.setStyle(Paint.Style.STROKE);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(15);
        bgPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        startAnim();
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 75);
        animator.setDuration(1600);
        animator.setRepeatCount(0);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                currentLen = 0;
                currentAlpha = 255;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                currentLen = 75;
                startAlphaAnim();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentLen = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    private void startAlphaAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(255, 0);
        animator.setDuration(800);
        animator.setRepeatCount(0);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                currentAlpha=255;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                currentLen=0;
                startAnim();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAlpha = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        outrRect = new RectF(0, 0, width, height);
        innerRect = new RectF(50, 50, width - 50, height - 50);
        invalidate();
    }

    private RectF outrRect;
    private RectF innerRect;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        paint.setStrokeWidth(currentLen);
        paint.setColor(Color.argb(currentAlpha, 255, 0, 0));
        if (outrRect != null && innerRect != null) {
            canvas.drawRoundRect(innerRect, 50, 50, paint);
            canvas.drawRoundRect(innerRect, 50, 50, bgPaint);
        }

////        path = new Path();
//        path.moveTo(0, 0);
//        paint.setStrokeWidth(5+currentLen/5);
//        canvas.drawRoundRect(50,50,width-50,height-50,100,100,paint);

//
//        if (currentLen > SPACE) {
//
//
//            if (currentLen <= height) {
//                path.moveTo(0, currentLen);
//                path.lineTo(0, height);
//                path.lineTo(width, height);
//                path.lineTo(width, 0);
//                path.lineTo(0, 0);
//                path.lineTo(0, height-currentLen-SPACE);
//
//            } else if (currentLen <= height + SPACE) {
//                path.moveTo(currentLen - height, height);
//                path.lineTo(width, height);
//                path.lineTo(width, 0);
//                path.lineTo(0, 0);
//                if (currentLen - SPACE > height) {
//                    path.lineTo(0, height);
//                    path.lineTo(currentLen - SPACE - height, height);
//                } else {
//                    path.lineTo(0, currentLen - SPACE);
//                }
//            }
//        }
//            path.moveTo(0,currentLen);
//            path.lineTo(width,height);
//            path.lineTo(width,0);
//            path.lineTo(0,0);
//        }
//
//
//        if(currentLen<=height){
//            path.lineTo(0,currentLen);
//        }else if(currentLen<=halfLen){
//            path.lineTo(0,height);
//            path.lineTo(currentLen-height,height);
//        }else if(currentLen<=halfLen+height){
//            path.lineTo(0,height);
//            path.lineTo(width,height);
//            path.lineTo(width,halfLen+height-currentLen);
//        }else{
//            path.lineTo(0,height);
//            path.lineTo(width,height);
//            path.lineTo(width,0);
//            path.lineTo(totleLen-currentLen,0);
//        }
////        path.moveTo(0,0);
//////        if(currentLen<50){
////            path.moveTo(0,totleLen-currentLen);
////        }
//        if(currentLen<=height){
//            path.lineTo(0,currentLen);
//        }else if(currentLen<=halfLen){
//            path.lineTo(0,height);
//            path.lineTo(currentLen-height,height);
//        }else if(currentLen<=halfLen+height){
//            path.lineTo(0,height);
//            path.lineTo(width,height);
//            path.lineTo(width,halfLen+height-currentLen);
//        }else{
////            if(totleLen-currentLen<300){
////                path.moveTo(0,currentLen-(totleLen-300));
////            }
//            path.lineTo(0,height);
//            path.lineTo(width,height);
//            path.lineTo(width,0);
//            path.lineTo(totleLen-currentLen,0);
//        }
//
////        path.lineTo(0,height);
////        path.lineTo(width,height);
////        path.lineTo(width,0);
//
//        canvas.drawPath(path, paint);
    }

}
