package com.game.base;

import java.util.List;
import java.util.Random;

import com.example.fengshen.R;
import com.game.commen.ActionToDo;
import com.game.commen.Direction;
import com.game.commen.GetImgCommen;
import com.game.commen.MapName;
import com.game.commen.Paintforziti;
import com.game.commen.ToDo;
import com.game.commen.IrregularButton.TouchChecker;
import com.game.data.RoleData;
import com.game.data.RoleData_Main;
import com.game.data.StroyTipData;
import com.game.effect.SpecialEffect;
import com.game.fengshen.GameMainActivity;
import com.game.renwu.Objs;
import com.game.renwu.Spirit_Main;
import com.game.renwu.Spirit_NPC;
import com.game.tip.PublicTips;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Toast;

public class GameMap implements TouchChecker {
	private Bitmap bmpGBG;// 背景
	private Bitmap map_kexing;// 可行区域层
	private Bitmap map_jianzhu;// 背景
	// Npc列表
	private List<Spirit_NPC> npclist;
	// 事件物体列表
	private List<Objs> objslist;
	// 特效列表
	private List<SpecialEffect> effectlist;
	// 初始地图坐标
	public int F_x = 0;
	public int F_y = 0;
	private int movespeed = 10;// 地图移动速度

	Random rd = new Random();
	Random rd2 = new Random();
	MapName mapname = MapName.神界_天坛;

	Direction dc = Direction.左;
	private boolean paodong = false;

	int guaiqun1_x = 378;
	int guaiqun1_y = 363;
	int zuobiao_x_new = 0;
	int zuobiao_y_new = 0;

	// 默认刷新多少帧的时间
	private int movetime = 0;
	private int movetime2 = 0;
	// 将地图分为三大层
	// 一层地图 二层可行区域层 三层建筑物遮罩层
	private Context context;

	// 地图总长宽
	private int mapwidth = -1;
	private int mapheight = -1;
	// 地图中心点坐标
	private int center_X = -1;
	private int center_Y = -1;

	private long from_x = 0;
	private long from_y = 0;
	private int move_X;
	private int move_Y;
	Bitmap ObjsImg;
	PublicTips tips;

	public long playtime = 0;
	
	public GameMap(Context context, Bitmap bmpGBG, Bitmap map_kexing,
			Bitmap map_jianzhu, int F_x, int F_y, List<Spirit_NPC> npclist,
			List<Objs> objslist, List<SpecialEffect> effectlist,
			PublicTips tips, MapName mapname) {
		super();
		this.effectlist = effectlist;
		this.context = context;
		this.mapname = mapname;
		this.map_kexing = map_kexing;
		this.map_jianzhu = map_jianzhu;
		this.bmpGBG = bmpGBG;
		this.npclist = npclist;
		this.objslist = objslist;
		this.F_x = F_x;
		this.F_y = F_y;
		// 地图总长宽
		mapwidth = bmpGBG.getWidth();
		mapheight = bmpGBG.getHeight();
		center_X = (int) (PubSet.screenWidth / 2);
		center_Y = (int) (PubSet.screenHeight / 2);
		this.tips = tips;

	}

	public int movemap(int move_X, int move_Y) {

		this.move_X = move_X;
		this.move_Y = move_Y;
		alljuli = (int) (((int) Math.abs(move_X - center_X) + (int) Math
				.abs(move_Y - center_Y)));
		if (alljuli >= 300) {
			movetime = (int) (((int) Math.abs(move_X - center_X) + (int) Math
					.abs(move_Y - center_Y)) / 10);
		} else if (alljuli >= 200) {
			movetime = (int) (((int) Math.abs(move_X - center_X) + (int) Math
					.abs(move_Y - center_Y)) / 5);
		} else if (alljuli >= 100) {
			movetime = (int) (((int) Math.abs(move_X - center_X) + (int) Math
					.abs(move_Y - center_Y)) / 3);
		} else if (alljuli < 100) {
			movetime = (int) (((int) Math.abs(move_X - center_X) + (int) Math
					.abs(move_Y - center_Y)) / 2);
		}

		return movetime;
	}

	public int todomovemap(int last_X, int last_Y) {

		this.move_X = last_X + F_x;
		this.move_Y = last_Y + F_y;
		alljuli = (int) (((int) Math.abs(move_X - F_x) + (int) Math.abs(move_Y
				- F_y)));
		if (alljuli >= 300) {
			movetime = (int) (((int) Math.abs(move_X - F_x) + (int) Math
					.abs(move_Y - F_y)) / 10);
		} else if (alljuli >= 200) {
			movetime = (int) (((int) Math.abs(move_X - F_x) + (int) Math
					.abs(move_Y - F_y)) / 5);
		} else if (alljuli >= 100) {
			movetime = (int) (((int) Math.abs(move_X - F_x) + (int) Math
					.abs(move_Y - F_y)) / 3);
		} else if (alljuli < 100) {
			movetime = (int) (((int) Math.abs(move_X - F_x) + (int) Math
					.abs(move_Y - F_y)) / 2);
		}
		return movetime;
	}

