package com.example.guessmusic.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.guessmusic.R;

/**
 * ͨ�ؽ���
 * 
 * @author DuanLiuchang
 *
 */
public class AllPassView extends Activity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.all_pass_view);
		
		//�������ϽǵĽ��
		FrameLayout view = (FrameLayout) findViewById(R.id.layout_bar_coin);
		view.setVisibility(View.INVISIBLE);
	}
	
}
