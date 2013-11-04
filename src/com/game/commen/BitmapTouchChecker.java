package com.game.commen;

import com.game.base.PubSet;
import com.game.commen.IrregularButton.TouchChecker;
import com.game.fengshen.GameMainActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

public class BitmapTouchChecker implements TouchChecker  
{  
    private Bitmap bitmap;  
    private Context context;
    public BitmapTouchChecker(Context context,Bitmap bitmap)  
    {  
        this.bitmap = bitmap;  
        this.context=context;
    }  
 // 初始地图坐标
 	public static int F_x = 0;
 	public static int F_y = 0;
 // 绘制背景
 	public void Draw(Canvas canvas, Paint paint, int spirit_x,
 			int spirit_y) {
 	// 地图总长宽
 		int mapwidth = bitmap.getWidth();
 			int mapheight = bitmap.getHeight();

 			// 屏幕长宽
 		int screenWidth = PubSet.screenWidth;
 		int screenHeight = PubSet.screenHeight;
 			
 		Rect map_rect = new Rect();
		map_rect.left = F_x;
		map_rect.top = F_y;
		map_rect.right = F_x + screenWidth;
		map_rect.bottom = F_y + screenHeight;

		// 屏幕保持固定尺寸
		Rect screen_rect = new Rect();
		screen_rect.left = 0;
		screen_rect.top = 0;
		screen_rect.right = screenWidth;
		screen_rect.bottom = screenHeight;
		
 		canvas.drawBitmap(bitmap, map_rect, screen_rect, paint);

 	}
    
    @Override  
    public boolean isInTouchArea(int x, int y, int width, int height)  
    {  
        if (bitmap != null)  
        {  
            int pixel = bitmap.getPixel(x, y);  
              
            if (((pixel >> 24) & 0xff) > 0)  
            {  
            	tip("不透的点击到了");  
                return true;  
            }  
        }     
          
        tip("其他区域不可点击");  
        return false;  
    }  
  
    public void tip(String str)
	{
		Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
	}
}  