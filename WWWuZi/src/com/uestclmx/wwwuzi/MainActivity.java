package com.uestclmx.wwwuzi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String MODE = "mode", SINGLEGAME = "single",
			PLAYERGAME = "player";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 单人模式点击事件
	 * 
	 * @param v
	 */
	public void singlegame(View v) {
		Intent intent = new Intent(MainActivity.this, GameActivity.class);
		intent.putExtra(MODE, SINGLEGAME);
		this.startActivity(intent);
		overridePendingTransition(R.anim.anim_show, R.anim.anim_hide);
	}

	/**
	 * 双人对战点击事件
	 * 
	 * @param v
	 */
	public void playergame(View v) {
		Intent intent = new Intent(MainActivity.this, GameActivity.class);
		intent.putExtra(MODE, PLAYERGAME);
		this.startActivity(intent);
		overridePendingTransition(R.anim.anim_show, R.anim.anim_hide);
	}

	/**
	 * 设置点击事件
	 * 
	 * @param v
	 */
	public void configues(View v) {
		// Intent intent = new Intent(MainActivity.this, GameActivity.class);
		// this.startActivity(intent);
		Toast toast = Toast.makeText(this, "功能稍后完成哦~", Toast.LENGTH_SHORT);
		toast.show();
	}

	/**
	 * 退出游戏点击事件
	 * 
	 * @param v
	 */
	public void exitgame(View v) {
		this.onBackPressed();
	}
}
