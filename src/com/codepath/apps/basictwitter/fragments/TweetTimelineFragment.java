package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetTimelineFragment extends TweetsListFragment {
	private String minUid;
	private String maxUid;
	private boolean loading = false; 
	public abstract void getTimeline(AsyncHttpResponseHandler handler, String since_id, String max_id);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loading = false;
	}

	public void populateList() {
		minUid = "";
		maxUid = "";
		clear();
		loadMore2TimeLine(false);
	}

	protected boolean loadMore2TimeLine(boolean latest) {
		if (loading) {
			return false;
		}
		loading = true;
		
		String since_id = "";
		String max_id = "";
		final boolean refresh = latest ? true : false;
		if (latest) {
			since_id = maxUid;
		} else {
			max_id = minUid;
		}
		
		if (!Utils.isNetworkAvailable(getActivity())) {
			Toast.makeText(getActivity(), "Network not available.",
					Toast.LENGTH_SHORT).show();
			NetworkUnavailableDialog.show(getActivity());
			if (refresh) {
				noteRefreshDone();
			} else {
				noteScrollDone();
			}
			return false;
		}
		

		Toast.makeText(getActivity(), "loadMore2TimeLine requesting tweets : ",
				Toast.LENGTH_SHORT).show(); 
		getTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("TimelineOnFailure", e.toString());
				Log.d("TimelineOnFailure", s);
				if (refresh) {
					noteRefreshDone();
				} else {
					noteScrollDone();
				}
				loading = false;
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
				} else {
					noteScrollDone();
				}
				loading = false;
			}
		}, since_id, max_id);
		return true;
	}

	@Override
	public boolean onScrollListner() {
		// TODO Auto-generated method stub
		return loadMore2TimeLine(false);

	}

	@Override
	public boolean onRefreshListner() {
		// TODO Auto-generated method stub
		return loadMore2TimeLine(true);
	}
}
