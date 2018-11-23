package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class MainMenu extends PirateScreen {
    private Texture title;
    private Texture background;
    private Texture settings;
    private Texture musicOnOff;
    private Texture newGame;
    private Texture loadGame;
    private Texture quickStart;
    private SpriteBatch batch;

    private Matrix4 viewMatrix = new Matrix4();
    private Matrix4 transformMatrix = new Matrix4();


    public MainMenu(Game game) {
        super(game);
        batch = new SpriteBatch();
        title = new Texture(Gdx.files.internal("drawing.jpg"));
        background = new Texture(Gdx.files.internal("background.jpg"));
        newGame = new Texture(Gdx.files.internal("play.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewMatrix.setToOrtho2D(0,0,480,320);
        batch.setProjectionMatrix(viewMatrix);
        batch.setTransformMatrix(transformMatrix);
        batch.begin();
        batch.disableBlending();
        batch.draw(background, 0, 0, 480, 320, 0, 0, 1920, 1080, false, false);
        batch.enableBlending();
        batch.draw(title, 100, 320-128, 480-200, 128, 0, 0, 941, 171, false, false);
        batch.draw(newGame, 160, 320 - 256, 160, 40, 0, 0, 343, 147, false, false);
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.end();
    }

    @Override
    public void hide() {

    }
}
