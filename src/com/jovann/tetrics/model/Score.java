package com.jovann.tetrics.model;

public class Score {

	private int lines;
	private int level;

	public Score(int lines, int level) {
		super();
		this.lines = lines;
		this.level = level;
	}

	public Score() {
		this.lines = 0;
		this.level = 0;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
