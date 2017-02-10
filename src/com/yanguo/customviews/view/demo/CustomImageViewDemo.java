/**   
* @Title: CustomImageViewDemo.java 
* @Package com.yanguo.customviews.view.demo 
* @Description: TODO
* @author wanglei1@boco.com.cn   
* @date 2017年2月7日 上午11:09:33 
* @version V1.0   
*/ 
package com.yanguo.customviews.view.demo;

import com.yanguo.customviews.R;
import com.yanguo.customviews.view.CustomVolumControlBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ScrollView;
import butterknife.Bind;
import butterknife.OnTouch;

/** 
* @ClassName: CustomImageViewDemo 
* @Description: TODO
* @author wanglei1@boco.com.cn
* @date 2017年2月7日 上午11:09:33 
*  
*/
public class CustomImageViewDemo extends Activity {

	@Bind(R.id.volumeControlBar)
	CustomVolumControlBar volumeControlBar;
	@Bind(R.id.scrollView)
	ScrollView scrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customimageview_demo);
	}
	
	
	@OnTouch(R.id.volumeControlBar)
	boolean onTouch(MotionEvent e){
		if(MotionEvent.ACTION_DOWN == e.getAction()){
			scrollView.requestDisallowInterceptTouchEvent(false);
		}else if(MotionEvent.ACTION_UP == e.getAction()){
			scrollView.requestDisallowInterceptTouchEvent(true);
		}
		return true;
	}
	
}
