package com.uit.battlecity;

import com.badlogic.gdx.Game;
import com.uit.battlecity.screens.LevelScreen;
import com.uit.battlecity.menu.TitleScreen;

public class BattleCity extends Game {

	@Override
	public void create() {
		this.setScreen(new TitleScreen(this));
	}
}
