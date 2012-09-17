package com.jovann.tetrics.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.jovann.tetrics.R;
import com.jovann.tetrics.misc.HighscoreManager;
import com.jovann.tetrics.model.Score;

public class HighscoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_highscore);

		initViews();
	}

	private void initViews() {
		Score highScore = HighscoreManager.getHighScore(this);

		if (highScore.getLevel() != 0) {
			((TextView) findViewById(R.id.highscore_level)).setText(Integer.valueOf(highScore.getLevel()).toString());
			((TextView) findViewById(R.id.highscore_lines)).setText(Integer.valueOf(highScore.getLines()).toString());
		}
	}
}
