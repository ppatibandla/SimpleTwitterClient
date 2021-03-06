package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.util.jar.Attributes.Name;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.SerializableCookie;

public class User implements Serializable {
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String backgroundColor;
	private String tagline;
	private int followerCount;
	private int followingCount;
	private int tweetCount;

	public String getTagline() {
		return tagline;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public static User fromJson(JSONObject json) {
		User user = new User();
		try {
			user.name = json.getString("name").trim();
			user.uid = json.getLong("id");
			user.screenName = json.getString("screen_name").trim();
			user.profileImageUrl = json.getString("profile_image_url");
			user.backgroundColor = json.getString("profile_background_color");
			user.tagline = json.getString("description");
			user.followerCount = json.getInt("followers_count");
			user.followingCount = json.getInt("friends_count");	
			user.tweetCount = json.getInt("statuses_count");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public int getTweetCount() {
		return tweetCount;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
