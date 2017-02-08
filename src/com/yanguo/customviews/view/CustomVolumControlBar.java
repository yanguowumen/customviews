/**   
* @Title: CustomVolumControlBar.java 
* @Package com.yanguo.customviews.view 
* @Description: TODO
* @author wanglei1@boco.com.cn   
* @date 2017年2月8日 下午3:05:08 
* @version V1.0   
*/
package com.yanguo.customviews.view;

import com.yanguo.customviews.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * @ClassName: CustomVolumControlBar
 * @Description: TODO
 * @author wanglei1@boco.com.cn
 * @date 2017年2月8日 下午3:05:08
 * 
 */
public class CustomVolumControlBar extends View {

	private static final String TAG = "CustomVolumControlBar";
	private int mDoutCount;
	private int mSplitSize;
	private int mCircleWith;
	private Bitmap mBg;
	private int mFirstColor;
	private int mSecondColor;
	private Paint mPaint;
	
	
	private int imageWidth, imageHeight;
	public CustomVolumControlBar(Context context) {
		this(context, null);
	}

	public CustomVolumControlBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomVolumControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	@SuppressLint("NewApi")
	public CustomVolumControlBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		//设置一个默认的circlewidth
		mCircleWith = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
		mFirstColor = Color.RED;
		mSecondColor = Color.BLACK;
		mDoutCount = 10;
		mSplitSize = 20;
		imageWidth = imageHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar, defStyleAttr,
				defStyleRes);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomVolumControlBar_dotcount:
				mDoutCount  = a.getInt(attr, 10);
				break;
			case R.styleable.CustomVolumControlBar_circle_width:
				mCircleWith  = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
				break;
			case R.styleable.CustomVolumControlBar_firstColor:
				mFirstColor = a.getColor(attr, Color.WHITE);
				break;
			case R.styleable.CustomVolumControlBar_secondColor:
				mSecondColor = a.getColor(attr, Color.BLACK);
				break;
			case R.styleable.CustomVolumControlBar_image:
				mBg = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, R.drawable.ic_launcher));
				break;
			}
		}
		a.recycle();
		mPaint = new Paint();
		if(mBg != null){
			imageWidth = mBg.getWidth();
			imageHeight = mBg.getHeight();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		int heightSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		
		int width = 0; 
		int height = 0;
		
		switch (widthMode) {
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			width = 2 * mCircleWith + (int)(Math.max(imageWidth, imageHeight) * 2 / Math.sqrt(2)) + getPaddingLeft() + getPaddingRight();
			break;
		case MeasureSpec.EXACTLY:
			width = widthSize;
			break;
		default:
			break;
		}
		
		switch (heightMode) {
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			height = 2 * mCircleWith + (int)(Math.max(imageWidth, imageHeight) * 2 / Math.sqrt(2)) + getPaddingLeft() + getPaddingRight();
			break;
		case MeasureSpec.EXACTLY:
			height = heightSize;
			break;
		default:
			break;
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		drawOval(canvas);
	}
	
	private void drawOval(Canvas canvas){
		mPaint.setAntiAlias(true);
		mPaint.setColor(mFirstColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(mCircleWith);
		mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头  
		int center = (getWidth() - getPaddingLeft() + getPaddingRight()) / 2;
		int radius = center - mCircleWith / 2;
		RectF oval = new RectF(mCircleWith / 2, mCircleWith / 2, center +  radius, center + radius);
		float totalItemSize = 270.0f /mDoutCount;
		
		float itemSize = totalItemSize - mSplitSize;
		
		for (int i = 0; i < mDoutCount; i++) {
			float startAngle = 135 + totalItemSize * i;
			float sweepAngle = itemSize;
			canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
		}
		
	}

}
