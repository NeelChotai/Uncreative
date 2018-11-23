package com.uncreative.game;

import com.badlogic.gdx.Game;
import screens.MainMenu;

public class Pirates extends Game {
    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
}
