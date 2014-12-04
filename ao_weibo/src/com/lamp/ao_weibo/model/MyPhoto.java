package com.lamp.ao_weibo.model;

public class MyPhoto { 
	private String id;
	private String thumbnailpic;
	private String bmiddlepic;
	private String bigpic;
	public MyPhoto(String id, String thumbnailpic, String bmiddlepic,
			String bigpic) {
		super();
		this.id = id;
		this.thumbnailpic = thumbnailpic;
		this.bmiddlepic = bmiddlepic;
		this.bigpic = bigpic;
	}
	public MyPhoto() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getThumbnailpic() {
		return thumbnailpic;
	}
	public void setThumbnailpic(String thumbnailpic) {
		this.thumbnailpic = thumbnailpic;
	}
	public String getBmiddlepic() {
		return bmiddlepic;
	}
	public void setBmiddlepic(String bmiddlepic) {
		this.bmiddlepic = bmiddlepic;
	}
	public String getBigpic() {
		return bigpic;
	}
	public void setBigpic(String bigpic) {
		this.bigpic = bigpic;
	}
	@Override
	public String toString() {
		return "UserPic [id=" + id + ", thumbnailpic=" + thumbnailpic
				+ ", bmiddlepic=" + bmiddlepic + ", bigpic=" + bigpic + "]";
	}
	

}
