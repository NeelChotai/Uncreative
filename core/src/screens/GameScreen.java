package screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.uncreative.game.*;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends PirateScreen{
    TiledMap tiledMap;
    TiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    MapProperties properties;
    Texture ship_side;
    Texture ship_down;
    Texture ship_up;
    SpriteBatch batch;
    Sprite playerSprite;
    Stage ui;
    InputMultiplexer inputMultiplexer;
    InputProcessor inputProcessor;
    TextureRegionDrawable buttonTexture;
    TextButton attackButton;
    TextButton fleeButton;
    TextButton.TextButtonStyle buttonStyle;

    float xoffset;
    float yoffset;

    PlayerShip player;
    ArrayList<College> colleges;
    ArrayList<Obstacle> obstacles;

    public GameScreen(Game game) {
        super(game);

        generateUI();

        //Create instances of the map and camera.
        tiledMap = new TmxMapLoader().load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Pirates.w, Pirates.h);
        camera.translate(Pirates.w/2f, Pirates.h/2f);
        camera.update();

        //Create an array of Location objects for each tile.
        properties = tiledMap.getProperties();

        xoffset = 0.2f * properties.get("tilewidth", Integer.class);
        yoffset = 0.75f * properties.get("tileheight", Integer.class);

        Pirates.map = new Location[properties.get("width", Integer.class)][properties.get("height", Integer.class)];
        for(int x = 0; x < properties.get("width", Integer.class); x++) {
            for(int y = 0; y < properties.get("height", Integer.class); y++) {
                Pirates.map[x][y] = new Location(x,y);
            }
        }

        batch = new SpriteBatch();

        ship_side = new Texture("ship_o_s.png");
        ship_up = new Texture("ship_o_f.png");
        ship_down = new Texture("ship_o_b.png");
        buttonTexture = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonTexture;
        buttonStyle.down = buttonTexture;
        buttonStyle.font = super.font;
        attackButton = new TextButton("Attack", buttonStyle);
        fleeButton = new TextButton("Flee", buttonStyle);

        Sprite playerSprite_right = new Sprite(ship_side);
        Sprite playerSprite_up = new Sprite(ship_up);
        Sprite playerSprite_down = new Sprite(ship_down);
        Sprite playerSprite_left = new Sprite(ship_side);
        playerSprite_left.flip(true, false);

        final Sprite[] playerSprites = {playerSprite_up, playerSprite_right, playerSprite_down, playerSprite_left};

        colleges = new ArrayList<College>();
        obstacles = new ArrayList<Obstacle>();
        Buff goodrickebuff1 = new Buff("attack", 1, 1, true);
        ArrayList<Buff>  goodrickeBuffs = new ArrayList<Buff>();
        goodrickeBuffs.add(goodrickebuff1);
        College goodricke = new College("Goodricke", 100,100, 100, true, goodrickeBuffs, Pirates.map[10][10]);
        colleges.add(goodricke);
        player = new PlayerShip(10, 10, 5, 2, goodricke, 1000, 0, new Item[0], Pirates.map[5][5]);
        playerSprite = playerSprite_right;
        playerSprite.setSize((float) properties.get("tilewidth", Integer.class),
                (float) properties.get("tileheight", Integer.class));
        playerSprite.setPosition(player.location.getLocation()[0]*properties.get("tilewidth", Integer.class) + xoffset, player.location.getLocation()[1]*properties.get("tileheight", Integer.class) + yoffset);

        inputMultiplexer = new InputMultiplexer();
        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    case Input.Keys.RIGHT:
                        takeTurn(player, Pirates.dir.E, playerSprites);
                        break;
                    case Input.Keys.DOWN:
                        takeTurn(player, Pirates.dir.S, playerSprites);
                        break;
                    case Input.Keys.LEFT:
                        takeTurn(player, Pirates.dir.W, playerSprites);
                        break;
                    case Input.Keys.UP:
                        takeTurn(player, Pirates.dir.N, playerSprites);
                }
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        setCameraToPlayer();
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
        ui.draw();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }

    private void setCameraToPlayer() {
        camera.position.x = player.location.getLocation()[0]*properties.get("tilewidth", Integer.class);
        camera.position.y = player.location.getLocation()[1]*properties.get("tileheight", Integer.class);
    }
    //THIS FUNCTION WILL BE USED TO "TAKE A TURN" FOR ALL SHIPS AND OBSTACLES!
    private void takeTurn(Ship ship, Pirates.dir direction, Sprite[] sprites) {//Sprite[] sprites = {up, right, down, left}
        if(!ship.isInBattle()) {
            ship.move(direction);
            switch (direction) {
                case N:
                    playerSprite = sprites[0];
                    break;
                case E:
                    playerSprite = sprites[1];
                    break;
                case S:
                    playerSprite = sprites[2];
                    break;
                case W:
                    playerSprite = sprites[3];
                    break;
            }
            playerSprite.setSize((float) properties.get("tilewidth", Integer.class),
                    (float) properties.get("tileheight", Integer.class));
            playerSprite.setPosition(player.location.getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, player.location.getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);
            player.addXP(10);
        } else {
            if(showCombatUI()) {
                player.attack();
            } else {
                player.flee();
            }
        }
        otherShipActions();
        tickBuffDurations(player);
        obstaclesActions();
    }

    private void generateUI() {
        ui = new Stage();
    }

    private void otherShipActions() {
        for(College college : colleges) {
            for(OtherShip othership : college.ships) {
                if(othership.isInBattle()) {
                    othership.attack();
                } else {
                    Random random = new Random();
                    int num = random.nextInt(5);
                    switch(num) {
                        case 0:
                            othership.move(Pirates.dir.N);
                            break;
                        case 1:
                            othership.move(Pirates.dir.E);
                            break;
                        case 2:
                            othership.move(Pirates.dir.S);
                            break;
                        case 3:
                            othership.move(Pirates.dir.W);
                            break;
                        default:
                            break;
                    }
                }
                tickBuffDurations(othership);

            }
        }
    }

    private void tickBuffDurations(Ship ship) {
        for(Buff buff:ship.getActiveBuffs()) {
            if(buff.isInfinite()) {
                continue;
            } else {
                buff.duration--;
                if(buff.duration <= 0) {
                    ship.removeBuff(buff);
                }
            }
        }
    }

    private void obstaclesActions() {
        for(Obstacle obstacle : obstacles) {
            if(obstacle instanceof MovingObstacle) {
                MovingObstacle moving = (MovingObstacle) obstacle;
                Random random = new Random();
                if(random.nextInt(10) == 0) {
                    moving.toggleFollowing();
                }
                if(moving.getFollowing()) {
                    moving.followShip(player);
                } else {
                    switch(random.nextInt(5)) {
                        case 0:
                            moving.move(Pirates.dir.N);
                            break;
                        case 1:
                            moving.move(Pirates.dir.E);
                            break;
                        case 2:
                            moving.move(Pirates.dir.S);
                            break;
                        case 3:
                            moving.move(Pirates.dir.W);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private Boolean showCombatUI() {//true if attack, false if flee

        return true;
    }
}
