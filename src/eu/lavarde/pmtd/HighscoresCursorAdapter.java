package eu.lavarde.pmtd;

import java.text.SimpleDateFormat;

import eu.lavarde.db.ChallengesDbAdapter;
import eu.lavarde.db.HighscoresDbAdapter;
import eu.lavarde.db.UsersDbAdapter;
import android.content.Context;
import android.database.Cursor;
import java.text.DateFormat;
import java.util.Date;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class HighscoresCursorAdapter extends CursorAdapter {
	private final LayoutInflater mInflater;
	private final int rowLayout;
	private ChallengesDbAdapter challengeDbA;
	private UsersDbAdapter userDbA;
	
	// View's fields
	private TextView position;
	private TextView challenge;
	private TextView user;
	private TextView score;
	private TextView when;
	
	public HighscoresCursorAdapter(Context context, Cursor c, int layout, ChallengesDbAdapter challengeDbA, UsersDbAdapter userDbA) {
		super(context, c, false);
	    mInflater = LayoutInflater.from(context);
	    rowLayout = layout;
		this.challengeDbA = challengeDbA;
		this.userDbA = userDbA;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		position.setText(String.valueOf(cursor.getPosition()+1)); // getPosition is zero-based, where we want to show 1-based
		challenge.setText(challengeDbA.fetchChallengeName(cursor.getLong(cursor.getColumnIndex(HighscoresDbAdapter.KEY_CHALLENGE))));
		user.setText(userDbA.fetchUserName(cursor.getLong(cursor.getColumnIndex(HighscoresDbAdapter.KEY_USER))));
		score.setText(cursor.getString(cursor.getColumnIndex(HighscoresDbAdapter.KEY_SCORE)));
		DateFormat df = SimpleDateFormat.getDateTimeInstance();
		when.setText(df.format(new Date(cursor.getLong(cursor.getColumnIndex(HighscoresDbAdapter.KEY_WHEN))*1000))); // seconds into milliseconds
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup parent) {
		final View view = mInflater.inflate(rowLayout, parent, false);
		// Initialise View's fields
		position = (TextView) view.findViewById(R.id.place_field);
		challenge = (TextView) view.findViewById(R.id.challenge_field);
		user = (TextView) view.findViewById(R.id.user_field);
		score = (TextView) view.findViewById(R.id.score_field);
		when = (TextView) view.findViewById(R.id.when_field);
		return view;
	}

}