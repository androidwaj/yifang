package com.lamp.ao_weibo.model;

public class User {
	private String id;
	private String user_id;
	private String username;
	private String token;
	private String head_img;
	private String description;
	
	public User(String id, String user_id, String username, String token,
			String head_img, String description) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.username = username;
		this.token = token;//访问令牌
		this.head_img = head_img;
		this.description = description;
	}

	public User() {
		super();
	}
	
	public User(String user_id, String username, String token, String head_img,
			String description) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.token = token;
		this.head_img = head_img;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", user_id=" + user_id + ", username="
				+ username + ", token=" + token + ", head_img=" + head_img
				+ ", description=" + description + "]";
	}
	
}
