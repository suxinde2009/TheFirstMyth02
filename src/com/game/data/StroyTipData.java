package com.game.data;

public class StroyTipData {

	// 对话者头像
	private String HeadUrl;
	// 对话者姓名
	private String StroyName;
	// 提示框内容
	private String StroyInfo;
	// 按钮内容
	private String TodoInfo;
	// 章节数
	private int chapter;
	// 当前进度
	private int progress;
	
	public int getChapter() {
		return chapter;
	}
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public String getTodoInfo() {
		return TodoInfo;
	}
	public void setTodoInfo(String todoInfo) {
		TodoInfo = todoInfo;
	}
	public String getHeadUrl() {
		return HeadUrl;
	}
	public void setHeadUrl(String headUrl) {
		HeadUrl = headUrl;
	}
	public String getStroyName() {
		return StroyName;
	}
	public void setStroyName(String stroyName) {
		StroyName = stroyName;
	}
	public String getStroyInfo() {
		return StroyInfo;
	}
	public void setStroyInfo(String stroyInfo) {
		StroyInfo = stroyInfo;
	}

}