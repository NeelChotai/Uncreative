package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MainMenu extends PirateScreen {
    private Image title;
    private Image background;
    private TextureRegionDrawable newGame;
    private Stage stage;
    private Button b_newGame;
    private Button.ButtonStyle b_newGameStyle;


    public MainMenu(Game game) {
        super(game);

        final Sound ping = Gdx.audio.newSound(Gdx.files.internal("Ping.mp3"));

        stage = new Stage();

        title = new Image(new Texture(Gdx.files.internal("drawing.jpg")));
        title.setPosition(1920/2, 900, Align.center);

        background = new Image(new Texture(Gdx.files.internal("background.jpg")));

        newGame = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("play.png"))));
        b_newGameStyle = new TextButton.TextButtonStyle();
        b_newGameStyle.up = newGame;
        b_newGameStyle.down = b_newGameStyle.up;
        b_newGame = new Button(b_newGameStyle);
        b_newGame.setPosition(1920/2, 1080/2, Align.center);
        b_newGame.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               System.out.println("BUTTON PRESS :D");
               ping.play();
           }
        });

        stage.addActor(background);
        stage.addActor(title);
        stage.addActor(b_newGame);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
