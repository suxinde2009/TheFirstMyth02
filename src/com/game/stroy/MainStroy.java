package com.game.stroy;

import java.util.ArrayList;
import java.util.List;

import com.game.commen.GameXmlcommen;
import com.game.data.StroyTipData;

public class MainStroy {
	static List<StroyTipData> stdlist;
	int nowstory = -1;
	static GameXmlcommen gx = new GameXmlcommen();
	public static List<StroyTipData> getmainstroy_chapter1()
	{
		stdlist = new ArrayList<StroyTipData>();
		
		stdlist = gx.getRiversFromXml("stroyxml/maimstoy1.xml");
		
		return stdlist;
	}
}