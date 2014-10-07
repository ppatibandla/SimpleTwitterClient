package com.codepath.apps.basictwitter.fragments;


import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class HomeTimeLineFragment extends TweetTimelineFragment {

	@Override
	public void getTimeline(AsyncHttpResponseHandler handler, String since_id,
			String max_id) {
		// TODO Auto-generated method stub
		TwitterClientApp.getRestClient().getHomeTimeline(handler, since_id, max_id);
	}
	
}
