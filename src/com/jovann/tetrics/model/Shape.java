package com.jovann.tetrics.model;

import static com.jovann.tetrics.Constants.LEFTL;
import static com.jovann.tetrics.Constants.LEFTS;
import static com.jovann.tetrics.Constants.RIGHTL;
import static com.jovann.tetrics.Constants.RIGHTS;
import static com.jovann.tetrics.Constants.ROD;
import static com.jovann.tetrics.Constants.SQUARE;
import static com.jovann.tetrics.Constants.TEE;

import java.util.ArrayList;

import com.jovann.tetrics.misc.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;

public class Shape {
	private ArrayList<Block> blocks;
	
	public int pivotX;
	public int pivotY;
	private int type;
	
	public void init(int x1, int y1,
				 int x2, int y2,
				 int x3, int y3,
				 int x4, int y4,
				 int pivotX, int pivotY,
				 Bitmap bitmap) {	
		blocks = new ArrayList<Block>();
		
		blocks.add(new Block(x1, y1, bitmap));
		blocks.add(new Block(x2, y2, bitmap));
		blocks.add(new Block(x3, y3, bitmap));
		blocks.add(new Block(x4, y4, bitmap));
		
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}
	
	public Shape(Context context, int type, int blockHeight, int blockWidth) {
		Bitmap bmp;
		
		try {
			this.type = type;
			bmp = BitmapUtil.getShapeBitmap(context, type, blockHeight, blockWidth);
			switch (type) {
				case RIGHTL:
					init(4,19, 5,19, 5,20, 5,21, 4,20, bmp);
					break;
				case LEFTL:
					init(4,19, 4,20, 4,21, 5,19, 5,20, bmp);
					break;
				case SQUARE:
					init(4,19, 5,19, 4,20, 5,20, 4,20, bmp);
					break;
				case TEE:
					init(3,19, 4,19, 5,19, 4,20, 4,20, bmp);
					break;
				case ROD:
					init(4,19, 4,20, 4,21, 4,22, 5,20, bmp);
					break;
				case LEFTS:
					init(4,19, 4,20, 5,20, 5,21, 4,20, bmp);
					break;
				case RIGHTS:
					init(4,21, 4,20, 5,20, 5,19, 4,20, bmp);
					break;
			}
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		}
	}
	
	public int getType() {
		return type;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public void rotateCounter() {
		/* Rotate around the pivot point. Angle +90 degrees. */
		
		
		for (Block block:blocks) {
			int oldX = pivotX - block.getPosX();
			int oldY = pivotY - block.getPosY();
			
			/* x' = xcos(90) - ysin(90)
			 * y' = xsin(90) - xcos(90)
			 */
			block.setPosX(pivotX - oldY);
			block.setPosY(pivotY + oldX);
		}
	}
	
	public void rotateClockwise() {
		for (Block block:blocks) {
			int oldX = pivotX - block.getPosX();
			int oldY = pivotY - block.getPosY();
			
			/* x' = xcos(90) - ysin(90)
			 * y' = xsin(90) - xcos(90)
			 */
			block.setPosX(pivotX + oldY);
			block.setPosY(pivotY - oldX);
		}
	}
}
