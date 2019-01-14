package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.uncreative.game.Pirates;

public abstract class PirateScreen implements Screen {
    Game game;
    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"),
                                    Gdx.files.internal("font.png"), false);

    public PirateScreen(Game game)
    {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        Pirates.w = width;
        Pirates.h = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
