package com.yunkang.prenatalgt.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * @作者： [欢欢 email：252339447@qq.com]<br>
 * @版本： [V1.0.0, 2016-1-6]<br>
 * @描述：聊天相关控件<br>
 */
public class BubbleDrawable extends Drawable {
    private RectF mRect;
    private Path mPath = new Path();
    private BitmapShader mBitmapShader;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mArrowWidth;
	private float mArrowTop ;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPosition;
	private float mArrowOffset ;
    private int bubbleColor;
    private Bitmap bubbleBitmap;
    private ArrowLocation mArrowLocation;
    private BubbleType bubbleType;
    private BubbleDrawable(Builder builder) {
        this.mRect = builder.mRect;
        this.mAngle = builder.mAngle;
        this.mArrowTop = builder.mArrowTop;
        this.mArrowOffset = builder.mArrowOffset;
        this.mArrowHeight = builder.mArrowHeight;
        this.mArrowWidth = builder.mArrowWidth;
        this.mArrowPosition = builder.mArrowPosition;
        this.bubbleColor = builder.bubbleColor;
        this.bubbleBitmap = builder.bubbleBitmap;
        this.mArrowLocation = builder.mArrowLocation;
        this.bubbleType = builder.bubbleType;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void draw(Canvas canvas) {
        setUp(canvas);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }
 
    private void setUpPath(ArrowLocation mArrowLocation, Path path){
        switch (mArrowLocation){
            case LEFT:
                setUpLeftPath(mRect, path);
                break;
            case RIGHT:
                setUpRightPath(mRect, path);
                break;
            case TOP:
                setUpTopPath(mRect, path);
                break;
            case BOTTOM:
                setUpBottomPath(mRect, path);
                break;
        }
    }

    private void setUp(Canvas canvas){
        switch (bubbleType){
            case COLOR:
                mPaint.setColor(bubbleColor);
                break;
            case BITMAP:
                if (bubbleBitmap == null)
                    return;
                if (mBitmapShader == null){
                    mBitmapShader = new BitmapShader(bubbleBitmap,
                            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                }
                mPaint.setShader(mBitmapShader);
                setUpShaderMatrix();
                break;
        }
        setUpPath(mArrowLocation, mPath);
        canvas.drawPath(mPath, mPaint);
    }

    private void setUpLeftPath(RectF rect, Path path){

    	path.moveTo(mAngle + mArrowWidth, rect.top);
		path.lineTo(rect.width(), rect.top);
		path.arcTo(new RectF(rect.right - mAngle * 2, rect.top, rect.right, mAngle * 2 + rect.top), 270, 90);
		path.lineTo(rect.right, rect.top);
		path.arcTo(new RectF(rect.right - mAngle * 2, rect.bottom - mAngle * 2, rect.right, rect.bottom), 0, 90);
		path.lineTo(rect.left + mArrowWidth, rect.bottom);
		path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mAngle * 2, mAngle * 2 + rect.left + mArrowWidth, rect.bottom), 90, 90);
		path.lineTo(rect.left + mArrowWidth, mArrowTop + mArrowHeight);
		path.lineTo(rect.left, mArrowTop - mArrowOffset);
		path.lineTo(rect.left + mArrowWidth, mArrowTop);
		path.lineTo(rect.left + mArrowWidth, rect.top);
		path.arcTo(new RectF(rect.left + mArrowWidth, rect.top, mAngle * 2 + rect.left + mArrowWidth, mAngle * 2 + rect.top), 180, 90);
		path.close();
    }

    private void setUpTopPath(RectF rect, Path path){
        path.moveTo(rect.left + Math.min(mArrowPosition, mAngle), rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowPosition,  rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowWidth / 2 + mArrowPosition, rect.top);
        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.top + mArrowHeight);
        path.lineTo(rect.right - mAngle, rect.top + mArrowHeight);

        path.arcTo(new RectF(rect.right - mAngle,
                rect.top + mArrowHeight , rect.right, mAngle + rect.top + mArrowHeight), 270, 90);
        path.lineTo(rect.right, rect.bottom - mAngle);

