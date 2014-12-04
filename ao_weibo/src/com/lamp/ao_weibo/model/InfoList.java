package com.lamp.ao_weibo.model;

public class InfoList {
	private long id;
	private long info_id;
	private String info_icon;
	private String info_name;
	private String info_time;
	public InfoList(long info_id, String info_icon,
			String info_name, String info_time, String info_content) {
		super();
		this.info_id = info_id;
		this.info_icon = info_icon;
		this.info_name = info_name;
		this.info_time = info_time;
		this.info_content = info_content;
	}
	private String info_content;
	public InfoList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InfoList(long info_id, String info_name,
			String info_time, String info_content) {
		super();
		this.info_id = info_id;
		this.info_name = info_name;
		this.info_time = info_time;
		this.info_content = info_content;
	}
	public InfoList(String info_name, String info_time,
			String info_content) {
		super();
		this.info_name = info_name;
		this.info_time = info_time;
		this.info_content = info_content;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((info_content == null) ? 0 : info_content.hashCode());
		result = prime * result + (int) (info_id ^ (info_id >>> 32));
		result = prime * result
				+ ((info_name == null) ? 0 : info_name.hashCode());
		result = prime * result
				+ ((info_time == null) ? 0 : info_time.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoList other = (InfoList) obj;
		if (info_content == null) {
			if (other.info_content != null)
				return false;
		} else if (!info_content.equals(other.info_content))
			return false;
		if (info_id != other.info_id)
			return false;
		if (info_name == null) {
			if (other.info_name != null)
				return false;
		} else if (!info_name.equals(other.info_name))
			return false;
		if (info_time == null) {
			if (other.info_time != null)
				return false;
		} else if (!info_time.equals(other.info_time))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "infoUser [info_id=" + info_id + ", info_name="
				+ info_name + ", info_time=" + info_time
				+ ", info_content=" + info_content + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInfo_id() {
		return info_id;
	}
	public void setInfo_id(long info_id) {
		this.info_id = info_id;
	}
	public String getInfo_icon() {
		return info_icon;
	}
	public void setInfo_icon(String info_icon) {
		this.info_icon = info_icon;
	}
	public String getInfo_name() {
		return info_name;
	}
	public void setInfo_name(String info_name) {
		this.info_name = info_name;
	}
	public String getInfo_time() {
		return info_time;
	}
	public void setInfo_time(String info_time) {
		this.info_time = info_time;
	}
	public String getInfo_content() {
		return info_content;
	}
	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}
	
	
	
}
