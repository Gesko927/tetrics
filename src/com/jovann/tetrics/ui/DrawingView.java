package com.jovann.tetrics.ui;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jovann.tetrics.R;
import com.jovann.tetrics.model.Block;
import com.jovann.tetrics.model.Shape;

public class DrawingView extends SurfaceView implements SurfaceHolder.Callback {

	private static final int ROWS = 25;
	private static final int COLUMNS = 10;
	private static final int NUM_SHAPES = 7;
	private static final int SIDE_LEFT = 1;
	private static final int SIDE_RIGHT = 2;
	private static final int MOVEMENT_THRESHOLD = 200;
	private static final int RELEASE_SHAPE_THRESHOLD = 400;
	private static final long START_TIMEOUT = 1000;
	private static final int LEVEL_SPEED_GAIN_VAL = 100;

	private DrawingThread thread;

	private Block boardBlocks[][] = new Block[ROWS][COLUMNS];
	private boolean boardBlocksFull[][] = new boolean[ROWS][COLUMNS];

	// private ArrayList<Block> boardBlocks;
	private int gameHeight;
	private int gameWidth;
	private int blockHeight;
	private int blockWidth;

	private Handler movementHandler;
	private Shape currentShape;

	private BitmapDrawable background;

	private boolean gameOver = false;

	private int lines = 0;
	private int level = 1;
	private int levelSpeed = 600;

	public DrawingView(Context context) {
		super(context);

		movementHandler = new Handler();
		movementHandler.removeCallbacks(movementTask);
		movementHandler.postDelayed(movementTask, START_TIMEOUT);

		getHolder().addCallback(this);
		thread = new DrawingThread(getHolder(), this);

		initBoard();
	}

