package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TweetsListFragment {
	private TwitterClient client;
	private String uid = "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterClientApp.getRestClient();
//		populateTimeline();
		// TODO Auto-generated method stub
	}
	
	
	public void populateTimeline() {

//		Tweet.max_id = Tweet.INVALID_ID;
//		Tweet.since_id = Tweet.INVALID_ID;
		clear();
		loadMore2TimeLine();

	}

	public void populateTimeline(long uid) {
		this.uid = String.valueOf(uid);
		clear();
		loadMore2TimeLine();
	}
	
	private void loadMore2TimeLine() {
		// TODO Auto-generated method stub

		String since_id = "";
		String max_id = "";

		/*
		 * if (Tweet.since_id != Tweet.INVALID_ID) { since_id =
		 * String.valueOf(Tweet.since_id); }
		 */
		/*
		if (Tweet.max_id != Tweet.INVALID_ID) {
			max_id = String.valueOf(Tweet.max_id - 1);
		}
		*/
		//Toast.makeText(this, "loadMore2TimeLine", Toast.LENGTH_SHORT).show();
		client.getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("TimelineOnFailure", e.toString());
				Log.d("TimelineOnFailure", s);
//				swipeContainer.setRefreshing(false);
				// TODO Auto-generated method stub
				super.onFailure(e, s);
			}

			@Override
			public void onSuccess(JSONArray json) {
				//Toast.makeText(TimeLineActivity.this, "loadMore2TimeLine responded", Toast.LENGTH_SHORT).show();
				addAll(Tweet.fromJsonArray(json));
//				swipeContainer.setRefreshing(false);
			}
		}, uid, since_id, max_id);
	}
}
