package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimeLineFragment extends TweetTimelineFragment {
	@Override
	public void getTimeline(AsyncHttpResponseHandler handler, String since_id,
			String max_id) {
		// TODO Auto-generated method stub
		TwitterClientApp.getRestClient().getMentionsTimeline(handler, since_id, max_id);
		
	}
}
