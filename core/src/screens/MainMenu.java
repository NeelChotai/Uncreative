package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.uncreative.game.Pirates;

public class MainMenu extends PirateScreen {
    private Image title;
    private Image background;
    private TextureRegionDrawable newGame;
    private TextureRegionDrawable t_volume;
    private Stage stage;
    private Button b_newGame;
    private Button.ButtonStyle b_newGameStyle;
    private TextButton b_volume;
    private TextButton.TextButtonStyle b_volumeStyle;
    public final Sound ping = Gdx.audio.newSound(Gdx.files.internal("Ping.mp3"));


    public MainMenu(Game game) {
        super(game);

        stage = new Stage();

        title = new Image(new Texture(Gdx.files.internal("drawing.jpg")));
        title.setPosition(Pirates.w/2.0f, Pirates.h*0.9f, Align.center);

        background = new Image(new Texture(Gdx.files.internal("background.jpg")));

        newGame = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play.png"))));
        b_newGameStyle = new Button.ButtonStyle();
        b_newGameStyle.up = newGame;
        b_newGameStyle.down = b_newGameStyle.up;
        b_newGame = new Button(b_newGameStyle);
        b_newGame.setPosition(Pirates.w/2.0f, Pirates.h/2.0f, Align.center);
        b_newGame.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               Pirates.game.setScreen(new GameScreen(Pirates.game));
           }
        });

        t_volume = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("volume.png"))));
        b_volumeStyle = new TextButton.TextButtonStyle();
        b_volumeStyle.up = t_volume;
        b_volumeStyle.down = t_volume;
        b_volumeStyle.font = new BitmapFont();
        b_volume = new TextButton("", b_volumeStyle);
        if(Pirates.getVolume() == 0f) {
            b_volume.setText("_____________");
        }
        b_volume.getLabel().setColor(255f, 0f, 0f, 1f);
        b_volume.getLabel().setFontScale(5f);
        b_volume.setPosition(0, 0, Align.bottomLeft);
        b_volume.setTransform(true);
        b_volume.setScale(0.15f);
        b_volume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pirates.setVolume(Pirates.getVolume() + 0.1f);
                if(Pirates.getVolume() < 0.5f) {
                    b_volume.setText("");
                } else {
                    b_volume.setText("_____________");//muted
                    Pirates.setVolume(0f);
                }
            }
        });

        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(b_newGame);
        stage.addActor(b_volume);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        ping.dispose();
        stage.dispose();
    }
}
