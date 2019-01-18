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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.uncreative.game.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    HashMap<Ship, Sprite> shipToSpriteMap;
    Stage ui;
    InputMultiplexer inputMultiplexer;
    InputProcessor inputProcessor;
    TextureRegionDrawable buttonTexture;
    TextButton attackButton;
    TextButton fleeButton;
    TextButton.TextButtonStyle buttonStyle;
    Boolean waitingForInput;

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
        attackButton.setTransform(true);
        fleeButton.setTransform(true);
        fleeButton.setVisible(false);
        attackButton.setVisible(false);
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attackButton.setVisible(false);
                fleeButton.setVisible(false);
                player.attack();
            }
        });
        fleeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attackButton.setVisible(false);
                fleeButton.setVisible(false);
                player.flee();
            }
        });

        shipToSpriteMap = new HashMap<Ship, Sprite>();

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


        Buff langwith1 = new Buff("defense", 1, 1, true);
        ArrayList<Buff> langwithBuffs = new ArrayList<Buff>();
        langwithBuffs.add(langwith1);
        College langwith = new College("Langwith", 100, 100, 100, false, langwithBuffs, Pirates.map[20][20]);
        langwith.ships.add(new OtherShip(5, 1, 0, langwith, 5, 5, new Item[0], Pirates.map[19][20]));
        colleges.add(langwith);

        player = new PlayerShip(10, 10, 5, 2, goodricke, 1000, 0, new Item[0], Pirates.map[5][5]);
        shipToSpriteMap.put(player, playerSprite_right);
        shipToSpriteMap.get(player).setSize((float) properties.get("tilewidth", Integer.class),
                (float) properties.get("tileheight", Integer.class));
        shipToSpriteMap.get(player).setPosition(player.location.getLocation()[0]*properties.get("tilewidth", Integer.class) + xoffset, player.location.getLocation()[1]*properties.get("tileheight", Integer.class) + yoffset);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(ui);

        ui.addActor(attackButton);
        ui.addActor(fleeButton);

        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    case Input.Keys.RIGHT:
                        movePlayer(player, Pirates.dir.E, playerSprites);
                        break;
                    case Input.Keys.DOWN:
                        movePlayer(player, Pirates.dir.S, playerSprites);
                        break;
                    case Input.Keys.LEFT:
                        movePlayer(player, Pirates.dir.W, playerSprites);
                        break;
                    case Input.Keys.UP:
                        movePlayer(player, Pirates.dir.N, playerSprites);
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
        for(Sprite sprite : shipToSpriteMap.values()) {
            sprite.draw(batch);
        }
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

    private void movePlayer(PlayerShip ship, Pirates.dir direction, Sprite[] sprites) {
        if(!ship.isInBattle()) {
            ship.move(direction);
            switch (direction) {
                case N:
                    shipToSpriteMap.put(ship, sprites[0]);
                    break;
                case E:
                    shipToSpriteMap.put(ship, sprites[1]);
                    break;
                case S:
                    shipToSpriteMap.put(ship, sprites[2]);
                    break;
                case W:
                    shipToSpriteMap.put(ship, sprites[3]);
            }
            shipToSpriteMap.get(ship).setSize((float) properties.get("tilewidth", Integer.class),
                    (float) properties.get("tileheight", Integer.class));
            shipToSpriteMap.get(ship).setPosition(player.location.getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, player.location.getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);
        } else {
            showCombatUI();
        }
        otherShipActions();
        tickBuffDurations(player);
        obstaclesActions();
    }

    private void moveShip(OtherShip ship, Pirates.dir direction, Sprite[] sprites) {//Sprite[] sprites = {up, right, down, left}
        if(!ship.isInBattle()) {
            ship.move(direction);
            switch (direction) {
                case N:
                    ship.sprite = sprites[0];
                    break;
                case E:
                    ship.sprite = sprites[1];
                    break;
                case S:
                    ship.sprite = sprites[2];
                    break;
                case W:
                    ship.sprite = sprites[3];
                    break;
            }
            ship.sprite.setSize((float) properties.get("tilewidth", Integer.class),
                    (float) properties.get("tileheight", Integer.class));
            ship.sprite.setPosition(player.location.getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, player.location.getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);
        } else {
            ship.attack();
        }
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

    private void showCombatUI() {//true if attack, false if flee
        attackButton.setVisible(true);
        fleeButton.setVisible(true);
    }
}
