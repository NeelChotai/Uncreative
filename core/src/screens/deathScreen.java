package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uncreative.game.Pirates;

public class deathScreen extends PirateScreen {
    int time;
    SpriteBatch batch;
    int score;

    public deathScreen(Game game, Integer score) {
        super(game);
        this.score = score;
        time = 0;
        batch = new SpriteBatch();

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        super.font.setColor(Color.WHITE);
        super.font.draw(batch, "GAME OVER\nSCORE: " + score, Pirates.w/2f, Pirates.h/2f);
        batch.end();
    }
}
