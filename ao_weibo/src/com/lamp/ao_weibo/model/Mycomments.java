package com.lamp.ao_weibo.model;

public class Mycomments {
	private long id;
	private long userid;
	private String userimg;
	private String username;
	private String time;
	private String source;
	private String comment_text;
	private String weibo_text;
	
	public Mycomments(long id, long userid, String userimg, String username,
			String time, String source, String comment_text, String weibo_text) {
		super();
		this.id = id;
		this.userid = userid;
		this.userimg = userimg;
		this.username = username;
		this.time = time;
		this.source = source;
		this.comment_text = comment_text;
		this.weibo_text = weibo_text;
	}
	public Mycomments() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getUserimg() {
		return userimg;
	}
	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getComment_text() {
		return comment_text;
	}
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}
	public String getWeibo_text() {
		return weibo_text;
	}
	public void setWeibo_text(String weibo_text) {
		this.weibo_text = weibo_text;
	}
	@Override
	public String toString() {
		return "Mycomments [id=" + id + ", userid=" + userid + ", userimg="
				+ userimg + ", username=" + username + ", time=" + time
				+ ", source=" + source + ", comment_text=" + comment_text
				+ ", weibo_text=" + weibo_text + "]";
	}
}
