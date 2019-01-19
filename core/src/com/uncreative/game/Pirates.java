package com.uncreative.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import screens.MainMenu;
import screens.deathScreen;

public class Pirates extends Game {
    public enum dir {
        N, E, S, W
    }
    public static Location[][] map;
    private static float volume;
    public static int w;
    public static int h;
    public static Music music;
    public static Game game;

    public Pirates() {
        this.game = this;
    }

    @Override
    public void create() {
        Pirates.h = Gdx.app.getGraphics().getHeight();
        Pirates.w = Gdx.app.getGraphics().getWidth();
        volume = 0f;
        music = Gdx.audio.newMusic(Gdx.files.internal("the-buccaneers-haul.mp3"));
        music.setLooping(true);
        music.setVolume(volume);
        music.play();
        setScreen(new MainMenu(this));
    }

    public static void setVolume(float x) {
        Pirates.volume = x;
        music.setVolume(x);
    }

    public static float getVolume() { return Pirates.volume; }
}
