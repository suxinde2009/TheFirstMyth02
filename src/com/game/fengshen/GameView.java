package com.game.fengshen;

import java.util.ArrayList;
import java.util.List;

import com.example.fengshen.R;
import com.game.base.BaseInfo;
import com.game.base.GameBackGround;
import com.game.base.GameChapterBaseSet_Shentan;
import com.game.base.GameMap;
import com.game.base.PubSet;
import com.game.commen.ActionToDo;
import com.game.commen.BitmapTouchChecker;
import com.game.commen.Direction;
import com.game.commen.EffectName;
import com.game.commen.GetImgCommen;
import com.game.commen.MapName;
import com.game.commen.ToDo;
import com.game.data.RoleData;
import com.game.data.RoleData_Main;
import com.game.effect.SpecialEffect;
import com.game.renwu.Objs;
import com.game.renwu.Spirit;
import com.game.renwu.SpiritMain;
import com.game.renwu.Spirit_Main;
import com.game.renwu.Spirit_NPC;
import com.game.renwu.Spiritgirl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class GameView extends SurfaceView implements Callback, Runnable {
	
	public static MotionEvent event;
	public static SurfaceHolder holder;
	public static Canvas canvas;
	public static boolean draw_flag;// 绘画线程
	private Thread th;
	private static List<Spirit> npclist;

	private int todotime = 0;
	
	// 男猪脚小华
	public static RoleData_Main role_Main;

	private Context mContext;
	private Boolean over = false;
	private int of = 0;
	private float mX, mY;
	private Path mPath;
	private static final float TOUCH_TOLERANCE = 4;
	private Paint lPaint;
	private Paint noPaint;

	public static int paodongflag = 0; // 0,默认模式 1，手势模式 2，点击模式 3，虚拟键盘模式
	public static MapName mapflag = MapName.神界_天坛;// 场景控制 1，方寸山 2，女妖怪洞府 3，牛魔王洞府
	// 猪脚的X,Y坐标
	public static float zhujiao_x = 10;
	public static float zhujiao_y = 10;
	public static float zhujiao_x_new = 10;
	public static float zhujiao_y_new = 10;

	// 默认刷新多少帧的时间
	private int paotime = 20;

	// 猪脚默认方向
	Direction dc = Direction.左;
	// 猪脚默认是否跑步状态
	public static ActionToDo paodong = ActionToDo.站立;
	// 猪脚跑步速度
	private int paospeed = 20;// 数值越大猪脚跑得越快
	// NPC移动路径
	private ArrayList<PointF> graphics = new ArrayList<PointF>();

	private float Effect_x = 0;
	private float Effect_y = 0;
	private float Effect_x_new = 0;
	private float Effect_y_new = 0;

	//
	private boolean zhanliflag = false;
	
	// 第一章神坛
	GameChapterBaseSet_Shentan shentan;

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		holder = this.getHolder();
		holder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		// 设置背景常亮
		this.setKeepScreenOn(true);

		// 建两个画笔来绘画游戏
		mPath = new Path();
		lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		lPaint.setColor(Color.BLACK);
		lPaint.setStyle(Paint.Style.STROKE);// 空心
		lPaint.setStrokeJoin(Paint.Join.ROUND);
		lPaint.setStrokeCap(Paint.Cap.ROUND);
		lPaint.setStrokeWidth(5);
		noPaint = new Paint();
		npclist = new ArrayList<Spirit>();
	}

	// 装载基本游戏事件，物体，NPC等
	public void initGameBase() {
		// 加载小华，男猪脚第二种动画编辑方式
		role_Main = new RoleData_Main();
		role_Main.setPlayerImgUrl_pao("spirit/zhujiao/pao/2314-f44c3fde-");
		role_Main.setPlayerImgUrl_zhan("spirit/zhujiao/zhan/1700-af8399b0-");
		role_Main
				.setPlayerImgUrl_attack("spirit/zhujiao/attack/0642-3e26809d-");
		role_Main.setPao_max(7);
		role_Main.setZhan_max(6);
		role_Main.setPlayerName("龙太子");
		role_Main.setYinziImg(GetImgCommen.readBitMap(mContext,
				R.drawable.yinzi));
		role_Main.setTalkbdImg(GetImgCommen.readBitMap(mContext,
				R.drawable.duihuakuang));
		shentan = new GameChapterBaseSet_Shentan(mContext);

		
	}

	// 游戏的初始化
	public void StartGame() {

		// 装载游戏
		initGameBase();
		// 线程实例----------------- 启动线程
		draw_flag = true;
		th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (draw_flag) {
			long startTime = System.currentTimeMillis();
			synchronized (holder) {
				myDraw();
				
				todotime+=PubSet.reFlashTime;
				timeDraw();
			}
			long endTime = System.currentTimeMillis();
			int diffTime = (int) (endTime - startTime);
			while (diffTime <= PubSet.reFlashTime) {
				diffTime = (int) (System.currentTimeMillis() - startTime);
				Thread.yield();
			}
		}
	}
	
	private void timeDraw()
	{
		if(todotime==5000)
		{
			shentan.maps_shentan.todomovemap((int) 780, (int) 400);
		} 
		if(todotime>1000)
		{
			
		}
	}

	private void myDraw() {
		// TODO Auto-generated method stub
		try {
			canvas = holder.lockCanvas();
			if (canvas != null) {
				drawmydream(canvas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void drawmydream(Canvas canvas) {
		canvas.drawColor(Color.WHITE);

		float from_x = 0;
		float from_y = 0;
		if (paotime > 0) {
			from_x = (zhujiao_x_new - zhujiao_x) / paotime;
			from_y = (zhujiao_y_new - zhujiao_y) / paotime;
		}

		// 简单的实现4个斜方向的人物跑动
		if (from_x > 0 && from_y > 0) {
			dc = Direction.右下;
			paodong = ActionToDo.跑动;
		} else if (from_x > 0 && from_y < 0) {
			dc = Direction.右上;
			paodong = ActionToDo.跑动;
		} else if (from_x < 0 && from_y < 0) {
			dc = Direction.左上;
			paodong = ActionToDo.跑动;
		} else if (from_x < 0 && from_y > 0) {
			dc = Direction.左下;
			paodong = ActionToDo.跑动;
		} else if (from_x == 0 && from_y == 0) {

			if (BaseInfo.getAttack_X() > 0 && BaseInfo.getAttack_Y() > 0) {
				dc = Direction.右下;
				int zhandc_x = (int) (BaseInfo.getAttack_X() - zhujiao_x);
				int zhandc_y = (int) (BaseInfo.getAttack_Y() - zhujiao_y);

				if (zhandc_x > 0 && zhandc_y > 0) {
					dc = Direction.右下;
				} else if (zhandc_x > 0 && zhandc_y < 0) {
					dc = Direction.右上;
				} else if (zhandc_x < 0 && zhandc_y < 0) {
					dc = Direction.左上;
				} else if (zhandc_x < 0 && zhandc_y > 0) {
					dc = Direction.左下;
				}
			} else {
				dc = Direction.右下;
			}

			if (zhanliflag == false) {
				paodong = ActionToDo.站立;
				zhanliflag = true;
			}
		}

		zhujiao_x += from_x;
		zhujiao_y += from_y;

		if (zhujiao_x > (PubSet.screenWidth * 0.95)
				&& zhujiao_y > (PubSet.screenHeight * 0.95)) {
			dc = Direction.右下;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x < (PubSet.screenWidth * 0.05)
				&& zhujiao_y < (PubSet.screenHeight * 0.05)) {
			dc = Direction.左上;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x > (PubSet.screenWidth * 0.95)
				&& zhujiao_y < (PubSet.screenHeight * 0.05)) {
			dc = Direction.右上;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x < (PubSet.screenWidth * 0.05)
				&& zhujiao_y > (PubSet.screenHeight * 0.95)) {
			dc = Direction.左下;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x > (PubSet.screenWidth * 0.05)
				&& zhujiao_x < (PubSet.screenWidth * 0.95)
				&& zhujiao_y > (PubSet.screenHeight * 0.95)) {
			dc = Direction.下;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x > (PubSet.screenWidth * 0.05)
				&& zhujiao_x < (PubSet.screenWidth * 0.95)
				&& zhujiao_y < (PubSet.screenHeight * 0.05)) {
			dc = Direction.上;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x > (PubSet.screenWidth * 0.95)
				&& zhujiao_y > (PubSet.screenHeight * 0.05)
				&& zhujiao_y < (PubSet.screenHeight * 0.95)) {
			dc = Direction.左;
			paodong = ActionToDo.跑动;
		}
		if (zhujiao_x < (PubSet.screenWidth * 0.05)
				&& zhujiao_y > (PubSet.screenHeight * 0.05)
				&& zhujiao_y < (PubSet.screenHeight * 0.95)) {
			dc = Direction.右;
			paodong = ActionToDo.跑动;
		}

		// 场景控制
		if (mapflag == MapName.神界_天坛) {
			shentan.maps_shentan.myDraw_Map(canvas, noPaint, event, (int) zhujiao_x,
					(int) zhujiao_y);
		}
		
		paotime--;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		PubSet.resouces = this.getResources();
		PubSet.context = mContext;
		// 屏幕宽高
		PubSet.screenWidth = this.getWidth();
		PubSet.screenHeight = this.getHeight();
		StartGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		draw_flag = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		this.event=event;
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (paodongflag == 1) {
				over = false;
				graphics.clear();
				of = 0;
				graphics.add(new PointF(x, y));
				touch_start(x, y);
				invalidate();

			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (paodongflag == 1) {
				graphics.add(new PointF(x, y));
				touch_move(x, y);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
//			shentan.maps_shentan.movemap((int) x, (int) y);
			// zhujiao_x_new = x;
			// zhujiao_y_new = y;
			// // 根据设置的速度来进行跑动
			// paotime = (int) (((int) Math.abs(zhujiao_x_new - zhujiao_x) +
			// (int) Math
			// .abs(zhujiao_y_new - zhujiao_y)) / paospeed);
			// zhanliflag = false;
			// BaseInfo.setAttack_X(0);
			// BaseInfo.setAttack_Y(0);
			// if(maps_niumowang.isInTouchArea((int)x, (int)y, 0, 0)==true)
			// {
			// zhujiao_x_new = x;
			// zhujiao_y_new = y;
			// // 根据设置的速度来进行跑动
			// paotime = (int) (((int) Math.abs(zhujiao_x_new - zhujiao_x) +
			// (int) Math
			// .abs(zhujiao_y_new - zhujiao_y)) / paospeed);
			// zhanliflag = false;
			// BaseInfo.setAttack_X(0);
			// BaseInfo.setAttack_Y(0);
			// }
			// maps_niumowang.isInTouchArea((int)x, (int)y, 0, 0);
			// if (sprirt_shusheng.onTouch(event) == false ) {
			// zhujiao_x_new = x;
			// zhujiao_y_new = y;
			// // 根据设置的速度来进行跑动
			// paotime = (int) (((int) Math.abs(zhujiao_x_new - zhujiao_x) +
			// (int) Math
			// .abs(zhujiao_y_new - zhujiao_y)) / paospeed);
			// zhanliflag = false;
			// BaseInfo.setAttack_X(0);
			// BaseInfo.setAttack_Y(0);
			// } else {
			// BaseInfo.setAttack_X(x);
			// BaseInfo.setAttack_Y(y);
			// BaseInfo.setAttack_X2(x);
			// BaseInfo.setAttack_Y2(y);
			// }
			break;
		}

		return true;
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
	}

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

}