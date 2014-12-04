package com.lamp.ao_weibo.constants;

public class Constant {
	//认证
	public static final String APPKEY = "3482483709";
	public static final String APP_SECRET = "ba876c95a40658151764808402f0c5b1";
	public static final String REDIRECT_URL = "http://www.baidu.com";
	public static final String AUTH_URL = "https://api.weibo.com/oauth2/access_token";
	
	//用户
	public static final String USERS_SHOW = "https://api.weibo.com/2/users/show.json";
	public static final String HOME_TIMELINE = "https://api.weibo.com/2/statuses/home_timeline.json";
	//微博
	public static final String SEND_WEIBO = "https://api.weibo.com/2/statuses/update.json";
	public static final String ATME_URL = "https://api.weibo.com/2/statuses/mentions.json";
	public static final String BYME_URL = "https://api.weibo.com/2/comments/by_me.json";
	public static final String HOTNEWS_URL = "https://api.weibo.com/2/statuses/public_timeline.json";
	public static final String USER_SHOW = "https://api.weibo.com/2/users/show.json";
	public static final String GUANZHU_URL = "https://api.weibo.com/2/friendships/create.json";
	public static final String TOME_URL = "https://api.weibo.com/2/comments/to_me.json";
	public static final String USER_COUNTS = "https://api.weibo.com/2/users/counts.json";
	
	public static final String ID2INFO = "https://api.weibo.com/2/statuses/show.json";
	public static String pinglun_url = "https://api.weibo.com/2/comments/show.json";
	
	//好友推荐
	public static final String TUIJIAN_HOT_URL = "https://api.weibo.com/2/suggestions/users/hot.json";
	public static final String MYPHOTO_URL = "https://api.weibo.com/2/place/users/photos.json";
	//收藏
	public static final String FAVORITES_URL = "https://api.weibo.com/2/favorites.json";
	public static final String PINGLUN_WEIBO = "https://api.weibo.com/2/comments/create.json";
}
