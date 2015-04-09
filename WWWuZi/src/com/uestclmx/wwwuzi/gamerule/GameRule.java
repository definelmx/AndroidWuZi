package com.uestclmx.wwwuzi.gamerule;

import java.util.ArrayList;
import java.util.List;

public class GameRule {

	/**
	 * ��ʷ������
	 * 
	 * @author ����
	 * 
	 */
	public class HistoryNode {
		public HistoryNode() {
			x = -1;
			y = -1;
			player = -1;
		}

		public HistoryNode(int y, int x, int player) {
			this.x = x;
			this.y = y;
			this.player = player;
		}

		public int x, y;
		public int player;
	}

	/**
	 * �涨���Ͻ�Ϊ����ԭ��
	 */
	public GameMap map;
	/**
	 * ȷ��ģʽ����������
	 */
	public int lx, ly;
	/**
	 * ��һ���������
	 */
	public int turn = 0;
	/**
	 * ��ʷ����
	 */
	public List<HistoryNode> history;

	public GameRule() {
		map = new GameMap();
		history = new ArrayList<HistoryNode>();
		init();
	}

	/**
	 * ��ʼ���������
	 */
	public void init() {
		history.clear();
		map.clear();
		turn = 0;
		lx = -1;
		ly = -1;
	}

	/**
	 * ĳ��������壬�������Ҫȷ��
	 * 
	 * @param y
	 *            ��ֱ����
	 * @param x
	 *            ˮƽ����
	 * @param player
	 *            �ڷ�Ϊ0���׷�Ϊ1
	 * @param mode
	 *            ģʽ0Ϊ��Ҫȷ�ϣ�1Ϊ����ȷ��
	 * @return ���ӳɹ�����2����ȷ�Ϸ���1��ʧ�ܷ���0
	 */
	public int putchess(int y, int x, int player, int mode) {
		if (mode == 0) {
			switch (map.board[y][x]) {
			case GameMap.NONE:
				if (player == 0) {
					map.board[y][x] = GameMap.BLACK_UN;
					if (ly > -1 && lx > -1
							&& map.board[ly][lx] == GameMap.BLACK_UN) {
						map.board[ly][lx] = GameMap.NONE;
					}
					ly = y;
					lx = x;
				} else if (player == 1) {
					map.board[y][x] = GameMap.WHITE_UN;
					if (ly > -1 && lx > -1
							&& map.board[ly][lx] == GameMap.WHITE_UN) {
						map.board[ly][lx] = GameMap.NONE;
					}
					ly = y;
					lx = x;
				} else
					return 0;
				return 1;
			case GameMap.BLACK_UN:
				if (player == 0) {
					map.board[y][x] = GameMap.BLACK;
					history.add(new HistoryNode(y, x, 0));
					ly = -1;
					lx = -1;
					return 2;
				}
				return 0;
			case GameMap.WHITE_UN:
				if (player == 1) {
					map.board[y][x] = GameMap.WHITE;
					ly = -1;
					lx = -1;
					history.add(new HistoryNode(y, x, 1));
					return 2;
				}
				return 0;
			}
			return 0;
		} else {
			if (map.board[y][x] == 0) {
				if (player == 0) {
					map.board[y][x] = GameMap.BLACK;
					history.add(new HistoryNode(y, x, 0));
				} else if (player == 1) {
					map.board[y][x] = GameMap.WHITE;
					history.add(new HistoryNode(y, x, 1));
				}
				return 2;
			}
		}
		return 0;
	}

	/**
	 * �ж��ڣ�y,x�����������Ƿ�ʤ���������Ӻ���Ч������ע����ֱ����Ϊ0��˳ʱ���������Ϊ3.
	 * 
	 * @param y
	 * @param x
	 * @param turn
	 * @return
	 */
	public boolean iswin(int y, int x, int turn) {
		int player = (turn == 0 ? GameMap.BLACK : GameMap.WHITE), i, j, l;
		// ����
		l = 0;
		i = y;
		j = x;
		while (i-- > 0 && map.board[i][j] == player)
			l++;
		i = y;
		while (++i < GameMap.CHESSBOARD_SIZE && map.board[i][j] == player)
			l++;
		if (l >= 4)
			return true;
		// ��������
		l = 0;
		i = y;
		j = x;
		while (i-- > 0 && ++j < GameMap.CHESSBOARD_SIZE
				&& map.board[i][j] == player)
			l++;
		i = y;
		j = x;
		while (++i < GameMap.CHESSBOARD_SIZE && j-- > 0
				&& map.board[i][j] == player)
			l++;
		if (l >= 4)
			return true;
		// ����
		l = 0;
		i = y;
		j = x;
		while (j-- > 0 && map.board[i][j] == player)
			l++;
		j = x;
		while (++j < GameMap.CHESSBOARD_SIZE && map.board[i][j] == player)
			l++;
		if (l >= 4)
			return true;
		// ��������
		l = 0;
		i = y;
		j = x;
		while (j-- > 0 && i-- > 0 && map.board[i][j] == player)
			l++;
		i = y;
		j = x;
		while (++j < GameMap.CHESSBOARD_SIZE && ++i < GameMap.CHESSBOARD_SIZE
				&& map.board[i][j] == player)
			l++;
		if (l >= 4)
			return true;
		return false;
	}

	public void cancel() {
		if (history.size() > 0) {
			HistoryNode temp = history.get(history.size() - 1);
			if (ly >= 0 && lx >= 0)
				map.board[ly][lx] = GameMap.NONE;
			ly = -1;
			lx = -1;
			turn = temp.player;
			map.board[temp.y][temp.x] = GameMap.NONE;
			history.remove(history.size() - 1);
		}
	}
}