package com.game.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.fengshen.R;
import com.game.commen.ActionToDo;
import com.game.commen.Direction;
import com.game.commen.EffectName;
import com.game.commen.GetImgCommen;
import com.game.commen.MapName;
import com.game.data.RoleData_Main;
import com.game.data.StroyTipData;
import com.game.effect.SpecialEffect;
import com.game.renwu.Spirit_NPC;
import com.game.stroy.MainStroy;
import com.game.tip.PublicTips;

public class GameChapterBaseSet_Shentan {
	
	public List<StroyTipData> stdlist;
	private Context mContext;
	public GameMap maps_shentan;
	public PublicTips tips;
	public boolean stroyflag = false; 
	public int stroytime = 0;
	public static int StoryFlaging = 0;
	//特效容器
	public List<SpecialEffect> effectlist;
	public List<Spirit_NPC> NPCList;
	public GameChapterBaseSet_Shentan(Context Context) {
		// TODO Auto-generated constructor stub
		mContext=Context;
		
		stdlist = new ArrayList<StroyTipData>();
		stdlist = MainStroy.getmainstroy_chapter1();
		tips = new PublicTips("publicimg/tipbd.png",stdlist,0);
		
		// 神坛特效1
		SpecialEffect shentan1 = new SpecialEffect(EffectName.环境_神坛1,
						"effects/part1/shentan1/0049-ecd97d89-",773,395, 14, true);
		// 神坛特效2
		SpecialEffect shentan2 = new SpecialEffect(EffectName.环境_神坛2,
								"effects/part1/shentan2/0053-1be628f9-",773,395, 19, false);
		effectlist = new ArrayList<SpecialEffect>();
		effectlist.add(shentan1);
		effectlist.add(shentan2);
		
		RoleData_Main roloinfo_chijiaodaxian = new RoleData_Main();
		roloinfo_chijiaodaxian
				.setPlayerImgUrl_zhan("spirit/npc/chijiaodaxian/0329-2073f650-");
		roloinfo_chijiaodaxian
				.setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		roloinfo_chijiaodaxian.setPao_max(0);
		roloinfo_chijiaodaxian.setZhan_max(9);
		roloinfo_chijiaodaxian.setMapset_X(600);
		roloinfo_chijiaodaxian.setMapset_Y(370);
		roloinfo_chijiaodaxian.setDefault_dir(Direction.右下);
		roloinfo_chijiaodaxian.setDefault_act(ActionToDo.站立);
		roloinfo_chijiaodaxian.setPlayerName("赤脚大仙");
		roloinfo_chijiaodaxian.setTalkAbout("我的脚不仅大，还很臭，哈哈...");
		Spirit_NPC npc_chijiaodaxian = new Spirit_NPC(roloinfo_chijiaodaxian);
		
		RoleData_Main roloinfo_qingxuzhenren = new RoleData_Main();
		roloinfo_qingxuzhenren
				.setPlayerImgUrl_zhan("spirit/npc/qingxuzhenjun/0352-22406e66-");
		roloinfo_qingxuzhenren
				.setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		roloinfo_qingxuzhenren.setPao_max(0);
		roloinfo_qingxuzhenren.setZhan_max(13);
		roloinfo_qingxuzhenren.setMapset_X(950);
		roloinfo_qingxuzhenren.setMapset_Y(350);
		roloinfo_qingxuzhenren.setDefault_dir(Direction.左下);
		roloinfo_qingxuzhenren.setDefault_act(ActionToDo.站立);
		roloinfo_qingxuzhenren.setPlayerName("清虚真君");
		roloinfo_qingxuzhenren.setTalkAbout("牛鼻子老道一枚,我的坐骑可是青牛噢！");
		Spirit_NPC npc_qingxuzhenren = new Spirit_NPC(roloinfo_qingxuzhenren);
		
		RoleData_Main roloinfo_taishanglaojun = new RoleData_Main();
		roloinfo_taishanglaojun
				.setPlayerImgUrl_zhan("spirit/npc/taishanglaojun/0533-3599d5c3-");
		roloinfo_taishanglaojun
				.setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		roloinfo_taishanglaojun.setPao_max(0);
		roloinfo_taishanglaojun.setZhan_max(9);
		roloinfo_taishanglaojun.setMapset_X(490);
		roloinfo_taishanglaojun.setMapset_Y(400);
		roloinfo_taishanglaojun.setDefault_dir(Direction.右下);
		roloinfo_taishanglaojun.setDefault_act(ActionToDo.站立);
		roloinfo_taishanglaojun.setPlayerName("太上老君");
		roloinfo_taishanglaojun.setTalkAbout("我的炼丹炉那是天地间的孕育出来的绝世宝物！");
		Spirit_NPC npc_taishanglaojun = new Spirit_NPC(roloinfo_taishanglaojun);
		
		RoleData_Main roloinfo_taiyizhenren = new RoleData_Main();
		roloinfo_taiyizhenren
				.setPlayerImgUrl_zhan("spirit/npc/xianren/1952-ca3334ff-");
		roloinfo_taiyizhenren
				.setPlayerImgUrl_selectflag("spirit/npcpublic/0003-16c93edf-");
		roloinfo_taiyizhenren.setPao_max(0);
		roloinfo_taiyizhenren.setZhan_max(10);
		roloinfo_taiyizhenren.setMapset_X(1100);
		roloinfo_taiyizhenren.setMapset_Y(420);
		roloinfo_taiyizhenren.setDefault_dir(Direction.左下);
		roloinfo_taiyizhenren.setDefault_act(ActionToDo.站立);
		roloinfo_taiyizhenren.setPlayerName("太乙真人");
		roloinfo_taiyizhenren.setTalkAbout("我乃乾元山金光洞太乙真人，各位上仙有礼了");
		Spirit_NPC npc_taiyizhenren = new Spirit_NPC(roloinfo_taiyizhenren);
		
		
		NPCList = new ArrayList<Spirit_NPC>();
		NPCList.add(npc_chijiaodaxian);
		NPCList.add(npc_qingxuzhenren);
		NPCList.add(npc_taishanglaojun);
		NPCList.add(npc_taiyizhenren);
		
		maps_shentan = new GameMap(mContext,GetImgCommen.readBitMap(mContext,
				R.drawable.map_shengjie),GetImgCommen.readBitMap(mContext,
						R.drawable.map_shengjie0),null, 10, 10, NPCList, null,effectlist,
						tips,MapName.神界_天坛);
		
		Thread td = new Thread(rbtime_story);
		td.start();
		stroyflag =true;
//		StroyPlayIng(StoryFlag,MainStroy.getmainstroy_chapter1());
		
	}

	//特效容器中添加新的特效
	public void addSpecialEffect(SpecialEffect addEffect)
	{
		effectlist.add(addEffect);
	}
	
	//特效容器中移除特效
	public void removeSpecialEffect(SpecialEffect addEffect)
	{
		effectlist.remove(addEffect);
	}
	
	
	Runnable rbtime_story =new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (stroyflag) {
				try {
					Thread.sleep(PubSet.miao);
					
					if(stroytime==10)
					{
						tips.StroyPlayIng(0,stdlist);
						tips.showflag=true;
					}
					
					stroytime++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}