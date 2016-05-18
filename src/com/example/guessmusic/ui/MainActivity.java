package com.example.guessmusic.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.guessmusic.R;

public class MainActivity extends Activity {
	
	//唱片相关动画
	private Animation mPanAnim;
	private LinearInterpolator mPanLin;
	
	private Animation mBarInAnim;
	private LinearInterpolator mBarInLin;
	
	private Animation mBarOutAnim;
	private LinearInterpolator mBarOutLin;
	
	private ImageView mViewPan;
	
	private ImageView mViewPanBar;
	
	// Play 按键事件
	private ImageButton mBtnPlayStart;
	
	//当前动画是否正在运行
	private boolean mIsRunning = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化控件
		mViewPan = (ImageView) findViewById(R.id.imageView1);
		mViewPanBar = (ImageView) findViewById(R.id.imageView2);
		
		//初始化动画
		mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
		mPanLin = new LinearInterpolator();
		mPanAnim.setInterpolator(mPanLin);
		mPanAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//开启拨杆退出动画
				mViewPanBar.setAnimation(mBarOutAnim);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
		});
		
		mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
		mBarInLin = new LinearInterpolator();
		//动画结束后保持其状态（默认为false） 动画停留
		mBarInAnim.setFillAfter(true);
		mBarInAnim.setInterpolator(mBarInLin);
		mBarInAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mViewPan.startAnimation(mPanAnim);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
		});
		
		mBarOutAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_d_45);
		mBarOutLin = new LinearInterpolator();
		mBarOutAnim.setFillAfter(true);
		mBarOutAnim.setInterpolator(mBarOutLin);
		mBarOutAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//整套动画播放完毕
				mIsRunning = false;
				mBtnPlayStart.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
		});
		
		mBtnPlayStart = (ImageButton) findViewById(R.id.btn_play_start);
		mBtnPlayStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handlePlayButton();
			}
		});
	}
	
	/**
	 * 处理圆盘中间的播放按钮，就是开始播放音乐
	 */
	private void handlePlayButton() {
		if (mViewPanBar != null) {
			if (!mIsRunning) {
				mIsRunning = true;
				//开始拨杆进入动画
				mViewPanBar.startAnimation(mBarInAnim);
				//将按钮隐藏
				mBtnPlayStart.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override
	public void onPause() {
		mViewPan.clearAnimation();
		
		super.onPause();
	}
	
}
