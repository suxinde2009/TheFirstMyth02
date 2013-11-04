package com.game.tip;

import java.util.ArrayList;
import java.util.List;

import com.game.base.BaseInfo;
import com.game.base.GameChapterBaseSet_Shentan;
import com.game.base.PubSet;
import com.game.commen.EffectName;
import com.game.commen.GetImgCommen;
import com.game.commen.Paintforziti;
import com.game.data.StroyTipData;
import com.game.effect.SpecialEffect;
import com.game.stroy.MainStroy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class PublicTips {

	public boolean showflag ;
	private String TipUrl = "";
	SpecialEffect dianjixiaoguo;
	private List<StroyTipData> stdlist;
	private int stroyjinduint=0;
	
	public PublicTips(String TipUrl,List<StroyTipData> stdlist,int stroyjinduint) {
		super();
		this.stdlist=stdlist;
		this.TipUrl = TipUrl;
		this.stroyjinduint=stroyjinduint;
		dianjixiaoguo = new SpecialEffect(EffectName.提示_提示点击效果,
				"effects/tip/dianji/0060-6c69364-", 0, 0, 16, true);
	}

	private int x, y;
	String headurl = "spirit/npc/chijiaodaxian/0329-2073f650-00000.png";
	String buttonurl = "publicimg/qdbt.png";
	String text = "";
	String headname = "";
	String storystr = "";
	String todostr = "";
	int sizemax = 28;// 每行有多少字符

	Rect rect_dianji_todo;

	public void PublicTips_settext(String text) {
		this.text = text;
	}

	public void PublicTips_headname(String headname) {
		this.headname = headname;
	}

	public void PublicTips_headurl(String headurl) {
		this.headurl = headurl;
	}

	public List<String> strlist;
	int talkline = 0;// 有几行对话的字

	public void PublicTips_storystr(String storystr) {
		this.storystr = storystr;
		strlist = new ArrayList<String>();
		if ((int) (storystr.length() % sizemax) != 0) {
			talkline = (int) (storystr.length() / sizemax) + 1;
		} else {
			talkline = (int) (storystr.length() / sizemax);
		}
		for (int i = 0; i < talkline; i++) {
			if (i == talkline - 1) {
				strlist.add(storystr.substring(i * sizemax, storystr.length()));
			} else {
				strlist.add(storystr.substring(i * sizemax, (i * sizemax)
						+ sizemax));
			}
		}
	}

	public void PublicTips_todo(String todostr) {
		this.todostr = todostr;
	}

	public void myDraw_Tips(Context context, Canvas canvas, Paint paint, int x,
			int y) {
		this.x = x;
		this.y = y;
		Bitmap bmp = null;
		Bitmap bmp_head = null;
		Bitmap bmp_button = null;
		try {
			bmp = GetImgCommen.readBitMap(context, TipUrl);
			bmp_head = GetImgCommen.readBitMap(context, headurl);
			bmp_button = GetImgCommen.readBitMap(context, buttonurl);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (bmp != null && showflag == true) {
			canvas.drawBitmap(bmp, setbmp_showRect(bmp),
					setbmp_weizhiRect(bmp, 0, 0, 0, 0), paint);
			canvas.drawBitmap(bmp_head, setbmp_showRect(bmp_head),
					setbmp_weizhiRect(bmp_head, -300, -130, -300, -130), paint);
			canvas.drawBitmap(bmp_button, setbmp_showRect(bmp_button),
					setbmp_weizhiRect(bmp_button, 270, 67, 270, 67), paint);
			rect_dianji_todo = new Rect();
			rect_dianji_todo = setbmp_weizhiRect(bmp_button, 270, 67, 270, 67);
			if (talkline > 0) {
				for (int i = talkline - 1; i >= 0; i--) {
					canvas.drawText(strlist.get(i).toString() + "",
							setbmp_weizhiRect(bmp, 0, 0, 0, 0).left + 50,
							setbmp_weizhiRect(bmp, 0, 0, 0, 0).top + 120
									+ (i * 24),
							Paintforziti.SetPaint(24, Color.WHITE, Color.BLACK));
				}
			}

			canvas.drawText(headname + "",
					setbmp_weizhiRect(bmp, 0, 0, 0, 0).left + 60,
					setbmp_weizhiRect(bmp, 0, 0, 0, 0).top + 76,
					Paintforziti.SetPaint(24, Color.YELLOW, Color.BLACK));

			canvas.drawText(todostr + "",
					setbmp_weizhiRect(bmp, 0, 0, 0, 0).right - 165,
					setbmp_weizhiRect(bmp, 0, 0, 0, 0).bottom - 56,
					Paintforziti.SetPaint(24, Color.YELLOW, Color.BLACK));

			dianjixiaoguo.myDraw_Effect(PubSet.context, canvas, paint,
					setbmp_weizhiRect(bmp, 0, 0, 0, 0).right - 185,
					setbmp_weizhiRect(bmp, 0, 0, 0, 0).bottom - 66);

			bmp.recycle();

			bmp = null;
		}

	}

	public Rect setbmp_showRect(Bitmap bmp) {
		Rect bmpRect = new Rect();
		int left = 0;
		int top = 0;
		int right = bmp.getWidth();
		int bottom = bmp.getHeight();
		bmpRect.set(left, top, right, bottom);
		return bmpRect;
	}

	public Rect setbmp_weizhiRect(Bitmap bmp, int left, int top, int right,
			int bottom) {
		// 处理设定显示坐标
		Rect dst = new Rect();
		// 让人物显示居中点
		dst.left = (int) x - bmp.getWidth() / 2 + left;
		dst.top = (int) y - bmp.getHeight() / 2 + top;
		dst.right = (int) x + bmp.getWidth() / 2 + right;
		dst.bottom = (int) y + bmp.getHeight() / 2 + bottom;
		return dst;
	}

	public void StroyPlayIng(int flagint,List<StroyTipData> std)
	{
		if(std.get(flagint)!=null)
		{
			PublicTips_headname(std.get(flagint).getStroyName());
			PublicTips_headurl(std.get(flagint).getHeadUrl());
			PublicTips_storystr(std.get(flagint).getStroyInfo());
			PublicTips_todo(std.get(flagint).getTodoInfo());
		}
	}
	boolean dianji = false;
	public boolean onTouch(MotionEvent e) {
		boolean selectflag = false;
		int x = (int) e.getX();
		int y = (int) e.getY();

		if(rect_dianji_todo!=null)
		{
			if (e.getAction() == MotionEvent.ACTION_DOWN && dianji ==false) {
				if (rect_dianji_todo != null) {
					if (x > rect_dianji_todo.left && x < rect_dianji_todo.right
							&& y > rect_dianji_todo.top
							&& y < rect_dianji_todo.bottom) {
						selectflag = true;
						
						stroyjinduint++;
						if(stdlist.get(stroyjinduint)!=null)
						{
							if(stdlist.get(stroyjinduint).getTodoInfo().equals("点击继续")==true)
							{
								StroyPlayIng(stroyjinduint,stdlist);	
							}
							if(stdlist.get(stroyjinduint).getTodoInfo().equals("完成对话")==true)
							{
								showflag = false;
							}
						}
						else
						{
							showflag = false;
						}
						
						
					} else {
						selectflag = false;
					}
				}
				dianji = true;
			}
			else if(e.getAction() == MotionEvent.ACTION_UP)
			{
				dianji = false;
			}
		}
		return selectflag;
	}
}