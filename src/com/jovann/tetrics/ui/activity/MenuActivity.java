package com.jovann.tetrics.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.jovann.tetrics.R;

public class MenuActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_menu);
		initViews();
	}

	private void initViews() {
		((ImageButton) findViewById(R.id.menu_newgame)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.menu_highscore)).setOnClickListener(this);
		((ImageButton) findViewById(R.id.menu_quit)).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.menu_newgame:
			startActivity(new Intent(this, GameActivity.class));
			break;

		case R.id.menu_highscore:
			startActivity(new Intent(this, HighscoreActivity.class));
			break;

		case R.id.menu_quit:
			finish();
			break;

		}
	}

}
