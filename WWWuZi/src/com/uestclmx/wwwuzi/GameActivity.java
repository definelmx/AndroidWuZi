package com.uestclmx.wwwuzi;

import com.uestclmx.wwwuzi.ai.AI;
import com.uestclmx.wwwuzi.ai.NoLimitAI;
import com.uestclmx.wwwuzi.gamerule.GameRule;
import com.uestclmx.wwwuzi.gameviews.ChessboardView;
import com.uestclmx.wwwuzi.gameviews.ChessboardView.ChessboardBlock;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GameActivity extends Activity {

	public GameRule rule;
	public ChessboardView chess;
	public AI ai;
	private View blackview, whiteview;

	private void changeturn(int turn) {
		if (turn == 0) {
			whiteview.setVisibility(View.INVISIBLE);
			blackview.setVisibility(View.VISIBLE);
		} else if (turn == 1) {
			whiteview.setVisibility(View.VISIBLE);
			blackview.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 0Ϊ����ai��ս��1Ϊ˫�˱��ض�ս
	 */
	public int mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		rule = new GameRule();
		chess = (ChessboardView) this.findViewById(R.id.chessboard);
		chess.setRule(rule);
		chess.setChildOnClickListener(new MyOnClickListener());
		blackview = this.findViewById(R.id.blackturn);
		whiteview = this.findViewById(R.id.whiteturn);
		// ����aiΪ�׷�
		String st = this.getIntent().getStringExtra(MainActivity.MODE);
		if (st.equals(MainActivity.SINGLEGAME)) {
			mode = 0;
			ai = new NoLimitAI(rule);
		} else if (st.equals(MainActivity.PLAYERGAME)) {
			mode = 1;
		}
	}

	public void cancel(View v) {
		if (mode == 0 && ai.getPlayer() == 0 && rule.history.size() == 1)
			return;
		rule.cancel();
		if (mode == 0) {
			rule.cancel();
		}
		changeturn(rule.turn);
		chess.refresh();
	}

	public void reset(View v) {
		rule.init();
		changeturn(rule.turn);
		chess.refresh();
	}

	public class MyOnClickListener implements OnClickListener {

		MyOnClickListener() {
		}

		@Override
		public void onClick(View v) {
			ChessboardBlock view = (ChessboardBlock) v;
			/*
			 * ���Ĳ���1��ʾ��ȷ�ϣ�0��ʾ��Ҫȷ��
			 */
			int temp = rule.putchess(view.y, view.x, rule.turn, 0);
			if (temp == 2) {
				/*
				 * �ж�ʤ��
				 */
				if (rule.iswin(view.y, view.x, rule.turn)) {
					new AlertDialog.Builder(GameActivity.this).setTitle(
							"" + (rule.turn == 0 ? "��" : "��") + "����ʤ").show();
					rule.init();
					return;
				}
				rule.turn = (rule.turn + 1) & 1;
				changeturn(rule.turn);
				if (mode == 0) {
					ai.next();
					/*
					 * �ж�ʤ��
					 */
					if (rule.iswin(rule.history.get(rule.history.size() - 1).y,
							rule.history.get(rule.history.size() - 1).x,
							rule.turn)) {
						new AlertDialog.Builder(GameActivity.this).setTitle(
								"" + (rule.turn == 0 ? "��" : "��") + "����ʤ")
								.show();
						rule.init();
						changeturn(rule.turn);
						return;
					}
					rule.turn = (rule.turn + 1) & 1;
					changeturn(rule.turn);
				}
			} else {
			}
			chess.refresh();
		}
	}
}
