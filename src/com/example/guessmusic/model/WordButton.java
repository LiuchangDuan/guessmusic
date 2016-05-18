package com.example.guessmusic.model;

import android.widget.Button;

/**
 * ÎÄ×Ö°´Å¥
 * @author LiuchangDuan
 *
 */
public class WordButton {

	public int mIndex;
	public boolean mIsVisible;
	public String mWordString;
	
	public Button mViewButton;
	
	public WordButton() {
		mIsVisible = true;
		mWordString = "";
	}
	
}
