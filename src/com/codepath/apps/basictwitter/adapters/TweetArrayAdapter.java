package com.codepath.apps.basictwitter.adapters;

import java.util.List;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.R.id;
import com.codepath.apps.basictwitter.R.layout;
import com.codepath.apps.basictwitter.activities.ProfileActivity;
import com.codepath.apps.basictwitter.activities.TimeLineActivity;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	private class ViewHolder {
		ImageView ivProfileImage;
		TextView tvUserName;
		TextView tvBody;
		TextView tvTimeStamp;
		TextView tvUserId;
		TextView tvRetweet;
	}
	
	public interface OnTweetClickListener {
		public void onProfileImageClick(User user);
	}
	
	private OnTweetClickListener listener;
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets,
							 OnTweetClickListener l) {
		super(context, 0, tweets);
		listener = l;

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v;
		ViewHolder vh;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
			vh = new ViewHolder();
			vh.ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
			vh.tvUserName = (TextView) v.findViewById(R.id.tvUserName);
			vh.tvBody = (TextView) v.findViewById(R.id.tvBody);
			vh.tvTimeStamp = (TextView) v.findViewById(R.id.tvTimeStamp);
			vh.tvUserId = (TextView) v.findViewById(R.id.tvUserId);
			vh.tvRetweet = (TextView) v.findViewById(R.id.tvRetweet);
			v.setTag(vh);
		} else {
			v = convertView;
			vh = (ViewHolder) v.getTag();
		}
		
		
		
		vh.ivProfileImage.setImageResource(android.R.color.transparent);
		vh.ivProfileImage.setTag(tweet);
		vh.ivProfileImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Tweet t = (Tweet) v.getTag();
				listener.onProfileImageClick(t.getUser());
			}
		});
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(tweet.getUser().getProfileImageUrl(), vh.ivProfileImage);
		vh.tvUserName.setText(tweet.getUser().getName());
		vh.tvBody.setText(tweet.getBody());
		vh.tvTimeStamp.setText(Utils.getRelativeTimeAgo(tweet.getCretedAt()));
		vh.tvUserId.setText("@" + tweet.getUser().getScreenName());
		if (tweet.isRetweeted()) {
			vh.tvRetweet.setText(tweet.getUser().getName() + " retweeted");
			vh.tvRetweet.setVisibility(View.VISIBLE);
		} else {
			vh.tvRetweet.setVisibility(View.INVISIBLE);		
		}
		return v;	
	}
}
