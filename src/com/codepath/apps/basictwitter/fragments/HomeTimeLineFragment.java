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
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimeLineFragment extends TweetTimelineFragment {

	@Override
	public void getTimeline(AsyncHttpResponseHandler handler, String since_id,
			String max_id) {
		// TODO Auto-generated method stub
		TwitterClientApp.getRestClient().getHomeTimeline(handler, since_id, max_id);
	}
	
}
