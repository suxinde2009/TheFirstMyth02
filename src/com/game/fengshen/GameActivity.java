package com.game.fengshen;


import com.example.fengshen.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

	private GameView msf;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 强制横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		msf = new GameView(this);
		setContentView(msf);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)

				.setTitle("温馨提示")
				.setIcon(R.drawable.icon)

				.setMessage("确定结束游戏？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					// 关闭
					public void onClick(DialogInterface dialoginterface, int i) {
						android.os.Process.killProcess(android.os.Process
								.myPid());

						// SoundManager.Instance.stopBackgroundSound();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					/* 设置跳出窗口的返回 */
					public void onClick(DialogInterface dialoginterface, int i) {
						// 不进行操
					}
				}).show();

	}

	public void onPause() {
		super.onPause();
	}
}
