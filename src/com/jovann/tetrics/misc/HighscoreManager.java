package com.jovann.tetrics.misc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.jovann.tetrics.model.Score;

public class HighscoreManager {

	private static final String HIGHSCORE_PREFS = "highScore";
	private static final String HIGHSCORE_LINES = "lines";
	private static final String HIGHSCORE_LEVEL = "level";

	public static void setHighScore(Context context, Score score) {
		SharedPreferences settings = context.getSharedPreferences(HIGHSCORE_PREFS, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(HIGHSCORE_LINES, score.getLines());
		editor.putInt(HIGHSCORE_LEVEL, score.getLevel());
		editor.commit();
	}

	public static Score getHighScore(Context context) {
		Score highScore = new Score();

		SharedPreferences settings = context.getSharedPreferences(HIGHSCORE_PREFS, 0);
		highScore.setLines(settings.getInt(HIGHSCORE_LINES, 0));
		highScore.setLevel(settings.getInt(HIGHSCORE_LEVEL, 0));

		return highScore;
	}

	public static boolean checkNewScore(Context context, Score newScore) {
		Score currentHighScore = getHighScore(context);

		if (currentHighScore.getLines() < newScore.getLines()) {
			Log.d("Score", "new high score:" + newScore.getLines() + ", level" + newScore.getLevel());
			setHighScore(context, newScore);
			return true;
		}

		return false;
	}
}
