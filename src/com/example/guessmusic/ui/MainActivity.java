package com.example.guessmusic.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.example.guessmusic.R;
import com.example.guessmusic.model.IWordButtonClickListener;
import com.example.guessmusic.model.WordButton;
import com.example.guessmusic.myui.MyGridView;
import com.example.guessmusic.util.Util;

public class MainActivity extends Activity implements IWordButtonClickListener {
	
	//唱片相关动画
	private Animation mPanAnim;
	private LinearInterpolator mPanLin;
	
	private Animation mBarInAnim;
	private LinearInterpolator mBarInLin;
	
	private Animation mBarOutAnim;
	private LinearInterpolator mBarOutLin;
	
	//唱片控件
	private ImageView mViewPan;
	
	//拨杆控件
	private ImageView mViewPanBar;
	
	// Play 按键事件
	private ImageButton mBtnPlayStart;
	
	//当前动画是否正在运行
	private boolean mIsRunning = false;
	
	//文字框容器
	private ArrayList<WordButton> mAllWords;
	
	private ArrayList<WordButton> mBtnSelectWords;
	
	private MyGridView mMyGridView;
	
	//已选择文字框UI容器
	private LinearLayout mViewWordsContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//初始化控件
		mViewPan = (ImageView) findViewById(R.id.imageView1);
		mViewPanBar = (ImageView) findViewById(R.id.imageView2);
		
		mMyGridView = (MyGridView) findViewById(R.id.gridView);
		
		//注册监听
		mMyGridView.registOnWordButtonClick(this);
		
		mViewWordsContainer = (LinearLayout) findViewById(R.id.word_select_container);
		
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
		
		//初始化游戏数据
		initCurrentStageData();
		
	}
	
	@Override
	public void onWordButtonClick(WordButton wordButton) {
		Toast.makeText(this, wordButton.mIndex + "", Toast.LENGTH_SHORT).show();
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
	
	//获得当前关的数据
	private void initCurrentStageData() {
		
		//初始化已选择的文字框
		mBtnSelectWords = initWordSelect();
		
		LayoutParams params = new LayoutParams(140, 140);
		
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton, params);
		}
		
		//获得数据
		mAllWords = initAllWord();
		
		//更新数据------MyGridView
		mMyGridView.updateData(mAllWords);
	}
	
	/**
	 * 初始化待选文字框
	 * @return
	 */
	private ArrayList<WordButton> initAllWord() {
		
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		//获得所有待选文字
		// TODO
		
		
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			
			WordButton button = new WordButton();
			
			//测试数据
			button.mWordString = "好";
			
			data.add(button);
			
		}
		
		return data;
		
	}
	
	/**
	 * 初始化已选择文字框
	 * @return
	 */
	private ArrayList<WordButton> initWordSelect() {
		
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		for (int i = 0; i < 4; i++) {
			
			View view = Util.getView(MainActivity.this, R.layout.self_ui_gridview_item);
			
			WordButton holder = new WordButton();
			
			holder.mViewButton = (Button) view.findViewById(R.id.item_btn);
			holder.mViewButton.setTextColor(Color.WHITE);
			holder.mViewButton.setText("");
			holder.mIsVisible = false;
			
			holder.mViewButton.setBackgroundResource(R.drawable.game_wordblank);
			
			data.add(holder);
			
		}
		
		return data;
		
	}
	
}
