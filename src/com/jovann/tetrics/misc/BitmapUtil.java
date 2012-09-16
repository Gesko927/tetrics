package com.jovann.tetrics.misc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jovann.tetrics.Constants;
import com.jovann.tetrics.R;

public class BitmapUtil {

	public static Bitmap getShapeBitmap(Context context, int type, int blockHeight, int blockWidth) throws IllegalArgumentException {
		Bitmap bmp = null;
		
		switch (type) {
			case Constants.RIGHTL:
			case Constants.LEFTL:
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);
				break;
			case Constants.SQUARE:
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);
				break;
			case Constants.TEE:
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.yellow);
				break;
			case Constants.ROD:
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue);
				break;
			case Constants.RIGHTS:
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.purple);
				break;
			case Constants.LEFTS:
				bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.grey);
				break;
		}
		
		if (bmp != null) {
			return Bitmap.createScaledBitmap(bmp, blockWidth, blockHeight, false);
		} else {
			throw new IllegalArgumentException("Couldn't create bitmap from resource");
		}

	}
	
	public static Bitmap getBackgroundBitmap(Context context) {
		return BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
	}
	
}
