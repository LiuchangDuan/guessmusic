package com.example.guessmusic.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

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
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.guessmusic.R;
import com.example.guessmusic.data.Const;
import com.example.guessmusic.model.IWordButtonClickListener;
import com.example.guessmusic.model.Song;
import com.example.guessmusic.model.WordButton;
import com.example.guessmusic.myui.MyGridView;
import com.example.guessmusic.util.Util;

public class MainActivity extends Activity implements IWordButtonClickListener {
	
	//��Ƭ��ض���
	private Animation mPanAnim;
	private LinearInterpolator mPanLin;
	
	private Animation mBarInAnim;
	private LinearInterpolator mBarInLin;
	
	private Animation mBarOutAnim;
	private LinearInterpolator mBarOutLin;
	
	//��Ƭ�ؼ�
	private ImageView mViewPan;
	
	//���˿ؼ�
	private ImageView mViewPanBar;
	
	// Play �����¼�
	private ImageButton mBtnPlayStart;
	
	//��ǰ�����Ƿ���������
	private boolean mIsRunning = false;
	
	//���ֿ�����
	private ArrayList<WordButton> mAllWords;
	
	private ArrayList<WordButton> mBtnSelectWords;
	
	private MyGridView mMyGridView;
	
	//��ѡ�����ֿ�UI����
	private LinearLayout mViewWordsContainer;
	
	//��ǰ�ĸ���
	private Song mCurrentSong;
	
	//��ǰ�ص�����
	private int mCurrentStageIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//��ʼ���ؼ�
		mViewPan = (ImageView) findViewById(R.id.imageView1);
		mViewPanBar = (ImageView) findViewById(R.id.imageView2);
		
		mMyGridView = (MyGridView) findViewById(R.id.gridView);
		
		//ע�����
		mMyGridView.registOnWordButtonClick(this);
		
		mViewWordsContainer = (LinearLayout) findViewById(R.id.word_select_container);
		
		//��ʼ������
		mPanAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
		mPanLin = new LinearInterpolator();
		mPanAnim.setInterpolator(mPanLin);
		mPanAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//���������˳�����
				mViewPanBar.setAnimation(mBarOutAnim);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
		});
		
		mBarInAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_45);
		mBarInLin = new LinearInterpolator();
		//���������󱣳���״̬��Ĭ��Ϊfalse�� ����ͣ��
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
				//���׶����������
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
		
		//��ʼ����Ϸ����
		initCurrentStageData();
		
	}
	
	@Override
	public void onWordButtonClick(WordButton wordButton) {
		Toast.makeText(this, wordButton.mIndex + "", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ����Բ���м�Ĳ��Ű�ť�����ǿ�ʼ��������
	 */
	private void handlePlayButton() {
		if (mViewPanBar != null) {
			if (!mIsRunning) {
				mIsRunning = true;
				//��ʼ���˽��붯��
				mViewPanBar.startAnimation(mBarInAnim);
				//����ť����
				mBtnPlayStart.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	@Override
	public void onPause() {
		mViewPan.clearAnimation();
		
		super.onPause();
	}
	
	private Song loadStageSongInfo(int stageIndex) {
		Song song = new Song();
		
		String[] stage = Const.SONG_INFO[stageIndex];
		song.setSongFileName(stage[Const.INDEX_FILE_NAME]);
		song.setSongName(stage[Const.INDEX_SONG_NAME]);
		
		return song;
	}
	
	//��õ�ǰ�ص�����
	private void initCurrentStageData() {
		
		//��ȡ��ǰ�صĸ�����Ϣ
		mCurrentSong = loadStageSongInfo(++mCurrentStageIndex);
		
		//��ʼ����ѡ������ֿ�
		mBtnSelectWords = initWordSelect();
		
		LayoutParams params = new LayoutParams(140, 140);
		
		for (int i = 0; i < mBtnSelectWords.size(); i++) {
			mViewWordsContainer.addView(mBtnSelectWords.get(i).mViewButton, params);
		}
		
		//�������
		mAllWords = initAllWord();
		
		//��������------MyGridView
		mMyGridView.updateData(mAllWords);
	}
	
	/**
	 * ��ʼ����ѡ���ֿ�
	 * @return
	 */
	private ArrayList<WordButton> initAllWord() {
		
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		//������д�ѡ����
		String[] words = generateWords();
		
		for (int i = 0; i < MyGridView.COUNTS_WORDS; i++) {
			
			WordButton button = new WordButton();
			
			button.mWordString = words[i];
			
			data.add(button);
			
		}
		
		return data;
		
	}
	
	/**
	 * ��ʼ����ѡ�����ֿ�
	 * @return
	 */
	private ArrayList<WordButton> initWordSelect() {
		
		ArrayList<WordButton> data = new ArrayList<WordButton>();
		
		//TODO
		for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
			
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
	
	/**
	 * �������еĴ�ѡ����
	 * @return
	 */
	private String[] generateWords() {
		
		Random random = new Random();
		
		String[] words = new String[MyGridView.COUNTS_WORDS];
		
		//�������
		for (int i = 0; i < mCurrentSong.getNameLength(); i++) {
			words[i] = mCurrentSong.getNameCharacters()[i] + "";
		}
		
		//��ȡ������ֲ���������
		for (int i = mCurrentSong.getNameLength(); i < MyGridView.COUNTS_WORDS; i++) {
			words[i] = getRandomChar() + "";
		}
		
		//��������˳��:���ȴ�����Ԫ�������ѡȡһ��Ԫ�����һ��Ԫ�ؽ��н���
		//Ȼ���ڵڶ���֮��ѡ��һ��Ԫ����ڶ���������ֱ�����һ��Ԫ��
		//�����ܹ�ȷ��ÿ��Ԫ����ÿ��λ�õĸ��ʶ���1/n
		for (int i = MyGridView.COUNTS_WORDS - 1; i >= 0; i--) {
			int index = random.nextInt(i + 1);
			
			String buf = words[index];
			words[index] = words[i];
			words[i] = buf;
		}
		
		return words;
	}
	
	//http://www.cnblogs.com/skyivben/archive/2012/10/20/2732484.html
	//����һ�����ִ� 16 ����ʼ���������ġ���λ�ֽڡ��ķ�Χ�� 0xB0 - 0xF7������λ�ֽڡ��ķ�Χ�� 0xA1 - 0xFE
	/**
	 * �����������
	 * @return
	 */
	private char getRandomChar() {
		String str = "";
		int hightPos;
		int lowPos;
		
		Random random = new Random();
		
		hightPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));
		
		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(hightPos).byteValue());
		b[1] = (Integer.valueOf(lowPos).byteValue());
		
		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return str.charAt(0);
		
	}
	
}