	int alljuli = 0;
	int yiban = 0;
	int a = 0;
	int b = 0;

	// 绘制背景
	public void myDraw_Map(Canvas canvas, Paint paint, MotionEvent event,
			int spirit_x, int spirit_y) {

		// 屏幕长宽
		int screenWidth = PubSet.screenWidth;
		int screenHeight = PubSet.screenHeight;

		if (movetime > 0) {

			from_x = (int) ((move_X - center_X) / movetime);
			from_y = (int) ((move_Y - center_Y) / movetime);
			F_x += from_x;
			F_y += from_y;
			move_X -= from_x;
			move_Y -= from_y;
			movetime--;
		}

		// if (spirit_x > (screenWidth * 0.95) && spirit_y > (screenHeight *
		// 0.95)) {
		// // 右下移动
		// F_x += movespeed;
		// F_y += movespeed;
		// } else if (spirit_x < (screenWidth * 0.05)
		// && spirit_y < (screenHeight * 0.05)) {
		// // 左上移动
		// F_x -= movespeed;
		// F_y -= movespeed;
		// } else if (spirit_x > (screenWidth * 0.95)
		// && spirit_y < (screenHeight * 0.05)) {
		// // 右上
		// F_x += movespeed;
		// F_y -= movespeed;
		// } else if (spirit_x < (screenWidth * 0.05)
		// && spirit_y > (screenHeight * 0.95)) {
		// // 左下
		// F_x -= movespeed;
		// F_y += movespeed;
		// } else if (spirit_x > (screenWidth * 0.05)
		// && spirit_x < (screenWidth * 0.95)
		// && spirit_y > (screenHeight * 0.95)) {
		// // 下
		// F_y += movespeed;
		// } else if (spirit_x > (screenWidth * 0.05)
		// && spirit_x < (screenWidth * 0.95)
		// && spirit_y < (screenHeight * 0.05)) {
		// // 上
		// F_y -= movespeed;
		// } else if (spirit_x > (screenWidth * 0.95)
		// && spirit_y > (screenHeight * 0.05)
		// && spirit_y < (screenHeight * 0.95)) {
		// // 左
		// F_x += movespeed;
		// } else if (spirit_x < (screenWidth * 0.05)
		// && spirit_y > (screenHeight * 0.05)
		// && spirit_y < (screenHeight * 0.95)) {
		// // 右
		// F_x -= movespeed;
		// }

		// 防止地图超出屏幕
		if (F_x >= (mapwidth - screenWidth)) {
			F_x = mapwidth - screenWidth;
		}
		if (F_y >= (mapheight - screenHeight)) {
			F_y = (mapheight - screenHeight);
		}
		if (F_x <= 0) {
			F_x = 0;

		}
		if (F_y <= 0) {
			F_y = 0;
		}
		// 截取会动的地图
		// 根据人物脚下的坐标设定位移量，随人物脚下坐标移动地图
		Rect map_rect = null;
		map_rect = new Rect();
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

		Paint p_zuobiao = new Paint();
		p_zuobiao.setTextSize(16);
		p_zuobiao.setColor(Color.GREEN);

		Paint renwu_zuobiao = new Paint();
		renwu_zuobiao.setTextSize(16);
		renwu_zuobiao.setColor(Color.RED);

		String map_zuobiao = F_x + "/" + F_y;
		String renwu_zuobiaostr = (Integer.valueOf(spirit_x) + Integer
				.valueOf(F_x))
				+ "/"
				+ (Integer.valueOf(spirit_y) + Integer.valueOf(F_y));
		String renwu_zuobiaostr2 = spirit_x + "/" + spirit_y;
		canvas.drawBitmap(bmpGBG, map_rect, screen_rect, paint);
		// canvas.drawBitmap(map_kexing, map_rect, screen_rect, paint);
		canvas.drawText(map_zuobiao, screenWidth - 120, screenHeight
				- (36 + 18), p_zuobiao);
		canvas.drawText(renwu_zuobiaostr, screenWidth - 120, screenHeight - 36,
				renwu_zuobiao);
		canvas.drawText(renwu_zuobiaostr2, screenWidth - 120,
				screenHeight - 18, renwu_zuobiao);

		// 加载特效
		if (effectlist != null && mapname == MapName.神界_天坛) {
			for (int i = 0; i < effectlist.size(); i++) {
				effectlist.get(i).myDraw_Effect(context, canvas, paint,
						effectlist.get(i).setmap_x - F_x,
						effectlist.get(i).setmap_y - F_y);
			}
		}

		if (npclist != null && mapname == MapName.神界_天坛) {
			for (int i = 0; i < npclist.size(); i++) {
				npclist.get(i).myDraw_Spirit(context, canvas, paint,
						npclist.get(i).roloinfo.getMapset_X() - F_x,
						npclist.get(i).roloinfo.getMapset_Y() - F_y,
						npclist.get(i).roloinfo.getDefault_dir(),
						npclist.get(i).roloinfo.getDefault_act());
			}
		}

		tips.myDraw_Tips(context, canvas, paint, center_X, center_Y + center_Y
				/ 3);
		if (event != null) {
			if (tips.onTouch(event) == true) {
//				canvas.drawText("点击了啊啊啊啊啊", screenWidth / 2, screenHeight / 2,
//						Paintforziti.SetPaint(14, Color.WHITE, Color.BLACK));
				
			}
		}
		
		playtime++;
	};

	
	
