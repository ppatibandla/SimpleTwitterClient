package com.codepath.apps.basictwitter.activities;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.ComposeDialog;
import com.codepath.apps.basictwitter.ComposeDialog.ComposeDialogListner;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.actionbarcompact.SupportFragmentTabListener;
import com.codepath.apps.basictwitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimeLineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utils.TwitterClient;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends ActionBarActivity implements
		ComposeDialogListner {
	private TwitterClient client;
	private User user = null;
	private TweetsListFragment fragmentTweetsList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		setContentView(R.layout.activity_time_line);
		setupTabs();
		
		client = TwitterClientApp.getRestClient();
		populateUserInfo();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.twitter_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.compose:
	    		FragmentActivity fa = (FragmentActivity) this;
	    		// Get these from "account/verify_credentials.json".
	    		DialogFragment settingsDialog = ComposeDialog
	    				.newInstance(user.getName(), user.getScreenName(),
	    						user.getProfileImageUrl());
	    		settingsDialog.show(fa.getSupportFragmentManager(),
	    				"fragment_compose_tweet");
	            return true;
	            
	        case R.id.profile:
	        	Intent i = new Intent(this, ProfileActivity.class);
	        	i.putExtra("profile", user);
	        	startActivity(i);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void populateUserInfo() {
		// TODO Auto-generated method stub
		client.getMyInfo(new JsonHttpResponseHandler() {

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
	
	private void setupTabs() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		Tab tab1 = actionBar
		    .newTab()
		    .setText("Home")
//		    .setIcon(R.drawable.ic_home_blue)
		    .setTag("HomeTimelineFragment")
		    .setTabListener(new SupportFragmentTabListener<HomeTimeLineFragment>(R.id.flContainer, this,
                        "HomeTimelineFragment", HomeTimeLineFragment.class));
		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
		    .newTab()
		    .setText("Mentions")
//		    .setIcon(R.drawable.ic_mentions)
		    .setTag("MentionsTimelineFragment")
		    .setTabListener(new SupportFragmentTabListener<MentionsTimeLineFragment>(R.id.flContainer, this,
                        "MentionsTimelineFragment", MentionsTimeLineFragment.class));
		actionBar.addTab(tab2);
		Toast.makeText(this, "Created tabs", Toast.LENGTH_SHORT).show();
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
				fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager()
						.findFragmentByTag("HomeTimelineFragment");
				fragmentTweetsList.insert(t, 0);
				// TODO Auto-generated method stub
				super.onSuccess(json);
			}
		}, tweet);
	}
}
