package com.codepath.apps.basictwitter.fragments;

import android.os.Bundle;

import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class UserTimeLineFragment extends TweetTimelineFragment {
	private TwitterClient client;
	private String uid = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterClientApp.getRestClient();
	}

	public void populateTimeline(long uid) {
		this.uid = String.valueOf(uid);
		clear();
		loadMore2TimeLine(false);
	}

	@Override
	public void getTimeline(AsyncHttpResponseHandler handler, String since_id,
			String max_id) {
		if (uid.isEmpty()) {
			handler.onFailure(new Throwable(), "Uid not configured");
			return;
		}
		client.getUserTimeline(handler, uid, since_id, max_id);
		return;		
	}
}
