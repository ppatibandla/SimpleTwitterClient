package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONObject;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.adapters.EndlessScrollListener;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

public class TweetsListFragment extends Fragment implements
		TweetArrayAdapter.OnTweetClickListener {
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter atweets;
	private ListView lvTweets;

	// Subclass should override these to implement Endless scrolling and refresh
	public  void onScroll() {};
	public  void onRefresh() {};


	private final int lvTweetsVisibilityThreshold = 10;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		tweets = new ArrayList<Tweet>();
		atweets = new TweetArrayAdapter(getActivity(), tweets, this);

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(atweets);

		lvTweets.setOnScrollListener(new EndlessScrollListener(
				lvTweetsVisibilityThreshold) {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {

				TweetsListFragment.this.onScroll();
			}

		});

		return v;
	}

	public void clear() {
		atweets.clear();
	}

	public void addAll(ArrayList<Tweet> tweets) {
		atweets.addAll(tweets);
	}

	public void insert(Tweet t, int pos) {
		atweets.insert(t, pos);
	}

	@Override
	public void onProfileImageClick(User user) {
		// TODO Auto-generated method stub
		Intent i = new Intent(getActivity(), ProfileActivity.class);
		i.putExtra("profile", user);
		startActivity(i);
	}

}
