/**   
* @Title: CustomTitleView.java 
* @Package com.yanguo.customviews.view 
* @Description: TODO
* @author wanglei1@boco.com.cn   
* @date 2017年2月6日 上午10:57:38 
* @version V1.0   
*/ 
package com.yanguo.customviews.view;

import com.yanguo.customviews.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/** 
* @ClassName: CustomTitleView 
* @Description: TODO
* @author wanglei1@boco.com.cn
* @date 2017年2月6日 上午10:57:38 
*  
*/
public class CustomTitleView extends View{
	
	private String TAG = "CustomTitleView";
	private String mTitleText;
	private int mTitleTextColor;
	private int mTitleTextSize;
	private Paint mPaint;
	private Rect mTitleTextBound;
	public CustomTitleView(Context context) {
		this(context, null);
	}
	
	public CustomTitleView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		
	}
	
	public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}
	
	@SuppressLint("NewApi")
	public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,defStyleAttr, defStyleRes);
		int n  = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (a.getIndex(i)) {
			case R.styleable.CustomTitleView_titleTextColor:
				mTitleTextColor = a.getColor(attr, Color.BLACK);
				break;
			case R.styleable.CustomTitleView_titleTextSize:
				mTitleTextSize = a.getDimensionPixelSize(attr, 
						(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				Log.i("TAG", "TitleTextSize " + mTitleTextSize);
				break;
			case R.styleable.CustomTitleView_titleText:
				mTitleText = a.getString(attr);
				break;
			default:
				break;
			}
		}
		a.recycle();
		
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		mTitleTextBound = new Rect();
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTitleTextBound);
		Log.i(TAG, "text height" + mTitleTextBound.height());
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width = 0, height = 0;
		switch (widthMode) {
		case MeasureSpec.AT_MOST:
			width = getPaddingLeft() + getPaddingRight() + mTitleTextBound.width() + 2 * mTitleTextBound.left;
			widthSize = Math.min(widthSize, width);
			break;
		case MeasureSpec.EXACTLY:
			width = widthSize;
			break;
		}
		
		switch (widthMode) {
		case MeasureSpec.AT_MOST:
			height = getPaddingTop() + getPaddingBottom() + mTitleTextBound.height() -  mTitleTextBound.top /2;
			break;
		case MeasureSpec.EXACTLY:
			height = heightSize;
			break;
		}
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i(TAG, "onDraw");
		mPaint.setColor(Color.YELLOW);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		mPaint.setColor(mTitleTextColor);
		mPaint.setTextSize(mTitleTextSize);
		//字体居中
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		int baseX = getWidth() / 2 - mTitleTextBound.width() / 2 - mTitleTextBound.left;
		int baseY = (int) (getHeight() / 2 + mTitleTextBound.height() / 2);
		Log.i(TAG, "baseY : " + baseY);
		canvas.drawText(mTitleText, baseX, baseY, mPaint);
	}
}
