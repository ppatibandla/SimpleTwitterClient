package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.codepath.apps.basictwitter.utils.Utils;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimeLineFragment extends TweetsListFragment {
	private TwitterClient client;
	private String minUid;
	private String maxUid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterClientApp.getRestClient();

		populateTimeline();

	}

	public void populateTimeline() {
		minUid = "";
		maxUid = "";
		clear();
		loadMore2TimeLine(false);

	}

	private void loadMore2TimeLine(boolean latest) {
		if (!Utils.isNetworkAvailable(getActivity())) {
			Toast.makeText(getActivity(), "Network not available.",
					Toast.LENGTH_SHORT).show();
			NetworkUnavailableDialog.show(getActivity());
			return;
		}
		
		String since_id = "";
		String max_id = "";
		
		if (latest) {
			since_id = maxUid;
		} else {
			max_id = minUid;
		}
		Toast.makeText(getActivity(), "loadMore2TimeLine requesting tweets : ",
				Toast.LENGTH_SHORT).show();
		client.getHomeTimeline(new JsonHttpResponseHandler() {
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
				long min_uid = tweets.get(tweets.size() - 1).getUid() - 1;
				long max_uid = tweets.get(0).getUid();
				if (minUid.isEmpty() || Long.valueOf(minUid) > min_uid) {
					minUid = String.valueOf(min_uid);
				}
				if (maxUid.isEmpty() || Long.valueOf(maxUid) < max_uid) {
					maxUid = String.valueOf(max_uid);
				}
				// swipeContainer.setRefreshing(false);
			}
		}, since_id, max_id);
	}

	@Override
	public void onScroll() {
		// TODO Auto-generated method stub
		loadMore2TimeLine(false);

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		loadMore2TimeLine(true);
	}
}
