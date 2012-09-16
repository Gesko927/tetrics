package com.jovann.tetrics.model;

import android.graphics.Bitmap;

public class Block {
	private int posX;
	private int posY;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	private Bitmap bitmap;
	
	public Block(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	
	public Block(int posX, int posY, Bitmap bitmap) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.bitmap = bitmap;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	@Override
	public String toString() {
		return "(" + posX + "," + posY + ")";
	}
}
