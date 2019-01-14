package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.uncreative.game.Pirates;

public class MainMenu extends PirateScreen {
    private Image background;
    private TextureRegionDrawable newGame;
    private TextureRegionDrawable t_volume;
    private Stage stage;
    private TextButton b_newGame;
    private TextButton.TextButtonStyle b_newGameStyle;
    private TextButton b_volume;
    private TextButton.TextButtonStyle b_volumeStyle;
    public final Sound ping = Gdx.audio.newSound(Gdx.files.internal("Ping.mp3"));
    private BitmapFont font = super.font;
    private Label title;
    private Label.LabelStyle titleStyle;

    public MainMenu(Game game) {
        super(game);

        stage = new Stage();

        background = new Image(new Texture(Gdx.files.internal("background.jpg")));

        titleStyle = new Label.LabelStyle();
        titleStyle.font = font;
        titleStyle.fontColor = Color.BROWN;
        title = new Label("Pirates!", titleStyle);
        title.setFontScale(5f);
        title.setHeight(title.getPrefHeight());
        title.setWidth(title.getPrefWidth());
        title.setPosition(Pirates.w/2f, Pirates.h*0.8f, Align.center);

        newGame = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button.png"))));
        b_newGameStyle = new TextButton.TextButtonStyle();
        b_newGameStyle.up = newGame;
        b_newGameStyle.down = b_newGameStyle.up;
        b_newGameStyle.font = font;
        b_newGame = new TextButton("Play!", b_newGameStyle);
        b_newGame.setTransform(true);
        b_newGame.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               newGame();
               ping.play(Pirates.getVolume());
           }
        });
        b_newGame.setOrigin(Align.center);
        b_newGame.setPosition(Pirates.w/2f, Pirates.h/2f, Align.center);
        b_newGame.setScale(Pirates.h/200f);
        b_newGame.getLabel().setFontScale(0.25f);


        t_volume = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("button.png"))));
        b_volumeStyle = new TextButton.TextButtonStyle();
        b_volumeStyle.up = t_volume;
        b_volumeStyle.down = t_volume;
        b_volumeStyle.font = font;
        b_volume = new TextButton("", b_volumeStyle);
        b_volume.setText("Sound: " + Pirates.getVolume() * 2);
        b_volume.getLabel().setColor(255f, 255f, 255f, 1f);
        b_volume.getLabel().setFontScale(0.2f);
        b_volume.setPosition(0, 0, Align.bottomLeft);
        b_volume.setTransform(true);
        b_volume.setScale(3f);
        b_volume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pirates.setVolume(Pirates.getVolume() + 0.1f);
                if(Pirates.getVolume() > 0.5f) {
                    Pirates.setVolume(0f);
                }
                b_volume.setText("Sound: " + Pirates.getVolume() * 2);
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

    public void hide() {
        dispose();
    }

    public void newGame() {
        game.setScreen(new GameScreen(game));
    }
}
