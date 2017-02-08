/**   
* @Title: CustomImageView.java 
* @Package com.yanguo.customviews.view 
* @Description: TODO
* @author wanglei1@boco.com.cn   
* @date 2017年2月7日 上午8:48:48 
* @version V1.0   
*/ 
package com.yanguo.customviews.view;

import java.lang.ref.SoftReference;

import com.yanguo.customviews.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/** 
* @ClassName: CustomImageView 
* @Description: TODO
* @author wanglei1@boco.com.cn
* @date 2017年2月7日 上午8:48:48 
*  
*/
public class CustomImageView extends View{

	private String mTitleText;
	private int mTitleTextSize;
	private int mTitleTextColor;
//	private SoftReference<Bitmap> mImageSoft;
	private Bitmap mImage;
	private int mImageScaleType;
	private Paint mPaint;
	private Rect mTextBound;
	public static final int SCALETYPE_FILL = 0;
	public static final int SCALETYPE_CENTER = 1;
	
	public CustomImageView(Context context) {
		this(context, null);
	}
	public CustomImageView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}
	
	@SuppressLint("NewApi")
	public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView,defStyleAttr, defStyleRes);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomImageView_titleText:
				mTitleText = a.getString(attr);
				break;
			case R.styleable.CustomImageView_titleTextColor:
				mTitleTextColor = a.getInt(attr, Color.BLACK);
				break;
			case R.styleable.CustomImageView_titleTextSize:
				mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;
			case R.styleable.CustomImageView_image:
				mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, R.drawable.ic_launcher));
				break;
			case R.styleable.CustomImageView_imageScaleType:
				mImageScaleType = a.getInt(attr, SCALETYPE_FILL);
				break;
			}
		}
		a.recycle();
		if(mPaint == null) mPaint = new Paint();
		mTextBound = new Rect();
		mPaint.setTextSize(mTitleTextSize);
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.e("CustomImageView", "onMeasure");
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heigheSize = MeasureSpec.getSize(heightMeasureSpec);
		int width = 0, height = 0;
		switch (widthMode) {
		case MeasureSpec.UNSPECIFIED://兼容HScrollView
		case MeasureSpec.AT_MOST:
			int desireWidthByText = getPaddingLeft() + getPaddingRight() + 
					(mTextBound.width() + 2 * mTextBound.left);
			int desireWidthByImage = mImage.getWidth() + getPaddingLeft() + getPaddingRight();
			width = Math.max(desireWidthByText, desireWidthByImage);
			width = Math.min(width, widthSize);
			break;
		case MeasureSpec.EXACTLY:
			width = widthSize;
			break;
		}
		
		switch (heightMode) {
		case MeasureSpec.UNSPECIFIED://兼容ScrollView
		case MeasureSpec.AT_MOST:
			int textHeight = mTextBound.height() - mTextBound.top / 2;
			int imageHeight = mImage.getHeight();
			height = textHeight + imageHeight + getPaddingBottom() + getPaddingTop();
			Math.min(height, heigheSize);
			break;
		case MeasureSpec.EXACTLY:
			height = heigheSize;
		};
		Log.e("CustomImageView", "height ： " + height);
		setMeasuredDimension(width, height);
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(4);
		mPaint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		canvas.drawBitmap(mImage, (getWidth() - mImage.getWidth()) / 2,
				getPaddingTop() , mPaint);
		mPaint.setTextSize(mTitleTextSize);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(mTitleTextColor);
		canvas.drawText(mTitleText, (getWidth() - mTextBound.width()) / 2 - mTextBound.left , 
				mImage.getHeight() + getPaddingTop() + (getHeight() - mImage.getHeight() - getPaddingTop())/ 2 - mTextBound.top / 2 , mPaint);
	}

}
