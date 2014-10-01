package com.codepath.apps.basictwitter;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeDialog extends DialogFragment {
	private EditText mEditText;

	public interface ComposeDialogListner {
		void onFinishCompose(String tweet);
	}
	public ComposeDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public static ComposeDialog newInstance(String userName, String userId, String profileUrl) {
		ComposeDialog frag = new ComposeDialog();
		Bundle args = new Bundle();
		args.putString("userName", userName);
		args.putString("userId", userId);
		args.putString("profileUrl", profileUrl);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.compose_tweet, container);
		mEditText = (EditText) view.findViewById(R.id.etComposeTweet);
		/*String title = getArguments().getString("title", "Enter Name");
		getDialog().setTitle(title);*/
		
		TextView tvUserName = (TextView) view.findViewById(R.id.tvUserNameCompose);
		TextView tvUserId = (TextView) view.findViewById(R.id.tvUserIdCompose);
		ImageView ivProfile = (ImageView) view.findViewById(R.id.ivProfileImageCompose);
		
		tvUserName.setText(getArguments().getString("userName"));
		tvUserId.setText("@" + getArguments().getString("userId"));
	
		ivProfile.setImageResource(android.R.color.transparent);
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(getArguments().getString("profileUrl"), ivProfile);
		// Show soft keyboard automatically
		mEditText.requestFocus();
		
		Button btTweet = (Button) view.findViewById(R.id.btTweet);
		Button btCancel = (Button) view.findViewById(R.id.btCancel);
		
		btTweet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tweet = mEditText.getText().toString();
				Toast.makeText(getActivity(), tweet, Toast.LENGTH_SHORT).show();
				((ComposeDialogListner)getActivity()).onFinishCompose(tweet);
				dismiss();
			}
		});
		
		btCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		return view;
	}
	
	public void onClickCancel(View v) {
		Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
		dismiss();
	}
}
