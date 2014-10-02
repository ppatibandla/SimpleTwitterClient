package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.ComposeDialog.ComposeDialogListner;
import com.codepath.apps.basictwitter.adapters.EndlessScrollListener;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends FragmentActivity implements
		ComposeDialogListner {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter atweets;
	private ListView lvTweets;
	private final int lvTweetsVisibilityThreshold = 10;
	private User user = null;

	private SwipeRefreshLayout swipeContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		client = TwitterClientApp.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		atweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(atweets);
		populateUserInfo();
		populateTimeline();
		lvTweets.setOnScrollListener(new EndlessScrollListener(
				lvTweetsVisibilityThreshold) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Toast.makeText(TimeLineActivity.this, "OnScrollListner",
				//		Toast.LENGTH_SHORT).show();
				loadMore2TimeLine();
			}

		});

		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Toast.makeText(TimeLineActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
				// Your code to refresh the list here.
				// Make sure you call swipeContainer.setRefreshing(false)
				// once the network request has completed successfully.
				populateTimeline();
			}
		});
		// Configure the refreshing colors
		swipeContainer.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

	}

	private void populateUserInfo() {
		// TODO Auto-generated method stub
		client.getUserInfo(new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
			}

			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				user = User.fromJson(json);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.twitter_menu, menu);
		return true;
	}

	public void populateTimeline() {

		Tweet.max_id = Tweet.INVALID_ID;
		Tweet.since_id = Tweet.INVALID_ID;
		atweets.clear();
		
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
		if (Tweet.max_id != Tweet.INVALID_ID) {
			max_id = String.valueOf(Tweet.max_id - 1);
		}

		//Toast.makeText(this, "loadMore2TimeLine", Toast.LENGTH_SHORT).show();
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("TimelineOnFailure", e.toString());
				Log.d("TimelineOnFailure", s);
				swipeContainer.setRefreshing(false);
				// TODO Auto-generated method stub
				super.onFailure(e, s);
			}

			@Override
			public void onSuccess(JSONArray json) {
				//Toast.makeText(TimeLineActivity.this, "loadMore2TimeLine responded", Toast.LENGTH_SHORT).show();
				atweets.addAll(Tweet.fromJsonArray(json));
				swipeContainer.setRefreshing(false);
			}
		}, since_id, max_id);
	}

	public void onComposeClick(MenuItem mi) {
		FragmentActivity fa = (FragmentActivity) this;
		// Get these from "account/verify_credentials.json".
		DialogFragment settingsDialog = ComposeDialog
				.newInstance(user.getName(), user.getScreenName(),
						user.getProfileImageUrl());
		settingsDialog.show(fa.getSupportFragmentManager(),
				"fragment_compose_tweet");
	}

	@Override
	public void onFinishCompose(String tweet) {
		// TODO Auto-generated method stub
		client.postUpdate(new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("postUpdate onFailure", e.toString());
				Log.d("postUpdate onFailure", s);
				// TODO Auto-generated method stub
				super.onFailure(e, s);
			}

			@Override
			public void onSuccess(JSONObject json) {
				Tweet t = Tweet.fromJson(json);
				atweets.insert(t, 0);
				// TODO Auto-generated method stub
				super.onSuccess(json);
			}
		}, tweet);
	}
}
