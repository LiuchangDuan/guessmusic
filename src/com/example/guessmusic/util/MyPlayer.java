package com.example.guessmusic.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

/**
 * 音乐播放类
 * 
 * @author DuanLiuchang
 *
 */
public class MyPlayer {
	
	//索引
	public final static int INDEX_STONE_ENTER = 0;
	public final static int INDEX_STONE_CANCEL = 1;
	public final static int INDEX_STONE_COIN = 2;
	
	//音效的文件名
	private final static String[] SONG_NAMES = 
		{"enter.mp3", "cancel.mp3", "coin.mp3"};
	
	//音效
	private static MediaPlayer[] mToneMediaPlayer = 
			new MediaPlayer[SONG_NAMES.length];

	//歌曲播放
	private static MediaPlayer mMusicMediaPlayer;
	
	//播放音效、按钮点击的声音，无需反复加载
	public static void playTone(Context context, int toneIndex) {
		//加载声音
		AssetManager assetManager = context.getAssets();
		
		if (mToneMediaPlayer[toneIndex] == null) {
			mToneMediaPlayer[toneIndex] = new MediaPlayer();
			
			try {
				//只需调用一次就可以了
				AssetFileDescriptor fileDescriptor = 
						assetManager.openFd(SONG_NAMES[toneIndex]);
				mToneMediaPlayer[toneIndex].setDataSource(fileDescriptor.getFileDescriptor(), 
						fileDescriptor.getStartOffset(), fileDescriptor.getLength());
				mToneMediaPlayer[toneIndex].prepare();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//声音播放
		mToneMediaPlayer[toneIndex].start();
		
	}
	
	/**
	 * 播放歌曲
	 * 
	 * @param context
	 * @param fileName
	 */
	public static void playSong(Context context, String fileName) {
		if (mMusicMediaPlayer == null) {
			mMusicMediaPlayer = new MediaPlayer();
		}
		
		//强制重置（非第一次加载）
		mMusicMediaPlayer.reset();
		
		//加载声音文件
		AssetManager assetManager = context.getAssets();
		try {
			AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
			mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), 
					fileDescriptor.getStartOffset(), fileDescriptor.getLength());
			
			mMusicMediaPlayer.prepare();
			
			//声音播放
			mMusicMediaPlayer.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void stopTheSong(Context context) {
		if (mMusicMediaPlayer != null) {
			mMusicMediaPlayer.stop();
		}
	}
	
}
