/**   
* @Title: CircleProgressView.java 
* @Package com.yanguo.customviews.view 
* @Description: TODO
* @author wanglei1@boco.com.cn   
* @date 2017年2月7日 下午6:54:39 
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
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/** 
* @ClassName: CircleProgressView 
* @Description: TODO
* @author wanglei1@boco.com.cn
* @date 2017年2月7日 下午6:54:39 
*  
*/
public class CircleProgressView extends View{

	private int mCircleWidth;
	private int mCircleDuration;
	private int mFirstColor;
	private int mSecondColor;
	private int mProgressCount;
	private int mProgressRadius;
	private Paint mPaint;
	public CircleProgressView(Context context) {
		this(context, null);
	}
	
	public CircleProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr,0);
	}
	
	@SuppressLint("NewApi")
	public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		mFirstColor = Color.RED;
		mSecondColor = Color.YELLOW;
		mCircleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				5, getResources().getDisplayMetrics());
		mProgressRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				25, getResources().getDisplayMetrics());
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressView,defStyleAttr, defStyleRes);
		int n =  a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CircleProgressView_circle_duration:
				mCircleDuration = a.getInt(attr, 200);
				break;
			case R.styleable.CircleProgressView_circle_width:
				mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						5, getResources().getDisplayMetrics()));
				break;
			case R.styleable.CircleProgressView_firstColor:
				mFirstColor = a.getColor(attr, Color.RED);
				break;
			case R.styleable.CircleProgressView_secondColor:
				
				mSecondColor = a.getColor(attr, Color.YELLOW);
				break;
			case R.styleable.CircleProgressView_progress_count:
				mProgressCount = a.getInteger(attr, 100);
				break;
			case R.styleable.CircleProgressView_progress_radius:
				mProgressRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						25, getResources().getDisplayMetrics()));
			default:
				break;
			}
		}
		
		a.recycle();
		mPaint = new Paint();
		new Thread(){
			public void run() {
				while(true){
					progress ++;
					if(progress == 360){
						progress = 0;
						round ++ ;
					}
					postInvalidate();
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
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
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			width = mProgressRadius * 2 + getPaddingLeft() + getPaddingRight();
			break;
		case MeasureSpec.EXACTLY:
			width = widthSize;
		default:
			break;
		}
		
		switch (heightMode) {
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			height = mProgressRadius * 2 + getPaddingTop() + getPaddingBottom();
			break;
		case MeasureSpec.EXACTLY:
			height = heightSize;
		default:
			break;
		}
		
		setMeasuredDimension(width, height);
	}
	
	private int round = 1;
	
	private int progress;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mCircleWidth);
		mPaint.setAntiAlias(true);
		int radius = mProgressRadius;;
		int centerX = getPaddingLeft() + (getWidth()- getPaddingLeft() - getPaddingRight()) / 2 ;
		int centerY = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom()) /2;
		if(round % 2 == 1){
			mPaint.setColor(mFirstColor);
			canvas.drawCircle(centerX, centerY,  radius, mPaint);
			mPaint.setColor(mSecondColor);
			RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
			canvas.drawArc(oval, -90, progress, false, mPaint);
		}else{
			mPaint.setColor(mSecondColor);
			canvas.drawCircle(centerX, centerY,  radius, mPaint);
			mPaint.setColor(mFirstColor);
			RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
			canvas.drawArc(oval, -90, progress, false, mPaint);
		}
	}

	
}
