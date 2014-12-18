package me.twmm.twdroid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import me.twmm.twdroid.R;

/**
 * Created by yy-mac on 12/17/14.
 */
public class TWProgressView extends View {

    private int firstColor;
    private int secondColor;
    private int speed;
    private int circleWidth;
    private Paint mPaint;
    private int mProgress;
    private String mPrompt;
    private Rect mTextBounds;

    public TWProgressView(Context context) {
        this(context, null);
    }

    public TWProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TWProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TWProgressView, 0, 0);

        firstColor = ta.getColor(R.styleable.TWProgressView_firstColor, Color.BLUE);
        secondColor = ta.getColor(R.styleable.TWProgressView_secondColor, Color.GREEN);
        speed = ta.getInt(R.styleable.TWProgressView_speed, 20);
        circleWidth = ta.getDimensionPixelSize(R.styleable.TWProgressView_circleWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
        mPrompt = "Loading";
        ta.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextBounds = new Rect();
        mPaint.setTextSize(20);
        mPaint.getTextBounds(mPrompt, 0, mPrompt.length(), mTextBounds);
        new Thread() {
            @Override
            public void run() {
                super.run();

                while (true) {
                    mProgress++;
                    if (mProgress > 360) {

                        int temp = firstColor;
                        firstColor = secondColor;
                        secondColor = temp;
                        mProgress = 0;
                    }

                    postInvalidate();

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int paddingX = getPaddingLeft() + getPaddingRight();
        int paddingY = getPaddingTop() + getPaddingBottom();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = (Math.min(getWidth() - paddingX, getHeight() - paddingY) - circleWidth) / 2;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(circleWidth);
        mPaint.setColor(firstColor);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        // 绘制文本的坐标原点为(left,bottom),所以是centerY + mTextBounds.height() / 2而不是centerY - mTextBounds.height() / 2
        canvas.drawText(mPrompt, centerX - mTextBounds.width() / 2, centerY + mTextBounds.height() / 2, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(secondColor);
        RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(oval, 0, mProgress, false, mPaint);
    }
}
