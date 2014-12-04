package com.lamp.ao_weibo.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.lamp.ao_weibo.constants.Constant;
import com.lamp.ao_weibo.model.MyPhoto;
import com.lamp.ao_weibo.model.Mycomments;
import com.lamp.ao_weibo.model.InfoList;
import com.lamp.ao_weibo.model.User;
import com.lamp.ao_weibo.model.Weiboinfo;

public class Tools {
	public static boolean isConnect(Context context){
		ConnectivityManager manager =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] infos = manager.getAllNetworkInfo();
		int flag = 0;
		for (int i = 0; i < infos.length; i++) {
			if(infos[i].isConnected()){
				flag++;
			}
		}
		return flag != 0;
	}

	public static String getTime(String time){
		String[] str=time.split(" ");
		return str[3];
	}
	
	public static String getSource(String source){
		if(TextUtils.isEmpty(source)){
			return "未知";
		}
		String str=source.substring(source.indexOf(">")+1, source.lastIndexOf("<"));
	   return str;
	}
	
	public static List<Weiboinfo> getWeiboinfos(String result) {
		List<Weiboinfo> ws = new ArrayList<Weiboinfo>();
		try {
			JSONObject jo=new JSONObject(result);
			JSONArray array=jo.getJSONArray("statuses");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jo1=array.getJSONObject(i);
				Weiboinfo info=new Weiboinfo();
				info.setWeibo_id(jo1.getLong("id"));
				info.setWeibo_time(getTime(jo1.getString("created_at")));
				info.setWeibo_text(jo1.getString("text"));
				info.setWeibo_source(getSource(jo1.getString("source")));
				if(jo1.has("thumbnail_pic"))
					info.setWeibo_spic(jo1.getString("thumbnail_pic"));
				if(jo1.has("bmiddle_pic"))
					info.setWeibo_mpic(jo1.getString("bmiddle_pic"));
				if(jo1.has("original_pic"))
					info.setWeibo_bpic(jo1.getString("original_pic"));
				info.setWeibo_zan(jo1.getInt("attitudes_count"));
				info.setWeibo_zhuanfa(jo1.getInt("reposts_count"));
				info.setWeibo_pinglun(jo1.getInt("comments_count"));
				JSONObject jo2=jo1.getJSONObject("user");
				info.setWeibo_user_name(jo2.getString("name"));
				info.setWeibo_user_img(jo2.getString("profile_image_url"));
				ws.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ws;
	}
	
	public static String sendPost(String url, List<String> keys,
			List<String> values) {
		String str = null;
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(url);

		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < keys.size(); i++) {
			parameters.add(new BasicNameValuePair(keys.get(i), values.get(i)));
		}
		try {
			hp.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
			hp.setHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			HttpResponse res = hc.execute(hp);
			str = EntityUtils.toString(res.getEntity(), "UTF-8");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String sendGuanZhuPost(String url, List<String> keys,
			List<String> values) {
		String str = null;
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(url);

		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < keys.size(); i++) {
			parameters.add(new BasicNameValuePair(keys.get(i), values.get(i)));
		}
		try {
			hp.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			HttpResponse res = hc.execute(hp);
			str = EntityUtils.toString(res.getEntity(), "UTF-8");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String sendGet(String url, List<String> keys,
			List<String> values, List<Header> headers) {
		String str = null;
		HttpClient hc = new DefaultHttpClient();
		// 拼接get请求的url
		url = url + "?" + keys.get(0) + "=" + values.get(0);
		for (int i = 1; i < keys.size(); i++) {
			url = url + "&" + keys.get(i) + "=" + values.get(i);
		}
		HttpGet hg = new HttpGet(url);
		if(headers!=null)
			for (int i = 0; i < headers.size(); i++) {
				hg.addHeader(headers.get(i));
			}
		try {
			HttpResponse res = hc.execute(hg);
			str = EntityUtils.toString(res.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static List<Mycomments> parseCommentsJson(String str)
	{    List<Mycomments> comments=new ArrayList<Mycomments>();
		try {
		JSONObject jo=new JSONObject(str);
		JSONArray array=jo.getJSONArray("comments");
		for (int i = 0; i < array.length(); i++) {
			Mycomments comment=new Mycomments();
			JSONObject jo1=array.getJSONObject(i);
			comment.setId(jo1.getLong("id"));
			comment.setTime(getTime(jo1.getString("created_at")));
			comment.setSource(getSource(jo1.getString("source")));
			comment.setComment_text(jo1.getString("text"));
			JSONObject jo2=jo1.getJSONObject("user");
			comment.setUserid(jo2.getLong("id"));
			comment.setUserimg(jo2.getString("profile_image_url"));
			comment.setUsername(jo2.getString("name"));
			JSONObject jo22=jo1.getJSONObject("status");
			comment.setWeibo_text(jo22.getString("text"));
			comments.add(comment);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return comments;
	}

	public static long name2uid(String token, String weibo_user_name) {
		List<String> keys=new ArrayList<String>();
	    keys.add("access_token");
	    keys.add("screen_name");
	    List<String> values=new ArrayList<String>();
	    values.add(token);
	    values.add(weibo_user_name);
		String str=sendGet(Constant.USER_SHOW, keys, values, null);
		try {
			return new JSONObject(str).getLong("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Weiboinfo getWeiboinfo(String sendGet) {
		Weiboinfo info=new Weiboinfo();
		try {
			JSONObject jo1=new JSONObject(sendGet);
				info.setWeibo_id(jo1.getLong("id"));
				info.setWeibo_time(getTime(jo1.getString("created_at")));
				info.setWeibo_text(jo1.getString("text"));
				info.setWeibo_source(getSource(jo1.getString("source")));
				if(jo1.has("thumbnail_pic"))
					info.setWeibo_spic(jo1.getString("thumbnail_pic"));
				if(jo1.has("bmiddle_pic"))
					info.setWeibo_mpic(jo1.getString("bmiddle_pic"));
				if(jo1.has("original_pic"))
					info.setWeibo_bpic(jo1.getString("original_pic"));
				info.setWeibo_zan(jo1.getInt("attitudes_count"));
				info.setWeibo_zhuanfa(jo1.getInt("reposts_count"));
				info.setWeibo_pinglun(jo1.getInt("comments_count"));
				JSONObject jo2=jo1.getJSONObject("user");
				info.setWeibo_user_name(jo2.getString("name"));
				info.setWeibo_user_img(jo2.getString("profile_image_url"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;
	}

	public static List<InfoList> ParseInfoList(String res) {
		List<InfoList> pingluns=new ArrayList<InfoList>();
		try {
			JSONObject jo=new JSONObject(res);
			JSONArray array=jo.getJSONArray("comments");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jo1=array.getJSONObject(i);
				InfoList info=new InfoList();
				info.setInfo_time(getTime(jo1.getString("created_at")));
				info.setInfo_content(jo1.getString("text"));
				JSONObject jo2 = jo1.getJSONObject("user");
				info.setInfo_name(jo2.getString("name"));
				info.setInfo_icon(jo2.getString("profile_image_url"));
				pingluns.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pingluns;
	}

	public static List<User> parseTuijian(String str, int flag) {
		List<User> users=new ArrayList<User>();
		try {
			JSONArray array=new JSONArray(str);
			for (int i = 3*flag; i <= array.length()-1&&i<=3*flag+2; i++) {
				JSONObject jo=array.getJSONObject(i);
				User ui=new User();
				ui.setHead_img(jo.getString("profile_image_url"));
				ui.setUsername(jo.getString("name"));
				ui.setUser_id(jo.getLong("id")+"");
				ui.setDescription(jo.getString("description"));
				users.add(ui);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public static List<MyPhoto> parseJsonMyPhoto(String str) {
		List<MyPhoto> pics=new ArrayList<MyPhoto>();
		try {
			JSONObject jo=new JSONObject(str);
			JSONArray array=jo.getJSONArray("statuses");
			for (int i = 0; i < array.length(); i++) {
				MyPhoto up=new MyPhoto();
				JSONObject jo1=array.getJSONObject(i);
				up.setThumbnailpic(jo1.getString("thumbnail_pic"));
			    up.setBmiddlepic(jo1.getString("bmiddle_pic"));
			    up.setBigpic(jo1.getString("original_pic"));
			    pics.add(up);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pics;
	}

	public static List<Weiboinfo> parseFavoritesJson(String res) {
		List<Weiboinfo> ws = new ArrayList<Weiboinfo>();
		try {
			JSONObject jo=new JSONObject(res);
			JSONArray array=jo.getJSONArray("favorites");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jo0=array.getJSONObject(i);
				JSONObject jo1=jo0.getJSONObject("status");
				Weiboinfo info=new Weiboinfo();
				info.setWeibo_id(jo1.getLong("id"));
				info.setWeibo_time(getTime(jo1.getString("created_at")));
				info.setWeibo_text(jo1.getString("text"));
				info.setWeibo_source(getSource(jo1.getString("source")));
				if(jo1.has("thumbnail_pic"))
					info.setWeibo_spic(jo1.getString("thumbnail_pic"));
				if(jo1.has("bmiddle_pic"))
					info.setWeibo_mpic(jo1.getString("bmiddle_pic"));
				if(jo1.has("original_pic"))
					info.setWeibo_bpic(jo1.getString("original_pic"));
				JSONObject jo2=jo1.getJSONObject("user");
				info.setWeibo_user_name(jo2.getString("name"));
				info.setWeibo_user_img(jo2.getString("profile_image_url"));
				ws.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ws;
	}

}
