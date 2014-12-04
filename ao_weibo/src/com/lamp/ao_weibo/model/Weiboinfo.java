package com.lamp.ao_weibo.model;

import java.io.Serializable;

public class Weiboinfo implements Serializable{
	private int id;
	private long weibo_id;
	private String weibo_user_img;
	private String weibo_user_name;
	private String weibo_time;
	private String weibo_source;//数据来源
	private String weibo_text;
	private String weibo_spic;
	private String weibo_mpic;
	private String weibo_bpic;
	private int weibo_zan;
	private int weibo_zhuanfa;
	private int weibo_pinglun;
	public Weiboinfo(long weibo_id, String weibo_user_img,
			String weibo_user_name, String weibo_time, String weibo_source,
			String weibo_text, String weibo_spic, String weibo_mpic,
			String weibo_bpic, int weibo_zan, int weibo_zhuanfa,
			int weibo_pinglun) {
		super();
		this.weibo_id = weibo_id;
		this.weibo_user_img = weibo_user_img;
		this.weibo_user_name = weibo_user_name;
		this.weibo_time = weibo_time;
		this.weibo_source = weibo_source;
		this.weibo_text = weibo_text;
		this.weibo_spic = weibo_spic;
		this.weibo_mpic = weibo_mpic;
		this.weibo_bpic = weibo_bpic;
		this.weibo_zan = weibo_zan;
		this.weibo_zhuanfa = weibo_zhuanfa;
		this.weibo_pinglun = weibo_pinglun;
	}
	public Weiboinfo() {
		super();
	}
	public long getWeibo_id() {
		return weibo_id;
	}
	public void setWeibo_id(long weibo_id) {
		this.weibo_id = weibo_id;
	}
	public String getWeibo_user_img() {
		return weibo_user_img;
	}
	public void setWeibo_user_img(String weibo_user_img) {
		this.weibo_user_img = weibo_user_img;
	}
	public String getWeibo_user_name() {
		return weibo_user_name;
	}
	public void setWeibo_user_name(String weibo_user_name) {
		this.weibo_user_name = weibo_user_name;
	}
	public String getWeibo_time() {
		return weibo_time;
	}
	public void setWeibo_time(String weibo_time) {
		this.weibo_time = weibo_time;
	}
	public String getWeibo_source() {
		return weibo_source;
	}
	public void setWeibo_source(String weibo_source) {
		this.weibo_source = weibo_source;
	}
	public String getWeibo_text() {
		return weibo_text;
	}
	public void setWeibo_text(String weibo_text) {
		this.weibo_text = weibo_text;
	}
	public String getWeibo_spic() {
		return weibo_spic;
	}
	public void setWeibo_spic(String weibo_spic) {
		this.weibo_spic = weibo_spic;
	}
	public String getWeibo_mpic() {
		return weibo_mpic;
	}
	public void setWeibo_mpic(String weibo_mpic) {
		this.weibo_mpic = weibo_mpic;
	}
	public String getWeibo_bpic() {
		return weibo_bpic;
	}
	public void setWeibo_bpic(String weibo_bpic) {
		this.weibo_bpic = weibo_bpic;
	}
	public int getWeibo_zan() {
		return weibo_zan;
	}
	public void setWeibo_zan(int weibo_zan) {
		this.weibo_zan = weibo_zan;
	}
	public int getWeibo_zhuanfa() {
		return weibo_zhuanfa;
	}
	public void setWeibo_zhuanfa(int weibo_zhuanfa) {
		this.weibo_zhuanfa = weibo_zhuanfa;
	}
	public int getWeibo_pinglun() {
		return weibo_pinglun;
	}
	public void setWeibo_pinglun(int weibo_pinglun) {
		this.weibo_pinglun = weibo_pinglun;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((weibo_bpic == null) ? 0 : weibo_bpic.hashCode());
		result = prime * result + (int) (weibo_id ^ (weibo_id >>> 32));
		result = prime * result
				+ ((weibo_mpic == null) ? 0 : weibo_mpic.hashCode());
		result = prime * result + weibo_pinglun;
		result = prime * result
				+ ((weibo_source == null) ? 0 : weibo_source.hashCode());
		result = prime * result
				+ ((weibo_spic == null) ? 0 : weibo_spic.hashCode());
		result = prime * result
				+ ((weibo_text == null) ? 0 : weibo_text.hashCode());
		result = prime * result
				+ ((weibo_time == null) ? 0 : weibo_time.hashCode());
		result = prime * result
				+ ((weibo_user_img == null) ? 0 : weibo_user_img.hashCode());
		result = prime * result
				+ ((weibo_user_name == null) ? 0 : weibo_user_name.hashCode());
		result = prime * result + weibo_zan;
		result = prime * result + weibo_zhuanfa;
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
		Weiboinfo other = (Weiboinfo) obj;
		if (weibo_bpic == null) {
			if (other.weibo_bpic != null)
				return false;
		} else if (!weibo_bpic.equals(other.weibo_bpic))
			return false;
		if (weibo_id != other.weibo_id)
			return false;
		if (weibo_mpic == null) {
			if (other.weibo_mpic != null)
				return false;
		} else if (!weibo_mpic.equals(other.weibo_mpic))
			return false;
		if (weibo_pinglun != other.weibo_pinglun)
			return false;
		if (weibo_source == null) {
			if (other.weibo_source != null)
				return false;
		} else if (!weibo_source.equals(other.weibo_source))
			return false;
		if (weibo_spic == null) {
			if (other.weibo_spic != null)
				return false;
		} else if (!weibo_spic.equals(other.weibo_spic))
			return false;
		if (weibo_text == null) {
			if (other.weibo_text != null)
				return false;
		} else if (!weibo_text.equals(other.weibo_text))
			return false;
		if (weibo_time == null) {
			if (other.weibo_time != null)
				return false;
		} else if (!weibo_time.equals(other.weibo_time))
			return false;
		if (weibo_user_img == null) {
			if (other.weibo_user_img != null)
				return false;
		} else if (!weibo_user_img.equals(other.weibo_user_img))
			return false;
		if (weibo_user_name == null) {
			if (other.weibo_user_name != null)
				return false;
		} else if (!weibo_user_name.equals(other.weibo_user_name))
			return false;
		if (weibo_zan != other.weibo_zan)
			return false;
		if (weibo_zhuanfa != other.weibo_zhuanfa)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Weiboinfo [weibo_id=" + weibo_id + ", weibo_user_img="
				+ weibo_user_img + ", weibo_user_name=" + weibo_user_name
				+ ", weibo_time=" + weibo_time + ", weibo_source="
				+ weibo_source + ", weibo_text=" + weibo_text + ", weibo_spic="
				+ weibo_spic + ", weibo_mpic=" + weibo_mpic + ", weibo_bpic="
				+ weibo_bpic + ", weibo_zan=" + weibo_zan + ", weibo_zhuanfa="
				+ weibo_zhuanfa + ", weibo_pinglun=" + weibo_pinglun + "]";
	}
	
	

}
