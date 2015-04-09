package com.uestclmx.wwwuzi.ai;

import com.uestclmx.wwwuzi.gamerule.GameRule;

public abstract class AI {
	protected GameRule rule;
	protected int player;

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getPlayer() {
		return player;
	}

	public AI(GameRule rule) {
		this.rule = rule;
		this.player = 1;
	}

	public AI(GameRule rule, int player) {
		this.rule = rule;
		this.player = player;
	}

	abstract public void next();
}
