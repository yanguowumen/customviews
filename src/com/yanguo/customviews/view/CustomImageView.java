/**   
* @Title: CustomImageView.java 
* @Package com.yanguo.customviews.view 
* @Description: TODO
* @author wanglei1@boco.com.cn   
* @date 2017年2月7日 上午8:48:48 
* @version V1.0   
*/ 
package com.yanguo.customviews.view;

import com.yanguo.customviews.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

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
	private Bitmap mImage;
	private int mImageScaleType;
	
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
				mTitleTextSize = a.getInt(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;
			case R.styleable.CustomImageView_image:
				mImage = BitmapFactory.decodeResource(getResources(), a.getInt(attr, R.drawable.ic_launcher));
				break;
			case R.styleable.CustomImageView_imageScaleType:
				mImageScaleType = a.getInt(attr, SCALETYPE_FILL);
				break;
			}
		}
	}

}