        path.arcTo(new RectF(rect.right - mAngle , rect.bottom - mAngle,
                rect.right , rect.bottom), 0, 90);
        path.lineTo(rect.left + mAngle, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle,
                mAngle + rect.left , rect.bottom), 90, 90);
        path.lineTo(rect.left , rect.top + mArrowHeight + mAngle);
        path.arcTo(new RectF(rect.left, rect.top + mArrowHeight , mAngle
                + rect.left, mAngle + rect.top + mArrowHeight), 180, 90);
        path.close();
    }

    private void setUpRightPath(RectF rect, Path path){

    	path.moveTo(mAngle, rect.top);
		path.lineTo(rect.width(), rect.top);
		path.arcTo(new RectF(rect.right - mAngle * 2 - mArrowWidth, rect.top, rect.right - mArrowWidth, mAngle * 2 + rect.top), 270, 90);
		path.lineTo(rect.right - mArrowWidth, mArrowTop);
		path.lineTo(rect.right, mArrowTop - mArrowOffset);
		path.lineTo(rect.right - mArrowWidth, mArrowTop + mArrowHeight);
		path.lineTo(rect.right - mArrowWidth, rect.height() - mAngle);
		path.arcTo(new RectF(rect.right - mAngle * 2 - mArrowWidth, rect.bottom - mAngle * 2, rect.right - mArrowWidth, rect.bottom), 0, 90);
		path.lineTo(rect.left, rect.bottom);
		path.arcTo(new RectF(rect.left, rect.bottom - mAngle * 2, mAngle * 2 + rect.left, rect.bottom), 90, 90);
		path.lineTo(rect.left, rect.top);
		path.arcTo(new RectF(rect.left, rect.top, mAngle * 2 + rect.left, mAngle * 2 + rect.top), 180, 90);
		path.close();
    }

    private void setUpBottomPath(RectF rect, Path path){

        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle, rect.top);
        path.arcTo(new RectF(rect.right - mAngle,
                rect.top, rect.right, mAngle + rect.top), 270, 90);

        path.lineTo(rect.right, rect.bottom - mArrowHeight - mAngle);
        path.arcTo(new RectF(rect.right - mAngle, rect.bottom - mAngle - mArrowHeight,
                rect.right, rect.bottom - mArrowHeight), 0, 90);

        path.lineTo(rect.left + mArrowWidth + mArrowPosition,  rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + Math.min(mAngle, mArrowPosition), rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle - mArrowHeight,
                mAngle + rect.left , rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mAngle);
        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
    }

    private void setUpShaderMatrix() {
        float scale;
        Matrix mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);
        int mBitmapWidth = bubbleBitmap.getWidth();
        int mBitmapHeight = bubbleBitmap.getHeight();
        float scaleX = getIntrinsicWidth() / (float) mBitmapWidth;
        float scaleY = getIntrinsicHeight() / (float) mBitmapHeight;
        scale = Math.min(scaleX, scaleY);
        mShaderMatrix.postScale(scale, scale);
        mShaderMatrix.postTranslate(mRect.left, mRect.top);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int)mRect.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int)mRect.height();
    }

    public static class Builder{
        public static float DEFAULT_ARROW_WITH = 25;
        public static float DEFAULT_ARROW_HEIGHT = 25;
        public static float DEFAULT_ARROW_TOP = 40;
        public static float DEFAULT_ANGLE = 20;
        public static float DEFAULT_ARROW_POSITION = 50;
        public static int DEFAULT_BUBBLE_COLOR = Color.RED;
        private RectF mRect;
        private float mArrowWidth = DEFAULT_ARROW_WITH;
        private float mAngle = DEFAULT_ANGLE;
        private float mArrowTop = DEFAULT_ARROW_TOP;
        private float mArrowOffset = 0;
        private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
        private float mArrowPosition = DEFAULT_ARROW_POSITION;
        private int bubbleColor = DEFAULT_BUBBLE_COLOR;
        private Bitmap bubbleBitmap;
        private BubbleType bubbleType = BubbleType.COLOR;
        private ArrowLocation mArrowLocation = ArrowLocation.LEFT;

        public Builder rect(RectF rect){
            this.mRect = rect;
            return this;
        }

        public Builder arrowWidth(float mArrowWidth){
            this.mArrowWidth = mArrowWidth;
            return this;
        }
        public Builder arrowTop(float mArrowTop){
        	this.mArrowTop = mArrowTop;
        	return this;
        }
        public Builder arrowOffset(float mArrowOffset){
        	this.mArrowOffset = mArrowOffset;
        	return this;
        }

        public Builder angle(float mAngle){
            this.mAngle = mAngle * 2;
            return this;
        }

        public Builder arrowHeight(float mArrowHeight){
            this.mArrowHeight = mArrowHeight;
            return this;
        }

        public Builder arrowPosition(float mArrowPosition){
            this.mArrowPosition = mArrowPosition;
            return this;
        }

        public Builder bubbleColor(int bubbleColor){
            this.bubbleColor = bubbleColor;
            bubbleType(BubbleType.COLOR);
            return this;
        }

        public Builder bubbleBitmap(Bitmap bubbleBitmap){
            this.bubbleBitmap = bubbleBitmap;
            bubbleType(BubbleType.BITMAP);
            return this;
        }

        public Builder arrowLocation(ArrowLocation arrowLocation){
            this.mArrowLocation = arrowLocation;
            return this;
        }

        public Builder bubbleType(BubbleType bubbleType){
            this.bubbleType = bubbleType;
            return this;
        }

        public BubbleDrawable build(){
            if (mRect == null){
                throw new IllegalArgumentException("BubbleDrawable Rect can not be null");
            }
            return new BubbleDrawable(this);
        }
    }

    public enum ArrowLocation{
        LEFT(0x00),
        RIGHT(0x01),
        TOP(0x02),
        BOTTOM(0x03);

        private int mValue;

        ArrowLocation(int value){
            this.mValue = value;
        }

        public static ArrowLocation mapIntToValue(int stateInt) {
            for (ArrowLocation value : ArrowLocation.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowLocation getDefault(){
            return LEFT;
        }

        public int getIntValue() {
            return mValue;
        }
    }

    public enum BubbleType{
        COLOR,
        BITMAP
    }
}