	int go = 0;
	String rdstr1 = "4,4,0,0,0,0,0,0,0,0,-4,-4,-4,-4,-4,4,4,4,4,4,-4,-4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";
	String rdstr2 = "4,4,4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-4,-4,-4,-4,-4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

	public void getMoveRandom(Direction lastdc) {

		String[] rdstrlist = rdstr1.split(",");
		String[] rdstrlist2 = rdstr2.split(",");
		String lastrd = rdstrlist[go];
		String lastrd2 = rdstrlist2[go];
		zuobiao_x_new = Integer.valueOf(lastrd);
		zuobiao_y_new = Integer.valueOf(lastrd2);

		if (zuobiao_x_new > 0 && zuobiao_y_new > 0) {
			dc = Direction.右下;
			paodong = true;
		}
		if (zuobiao_x_new < 0 && zuobiao_y_new < 0) {
			dc = Direction.左上;
			paodong = true;
		}
		if (zuobiao_x_new > 0 && zuobiao_y_new < 0) {
			dc = Direction.右上;
			paodong = true;
		}
		if (zuobiao_x_new < 0 && zuobiao_y_new > 0) {
			dc = Direction.左下;
			paodong = true;
		}
		if (zuobiao_x_new == 0 && zuobiao_y_new > 0) {
			dc = Direction.下;
			paodong = true;
		}
		if (zuobiao_x_new == 0 && zuobiao_y_new < 0) {
			dc = Direction.上;
			paodong = true;
		}
		if (zuobiao_x_new > 0 && zuobiao_y_new == 0) {
			dc = Direction.左;
			paodong = true;
		}
		if (zuobiao_x_new < 0 && zuobiao_y_new == 0) {
			dc = Direction.右;
			paodong = true;
		}
		if (zuobiao_x_new == 0 && zuobiao_y_new == 0) {
			dc = lastdc;
			paodong = false;
		}

		if (go >= 39) {
			go = 0;
		}

		go++;
	}

	public void getMoveRandom2(Direction lastdc) {

		String rdstr1 = "4,0,0,0,0,0,0,0,0,0,0,-4";
		String[] rdstrlist = rdstr1.split(",");
		String lastrd = rdstrlist[rd.nextInt(12)];
		String lastrd2 = rdstrlist[rd.nextInt(12)];
		zuobiao_x_new = Integer.valueOf(lastrd);
		zuobiao_y_new = Integer.valueOf(lastrd2);

		if (zuobiao_x_new > 0 && zuobiao_y_new > 0) {
			dc = Direction.右下;
			paodong = true;
		}
		if (zuobiao_x_new < 0 && zuobiao_y_new < 0) {
			dc = Direction.左上;
			paodong = true;
		}
		if (zuobiao_x_new > 0 && zuobiao_y_new < 0) {
			dc = Direction.右上;
			paodong = true;
		}
		if (zuobiao_x_new < 0 && zuobiao_y_new > 0) {
			dc = Direction.左下;
			paodong = true;
		}
		if (zuobiao_x_new == 0 && zuobiao_y_new > 0) {
			dc = Direction.下;
			paodong = true;
		}
		if (zuobiao_x_new == 0 && zuobiao_y_new < 0) {
			dc = Direction.上;
			paodong = true;
		}
		if (zuobiao_x_new > 0 && zuobiao_y_new == 0) {
			dc = Direction.左;
			paodong = true;
		}
		if (zuobiao_x_new < 0 && zuobiao_y_new == 0) {
			dc = Direction.右;
			paodong = true;
		}
		if (zuobiao_x_new == 0 && zuobiao_y_new == 0) {
			dc = lastdc;
			paodong = false;
		}

	}

	@Override
	public boolean isInTouchArea(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		try {
			if (map_kexing != null) {
				int pixel = map_kexing.getPixel(x + F_x, y + F_y);

				if (((pixel >> 24) & 0xff) > 0) {
					// tip("可行区域");
					return true;
				}
			}
			// tip("不可行区域");
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public void tip(String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}