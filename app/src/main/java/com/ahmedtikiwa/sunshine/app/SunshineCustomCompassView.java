package com.ahmedtikiwa.sunshine.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ahmed on 2016/11/20.
 */

public class SunshineCustomCompassView extends View {

    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private float mBearing;
    private Matrix mMatrix;
    private static final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SunshineCustomCompassView(Context context) {
        super(context);
        drawCompassImage(context);
    }

    public SunshineCustomCompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawCompassImage(context);
    }

    public SunshineCustomCompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawCompassImage(context);
    }

    private void drawCompassImage(Context context) {
        mMatrix = new Matrix();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.compass_48);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        if (bitmapWidth > canvasWidth || bitmapHeight > canvasHeight) {
            mBitmap = Bitmap.createScaledBitmap(mBitmap,
                    (int) (bitmapWidth * 0.85), (int) (bitmapHeight * 0.85), true);
        }

        // center
        int bitmapX = mBitmap.getWidth() / 2;
        int bitmapY = mBitmap.getHeight() / 2;
        int parentX = mWidth / 2;
        int parentY = mHeight / 2;
        int centerX = parentX - bitmapX;
        int centerY = parentY - bitmapY;

        // calculate rotation angle
        int rotation = (int) (360 - mBearing);

        // reset matrix
        mMatrix.reset();
        mMatrix.setRotate(rotation, bitmapX, bitmapY);
        // center bitmap on canvas
        mMatrix.postTranslate(centerX, centerY);
        // draw bitmap
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);

    }
}
