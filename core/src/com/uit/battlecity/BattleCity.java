package com.uit.battlecity;

import com.badlogic.gdx.Game;
import com.uit.battlecity.screens.TitleScreen;

public class BattleCity extends Game {
    private static BattleCity instance;

    public BattleCity() {
        super();
        instance = this;
    }

	@Override
	public void create() {
		this.setScreen(new TitleScreen(this));
	}

    public static BattleCity getInstance() {
        return instance;
    }
}
