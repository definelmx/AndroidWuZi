package com.uestclmx.wwwuzi.gamerule;

/**
 * �����߼���ͼ
 * 
 * @author ����
 * 
 */
public class GameMap {
	public final static int CHESSBOARD_SIZE = 15;
	public final static int NONE = 0, BLACK = 1, BLACK_UN = 2, WHITE = 3,
			WHITE_UN = 4;

	public int[][] board;

	public int[][] value;

	public GameMap() {
		board = new int[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
		value = new int[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
		clear();
	}

	public void clear() {
		int i, j;
		for (i = 0; i < CHESSBOARD_SIZE; i++)
			for (j = 0; j < CHESSBOARD_SIZE; j++) {
				board[i][j] = 0;
				value[i][j] = 0;
			}
	}

	public void clearvalue() {
		int i, j;
		for (i = 0; i < CHESSBOARD_SIZE; i++)
			for (j = 0; j < CHESSBOARD_SIZE; j++) {
				value[i][j] = 0;
			}
	}

}
