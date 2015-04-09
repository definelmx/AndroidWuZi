package com.uestclmx.wwwuzi.gameviews;

import com.uestclmx.wwwuzi.R;
import com.uestclmx.wwwuzi.gamerule.GameMap;
import com.uestclmx.wwwuzi.gamerule.GameRule;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ChessboardView extends ViewGroup {

	/**
	 * �������̵�ÿһ������
	 * 
	 * ʵ������Ҫ����x��yλ��
	 * 
	 * @author ����
	 * 
	 */
	public class ChessboardBlock extends RelativeLayout {

		public ChessboardBlock(Context context) {
			super(context);
			init(context);
		}

		public ChessboardBlock(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}

		public ChessboardBlock(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init(context);
		}

		/**
		 * ��¼λ��
		 */
		public int x, y;

		public void setXY(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void setXY(int order) {
			x = order % GameMap.CHESSBOARD_SIZE;
			y = order / GameMap.CHESSBOARD_SIZE;
		}

		/**
		 * ����������������
		 */
		private View content;

		/**
		 * ��ǩ
		 */
		private View target;

		/**
		 * ����
		 */
		private View chess;

		/**
		 * ��ʼ��
		 * 
		 * @param context
		 */
		private void init(Context context) {
			content = View.inflate(context, R.layout.tools_chessboardblock,
					null);
			this.addView(content);
			target = content.findViewById(R.id.target);
			chess = content.findViewById(R.id.chess);
		}

		/**
		 * ����ѡ��Ч���Ƿ����
		 */
		public void setSelected(boolean sel) {
			if (sel) {
				target.setVisibility(View.VISIBLE);
			} else {
				target.setVisibility(View.INVISIBLE);
			}
		}

		/**
		 * ��������
		 * 
		 * @param state
		 *            1��2��͸3��4��͸
		 */
		public void setState(int state) {
			switch (state) {
			case GameMap.BLACK:
				chess.setBackgroundResource(R.drawable.chessblack);
				break;
			case GameMap.BLACK_UN:
				chess.setBackgroundResource(R.drawable.chessblack_trans);
				break;
			case GameMap.WHITE:
				chess.setBackgroundResource(R.drawable.chesswhite);
				break;
			case GameMap.WHITE_UN:
				chess.setBackgroundResource(R.drawable.chesswhite_trans);
				break;
			default:
				chess.setBackgroundColor(Color.TRANSPARENT);
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getPointerCount() == 1) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getX(0) < 0 || event.getX(0) >= this.getWidth()
							|| event.getY(0) < 0
							|| event.getY(0) >= this.getHeight()) {
						scroll = true;
					}
				}
			}
			return super.onTouchEvent(event);
		}
	}

	public ChessboardView(Context context) {
		super(context);
		background = null;
		init(context);
	}

	public ChessboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int i;
		for (i = 0; i < attrs.getAttributeCount(); i++) {
			if (attrs.getAttributeName(i).equals("background")) {
				break;
			}
		}
		background = new View(context);
		background.setBackgroundResource(Integer.parseInt(attrs
				.getAttributeValue(i).substring(1)));
		init(context);
	}

	final static int CHESSBOARD_HEIGHT = 80;

	/**
	 * ��������
	 */
	public ChessboardBlock blocks[];

	public GameRule rule;

	public Context context;

	private View background;

	/**
	 * �����ж��Ƿ��϶��Խ������ض���
	 */
	public boolean scroll;

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 */
	private void init(Context context) {
		scroll = false;
		this.context = context;
		if (background != null)
			this.addView(background);
		this.blocks = new ChessboardBlock[GameMap.CHESSBOARD_SIZE
				* GameMap.CHESSBOARD_SIZE];
		for (int i = 0; i < blocks.length; i++) {
			blocks[i] = new ChessboardBlock(context);
			blocks[i].setXY(i);
			blocks[i].setBackgroundResource(R.drawable.chessboardcell);
			this.addView(blocks[i]);
		}
	}

	/**
	 * �������Ӹ��ӵ���¼�
	 * 
	 * @param onClickListener
	 */
	public void setChildOnClickListener(OnClickListener onClickListener) {
		for (int i = 0; i < blocks.length; i++) {
			blocks[i].setOnClickListener(onClickListener);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (background != null) {
			background.measure(
					MeasureSpec.makeMeasureSpec(CHESSBOARD_HEIGHT
							* GameMap.CHESSBOARD_SIZE, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(CHESSBOARD_HEIGHT
							* GameMap.CHESSBOARD_SIZE, MeasureSpec.EXACTLY));
		}
		for (int i = 0; i < blocks.length; i++) {
			blocks[i].measure(MeasureSpec.makeMeasureSpec(CHESSBOARD_HEIGHT,
					MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
					CHESSBOARD_HEIGHT, MeasureSpec.EXACTLY));
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		r -= l;
		b -= t;
		l = 0;
		t = 0;
		if (background != null) {
			background.layout(l, t, background.getMeasuredWidth(),
					background.getMeasuredHeight());
		}
		for (int i = 0; i < blocks.length; i++) {
			blocks[i]
					.layout(CHESSBOARD_HEIGHT * (i % GameMap.CHESSBOARD_SIZE),
							CHESSBOARD_HEIGHT * (i / GameMap.CHESSBOARD_SIZE),
							CHESSBOARD_HEIGHT * (i % GameMap.CHESSBOARD_SIZE)
									+ CHESSBOARD_HEIGHT, CHESSBOARD_HEIGHT
									* (i / GameMap.CHESSBOARD_SIZE)
									+ CHESSBOARD_HEIGHT);
		}
	}

	private int posx = -1, posy = -1;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scroll) {
			return true;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getPointerCount() == 1) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (posx == -1 && posy == -1) {
					posx = (int) event.getX(0);
					posy = (int) event.getY(0);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (posx == -1 && posy == -1) {
					posx = (int) event.getX(0);
					posy = (int) event.getY(0);
				} else if (Math.abs(posx - event.getX(0)) > 5
						|| Math.abs(posy - event.getY(0)) > 5) {
					// �趨�߽磺���ܻ�������
					int tx = (int) (posx - event.getX(0) + this.getScrollX()), ty = (int) (posy
							- event.getY(0) + this.getScrollY());
					if (tx + this.getWidth() >= CHESSBOARD_HEIGHT
							* GameMap.CHESSBOARD_SIZE)
						tx = CHESSBOARD_HEIGHT * GameMap.CHESSBOARD_SIZE
								- this.getWidth() - 1;
					if (ty + this.getHeight() >= CHESSBOARD_HEIGHT
							* GameMap.CHESSBOARD_SIZE)
						ty = CHESSBOARD_HEIGHT * GameMap.CHESSBOARD_SIZE
								- this.getHeight() - 1;
					if (tx < 0)
						tx = 0;
					if (ty < 0)
						ty = 0;
					this.scrollTo(tx, ty);
					// this.scrollBy((int) (posx - event.getX(0)),
					// (int) (posy - event.getY(0)));
					posx = (int) event.getX(0);
					posy = (int) event.getY(0);
				}
				break;
			case MotionEvent.ACTION_UP:
				posx = -1;
				posy = -1;
				scroll = false;
				break;
			case MotionEvent.ACTION_CANCEL:
				posx = -1;
				posy = -1;
				scroll = false;
				break;
			}
		}
		return true;
	}

	/**
	 * ����ruleˢ��
	 */
	public void refresh() {
		int i, j;
		for (i = 0; i < GameMap.CHESSBOARD_SIZE; i++)
			for (j = 0; j < GameMap.CHESSBOARD_SIZE; j++) {
				blocks[i * GameMap.CHESSBOARD_SIZE + j]
						.setState(rule.map.board[i][j]);
				blocks[i * GameMap.CHESSBOARD_SIZE + j].setSelected(false);
			}
		if (rule.history.size() > 1) {
			blocks[rule.history.get(rule.history.size() - 1).y
					* GameMap.CHESSBOARD_SIZE
					+ rule.history.get(rule.history.size() - 1).x]
					.setSelected(true);
			blocks[rule.history.get(rule.history.size() - 2).y
					* GameMap.CHESSBOARD_SIZE
					+ rule.history.get(rule.history.size() - 2).x]
					.setSelected(true);
		} else if (rule.history.size() == 1) {
			blocks[rule.history.get(rule.history.size() - 1).y
					* GameMap.CHESSBOARD_SIZE
					+ rule.history.get(rule.history.size() - 1).x]
					.setSelected(true);
		}
	}

	/**
	 * ���ù���ʵ��
	 * 
	 * @param rule
	 */
	public void setRule(GameRule rule) {
		this.rule = rule;
	}

}