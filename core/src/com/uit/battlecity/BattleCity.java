package com.uit.battlecity;

import com.badlogic.gdx.Game;
import com.uit.battlecity.screens.LevelScreen;

public class BattleCity extends Game {

	@Override
	public void create() {
		setScreen(new LevelScreen(3, false));
	}
}
