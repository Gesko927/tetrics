package com.jovann.tetrics.ui.activity;

import com.jovann.tetrics.ui.DrawingView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GameActivity extends Activity {

	private DrawingView gameView;
	private GestureDetector gestureDetector;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new DrawingView(GameActivity.this);
        gestureDetector = new GestureDetector(new FlingGestureListener());
        setContentView(gameView);
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}
    
	private class FlingGestureListener extends GestureDetector.SimpleOnGestureListener {
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			Log.d("ASD", "onFling");
			gameView.fling(e1, e2, distanceX, distanceY);
			return true;
		}

	}
}