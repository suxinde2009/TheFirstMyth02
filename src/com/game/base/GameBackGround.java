package com.game.base;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameBackGround {
	private Bitmap bmpGBG;// 背景

	public GameBackGround(Bitmap bmpGBG) {
		super();
		this.bmpGBG = Bitmap.createScaledBitmap(bmpGBG, PubSet.screenWidth, PubSet.screenHeight,
				false);
	}
	// 绘制背景
	public void myDraw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bmpGBG, 0, 0, paint);
	};
	
	
	
	
}