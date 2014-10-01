package com.codepath.apps.basictwitter.adapters;

import java.util.List;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.Utils;
import com.codepath.apps.basictwitter.R.id;
import com.codepath.apps.basictwitter.R.layout;
import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvTimeStamp = (TextView) v.findViewById(R.id.tvTimeStamp);
		TextView tvUserId = (TextView) v.findViewById(R.id.tvUserId);
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvTimeStamp.setText(Utils.getRelativeTimeAgo(tweet.getCretedAt()));
		tvUserId.setText("@" + tweet.getUser().getScreenName());
		return v;
		
	}
}
