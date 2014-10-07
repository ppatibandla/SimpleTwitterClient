package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.codepath.apps.basictwitter.utils.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	private String minUid;
	private String maxUid;

	public abstract void getTimeline(AsyncHttpResponseHandler handler, String since_id, String max_id);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateTimeline();
	}

	public void populateTimeline() {
		minUid = "";
		maxUid = "";
		clear();
		loadMore2TimeLine(false);
	}

	protected void loadMore2TimeLine(boolean latest) {
		if (!Utils.isNetworkAvailable(getActivity())) {
			Toast.makeText(getActivity(), "Network not available.",
					Toast.LENGTH_SHORT).show();
			NetworkUnavailableDialog.show(getActivity());
			return;
		}

		String since_id = "";
		String max_id = "";
		final boolean refresh = latest ? true : false;
		if (latest) {
			since_id = maxUid;
		} else {
			max_id = minUid;
		}
		Toast.makeText(getActivity(), "loadMore2TimeLine requesting tweets : ",
				Toast.LENGTH_SHORT).show(); 
		getTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("TimelineOnFailure", e.toString());
				Log.d("TimelineOnFailure", s);
				// swipeContainer.setRefreshing(false);
				// TODO Auto-generated method stub
				super.onFailure(e, s);
			}

			@Override
			public void onSuccess(JSONArray json) {
				ArrayList<Tweet> tweets = Tweet.fromJsonArray(json);
				Toast.makeText(
						getActivity(),
						"loadMore2TimeLine responded , tweets : "
								+ String.valueOf(tweets.size()),
						Toast.LENGTH_SHORT).show();
				addAll(tweets);
				if (tweets.size() > 0) {
					long min_uid = tweets.get(tweets.size() - 1).getUid() - 1;
					long max_uid = tweets.get(0).getUid();
					if (minUid.isEmpty() || Long.valueOf(minUid) > min_uid) {
						minUid = String.valueOf(min_uid);
					}
					if (maxUid.isEmpty() || Long.valueOf(maxUid) < max_uid) {
						maxUid = String.valueOf(max_uid);
					}
				}
				if (refresh) {
					noteRefreshDone();
				}
			}
		}, since_id, max_id);
	}

	@Override
	public void onScrollListner() {
		// TODO Auto-generated method stub
		loadMore2TimeLine(false);

	}

	@Override
	public void onRefreshListner() {
		// TODO Auto-generated method stub
		loadMore2TimeLine(true);
	}
}
