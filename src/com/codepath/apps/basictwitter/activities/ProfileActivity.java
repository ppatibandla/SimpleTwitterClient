package com.codepath.apps.basictwitter.activities;

import java.util.SimpleTimeZone;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.R.layout;
import com.codepath.apps.basictwitter.fragments.UserTimeLineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.codepath.apps.basictwitter.utils.TwitterClientApp;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends FragmentActivity {

	private User profileUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		profileUser = (User) getIntent().getSerializableExtra("profile");
		
		getActionBar().setTitle("@"+ profileUser.getScreenName());
		populateProfileHeader();
		
		UserTimeLineFragment fg = (UserTimeLineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
		fg.populateTimeline(profileUser.getUid());
		Toast.makeText(this, profileUser.getScreenName(), Toast.LENGTH_SHORT).show();
	}
	private void populateProfileHeader() {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);
		
		tvName.setText(profileUser.getName());
		tvTagline.setText(profileUser.getTagline());
		tvFollowers.setText(profileUser.getFollowerCount() + " Followers");
		tvFollowing.setText(profileUser.getFollowingCount() + " Following");
		ImageLoader.getInstance().displayImage(profileUser.getProfileImageUrl(), ivProfile);
	}
}