	private void initBackground() {
		background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.background_tile));
		background.setBounds(0, 0, gameWidth, gameHeight);
		background.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
	}

	private void initBoard() {
		for (int row = 0; row < ROWS; row++) {
			for (int column = 0; column < COLUMNS; column++) {
				boardBlocksFull[row][column] = false;
				boardBlocks[row][column] = new Block(column, row);
			}
		}
	}

	private Runnable movementTask = new Runnable() {
		public void run() {

			moveShapeDown();
			if (!gameOver)
				movementHandler.postDelayed(this, levelSpeed);
		}
	};

	private boolean moveShapeDown() {
		if (!moveColisionBottom()) {
			for (Block block : currentShape.getBlocks()) {
				block.setPosY(block.getPosY() - 1);
			}
			currentShape.pivotY = currentShape.pivotY - 1;
			return true;
		} else {
			for (Block block : currentShape.getBlocks()) {
				int posX = block.getPosX();
				int posY = block.getPosY();

				boardBlocksFull[posY][posX] = true;
				boardBlocks[block.getPosY()][block.getPosX()].setBitmap(block.getBitmap());
			}

			removeFullLines();

			currentShape = new Shape(getContext(), new Random().nextInt(NUM_SHAPES), blockHeight, blockWidth);

			if (rotationColision()) {
				endGame();
			}

			return false;
		}
	}

	private void printBoard() {
		for (int row = ROWS - 1; row >= 0; row--) {
			StringBuilder rowString = new StringBuilder();
			for (int column = 0; column < COLUMNS; column++) {
				rowString.append("").append(boardBlocksFull[row][column] == true ? "1" : "0").append(" ");
			}
		}
	}

	private void endGame() {
		movementHandler.removeCallbacks(movementTask);
		gameOver = true;
		printBoard();
		thread.setRunning(false);
		thread.stop();
	}

	private void removeFullLines() {
		for (int row = 0; row < ROWS; row++) {
			boolean lineFull = true;

			for (int column = 0; column < COLUMNS; column++) {
				if (!boardBlocksFull[row][column] == true) {
					lineFull = false;
					break;
				}
			}

			if (lineFull) {
				for (int k = row; k < ROWS - 1; k++) {
					for (int column = 0; column < COLUMNS; column++) {
						boardBlocks[k][column] = boardBlocks[k + 1][column];
						boardBlocksFull[k][column] = boardBlocksFull[k + 1][column];
					}
				}
				lines++;
				if (lines % 5 == 0) {
					if (level < 10) {
						level++;
					}
					levelSpeed = levelSpeed - LEVEL_SPEED_GAIN_VAL / level;
				}

				row--; // After the rows are lowered, check if the current row
						// is full again
			}
		}
	}

	private boolean moveColisionBottom() {
		for (Block block : currentShape.getBlocks()) {
			int neighborX = block.getPosX();
			int neighborY = block.getPosY() - 1;

			if (neighborY < 0 || boardBlocksFull[neighborY][neighborX] == true)
				return true;
		}
		return false;
	}

	private boolean sideCollision(int side) {
		boolean collision = false;
		int neighborOffset = 0;

		if (side == SIDE_LEFT) {
			neighborOffset = -1;
		} else {
			neighborOffset = +1;
		}

		for (Block block : currentShape.getBlocks()) {
			int neighborX = block.getPosX() + neighborOffset;
			int neighborY = block.getPosY();

			boolean inBounds = (neighborX < COLUMNS && neighborX >= 0);
			if (!inBounds || boardBlocksFull[neighborY][neighborX] == true) {
				collision = true;
				break;
			}

		}

		return collision;
	}

	private void moveShapeLeft() {
		if (!sideCollision(SIDE_LEFT)) {
			moveShape(SIDE_LEFT);
		}
	}

	private void moveShapeRight() {
		if (!sideCollision(SIDE_RIGHT)) {
			moveShape(SIDE_RIGHT);
		}
	}

	private void moveShape(int side) {
		int movePosition = 0;
		if (side == SIDE_LEFT) {
			movePosition = -1;
		} else {
			movePosition = 1;
		}

		for (Block block : currentShape.getBlocks()) {
			block.setPosX(block.getPosX() + movePosition);
		}
		currentShape.pivotX = currentShape.pivotX + movePosition;
	}

	private boolean rotationColision() {
		for (Block block : currentShape.getBlocks()) {
			if (block.getPosX() >= COLUMNS || block.getPosX() < 0)
				return true;

			if (block.getPosY() < 0)
				return true;

			if (boardBlocksFull[block.getPosY()][block.getPosX()] == true) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);

		drawBackground(canvas);
		drawScore(canvas);
		drawShape(canvas);
		drawBlocks(canvas);

		if (gameOver)
			drawGameoverMessage(canvas);
	}

	private void drawBackground(Canvas canvas) {
		if (background != null) {
			background.draw(canvas);
		}
	}

	private void drawScore(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(14);

		canvas.drawText("Lines:" + lines, (int) (2 * gameWidth) / 3, (int) gameHeight / 15, paint);
		canvas.drawText("Level:" + level, (int) (2 * gameWidth) / 3, (int) gameHeight / 10, paint);
	}

	private void drawGameoverMessage(Canvas canvas) {
		Bitmap gameOverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
		int bitmapWidth = gameOverBitmap.getWidth();
		int bitmapLeft = (gameWidth / 2) - (bitmapWidth / 2);
		int bitmapTop = gameHeight / 2;
		canvas.drawBitmap(gameOverBitmap, bitmapLeft, bitmapTop, null);
	}

	private void drawShape(Canvas canvas) {
		Paint paint = new Paint();

		if (currentShape != null) {
			for (Block block : currentShape.getBlocks()) {
				canvas.drawBitmap(block.getBitmap(), block.getPosX() * blockWidth, gameHeight - block.getPosY()
						* blockHeight - blockHeight, paint);
			}
		}
	}

	private void drawBlocks(Canvas canvas) {
		Paint paint = new Paint();

		for (int row = 0; row < ROWS; row++) {
			for (int column = 0; column < COLUMNS; column++) {
				if (boardBlocksFull[row][column] == true) {
					canvas.drawBitmap(boardBlocks[row][column].getBitmap(), column * blockWidth, gameHeight - row
							* blockHeight - blockHeight, paint);
				}
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		thread.setRunning(true);
		thread.start();

		gameHeight = this.getHeight();
		gameWidth = this.getWidth();

		blockHeight = gameHeight / 20;
		blockWidth = gameWidth / 10;

		currentShape = new Shape(getContext(), new Random().nextInt(7), blockHeight, blockWidth);

		initBackground();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		thread.setRunning(false);

	}

	class DrawingThread extends Thread {
		private SurfaceHolder surfaceHolder;
		private DrawingView drawingView;
		private boolean running = false;

		public DrawingThread(SurfaceHolder sHolder, DrawingView dView) {
			surfaceHolder = sHolder;
			drawingView = dView;
		}

		public void setRunning(boolean run) {
			running = run;
		}

		@Override
		public void run() {
			super.run();
			Canvas c;
			while (running) {
				c = null;
				try {
					c = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder) {
						drawingView.onDraw(c);
					}
				} finally {
					if (c != null) {
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

	public void fling(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (distanceX > MOVEMENT_THRESHOLD) {
			moveShapeRight();
		}
		if (distanceX < -MOVEMENT_THRESHOLD) {
			moveShapeLeft();
		}

		if (distanceY < -MOVEMENT_THRESHOLD) {
			currentShape.rotateClockwise();
			if (rotationColision()) {
				currentShape.rotateCounter();
			}
		}
		if (distanceY > RELEASE_SHAPE_THRESHOLD) {
			while (moveShapeDown())
				;
		}
	}

}