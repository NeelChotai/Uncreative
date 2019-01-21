package screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.utils.Align;
import com.uncreative.game.*;

import javax.print.attribute.standard.MediaSize;
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
    Texture buildingTexture;
    SpriteBatch batch;
    HashMap<Ship, Sprite> shipToSpriteMap;
    HashMap<Building, Sprite> buildingToSpriteMap;
    Stage ui;
    InputMultiplexer inputMultiplexer;
    InputProcessor inputProcessor;
    TextureRegionDrawable buttonTexture;
    TextButton attackButton;
    TextButton fleeButton;
    TextButton healButton;
    TextButton.TextButtonStyle buttonStyle;
    int turns;
    int turnsOutOfCombat;
    final int shipLimitPerCollege = 5;
    boolean interactingWithDepartment = false;

    float xoffset;
    float yoffset;

    PlayerShip player;
    ArrayList<College> colleges;
    ArrayList<Obstacle> obstacles;
    ArrayList<Department> departments;

    public GameScreen(Game game) {
        super(game);
        turns = 0;
        turnsOutOfCombat = 0;

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
        buildingTexture = new Texture("building.png");//Replace with building texture

        buttonTexture = new TextureRegionDrawable(new TextureRegion(new Texture("button.png")));
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonTexture;
        buttonStyle.down = buttonTexture;
        buttonStyle.font = super.font;
        attackButton = new TextButton("Attack", buttonStyle);
        fleeButton = new TextButton("Flee", buttonStyle);
        healButton = new TextButton("Heal 1HP", buttonStyle);
        attackButton.setTransform(true);
        fleeButton.setTransform(true);
        healButton.setTransform(true);
        attackButton.setOrigin(Align.center);
        attackButton.setPosition(Pirates.w/4f, Pirates.h/2f, Align.center);
        attackButton.setScale(Pirates.h/200f);
        attackButton.getLabel().setFontScale(0.25f);
        fleeButton.setOrigin(Align.center);
        fleeButton.setScale(Pirates.h/200f);
        fleeButton.getLabel().setFontScale(0.25f);
        fleeButton.setPosition(3f*Pirates.w/4f, Pirates.h/2f, Align.center);
        healButton.setOrigin(Align.center);
        healButton.setPosition(Pirates.w/2f, Pirates.h/2f, Align.center);
        healButton.setScale(Pirates.h/200f);
        healButton.getLabel().setFontScale(0.25f);
        fleeButton.setVisible(false);
        attackButton.setVisible(false);
        healButton.setVisible(false);
        attackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attackButton.setVisible(false);
                fleeButton.setVisible(false);
                player.attack();
                endTurn();
            }
        });
        fleeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attackButton.setVisible(false);
                fleeButton.setVisible(false);
                Random random = new Random();
                if(random.nextBoolean()) {
                    player.flee();
                } else {
                    endTurn();
                }
            }
        });
        healButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(player.location.building instanceof Department) {
                    ((Department) player.location.building).healShip(player, 1);
                }

            }
        });

        shipToSpriteMap = new HashMap<Ship, Sprite>();
        buildingToSpriteMap = new HashMap<Building, Sprite>();

        colleges = new ArrayList<College>();
        departments = new ArrayList<Department>();
        obstacles = new ArrayList<Obstacle>();

        Buff goodrickebuff1 = new Buff("maxHP", 1, 1, true);
        ArrayList<Buff>  goodrickeBuffs = new ArrayList<Buff>();
        goodrickeBuffs.add(goodrickebuff1);
        College goodricke = new College("Goodricke", 100,100, 100, true, goodrickeBuffs, Pirates.map[10][10]);
        colleges.add(goodricke);


        Buff langwith1 = new Buff("defense", 1, 1, true);
        ArrayList<Buff> langwithBuffs = new ArrayList<Buff>();
        langwithBuffs.add(langwith1);
        College langwith = new College("Langwith", 100, 100, 100, false, langwithBuffs, Pirates.map[20][20]);
        colleges.add(langwith);

        Buff halifax1 = new Buff("maxHP", 5, 1, true);
        ArrayList<Buff> halifaxBuffs = new ArrayList<Buff>();
        halifaxBuffs.add(halifax1);
        College halifax = new College("Halifax", 150, 100, 100, false, halifaxBuffs, Pirates.map[99][98]);
        colleges.add(halifax);

        Department computerScience = new Department("Computer Science", 2, 100, new Item[0], Pirates.map[10][5]);
        departments.add(computerScience);

        Department maths = new Department("Maths", 2, 100, new Item[0], Pirates.map[50][50]);
        departments.add(maths);


        player = new PlayerShip(10, 50, 5, 2, goodricke, 0, 0, new Item[0], Pirates.map[5][5]);
        goodricke.ships.add(player);
        Sprite shipSprite = new Sprite(ship_side);
        shipToSpriteMap.put(player, shipSprite);
        shipSprite.setSize((float) properties.get("tilewidth", Integer.class), (float) properties.get("tileheight", Integer.class));
        shipSprite.setPosition(player.getLocation().getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, player.getLocation().getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);

        for(College college : colleges) {
            Sprite buildingSprite = new Sprite(buildingTexture);
            buildingToSpriteMap.put(college, buildingSprite);
            buildingSprite.setSize((float) properties.get("tilewidth", Integer.class), (float) properties.get("tileheight", Integer.class));
            buildingSprite.setPosition(college.getLocation().getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, college.getLocation().getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);

            if(!college.getCaptured()) {
                createNewShip(college);
            }
        }

        for(Department department : departments) {
            Sprite buildingSprite = new Sprite(buildingTexture);
            buildingToSpriteMap.put(department, buildingSprite);
            buildingSprite.setSize((float) properties.get("tilewidth", Integer.class), (float) properties.get("tileheight", Integer.class));
            buildingSprite.setPosition(department.getLocation().getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, department.getLocation().getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);
        }

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(ui);

        ui.addActor(attackButton);
        ui.addActor(fleeButton);
        ui.addActor(healButton);

        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch(keycode) {
                    case Input.Keys.RIGHT:
                        if(interactingWithDepartment){
                            healButton.setVisible(false);
                        }
                        if(!player.isInBattle()) {
                            movePlayer(player, Pirates.dir.E);
                        }
                        break;
                    case Input.Keys.DOWN:
                        if(interactingWithDepartment){
                            healButton.setVisible(false);
                        }
                        if (!player.isInBattle()) {
                            movePlayer(player, Pirates.dir.S);
                        }
                        break;
                    case Input.Keys.LEFT:
                        if(interactingWithDepartment){
                            healButton.setVisible(false);
                        }
                        if (!player.isInBattle()) {
                            movePlayer(player, Pirates.dir.W);
                        }
                        break;
                    case Input.Keys.UP:
                        if(interactingWithDepartment){
                            healButton.setVisible(false);
                        }
                        if (!player.isInBattle()) {
                            movePlayer(player, Pirates.dir.N);
                        }
                        break;
                    case Input.Keys.ESCAPE:
                        setMainMenu();
                    case Input.Keys.SPACE:
                        endTurn();
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
        shipToSpriteMap.get(player).setColor(Color.GREEN);
        font.getData().setScale(0.5f);
        if (!player.isInBattle()) {
            for(College college : colleges) {
                for(Ship ship: college.ships) {
                    Sprite shipSprite = shipToSpriteMap.get(ship);
                    shipSprite.draw(batch);
                    if(!(ship instanceof PlayerShip)) {
                        font.draw(batch, ship.getCollegeAllegiance().getName() + " Ship", (int)shipSprite.getX() -20, (int) shipSprite.getY() + 40);
                        font.draw(batch, "HP: " + ship.getHP() + "/" + ship.getMaxHP(), (int) shipSprite.getX() - 10, (int) shipSprite.getY() - 10);
                    }
                }
            }
            for(Building building : buildingToSpriteMap.keySet()) {
                Sprite buildingSprite = buildingToSpriteMap.get(building);
                font.draw(batch, building.getName(), (int)buildingSprite.getX() -20, (int) buildingSprite.getY() + 40);
                if(building instanceof College) {
                    College college = (College) building;
                    if(college.getCaptured()){
                        buildingSprite.setColor(Color.GREEN);
                    }
                    font.draw(batch, "HP: " + college.getHP(), (int) buildingSprite.getX() - 10, (int) buildingSprite.getY() - 10);
                }
                buildingSprite.draw(batch);
            }
        } else {
            shipToSpriteMap.get(player).draw(batch);
            Sprite sprite = shipToSpriteMap.get(player.inBattle);
            sprite.draw(batch);
            font.draw(batch, "HP: " + player.inBattle.getHP() + "/" + player.inBattle.getMaxHP(),(int)sprite.getX() - 10, (int) sprite.getY() - 10);
        }
        font.getData().setScale(1f);
        batch.end();
        ui.getBatch().begin();
        font.draw(ui.getBatch(), "Gold: " + player.getGoldAvailable() + "     XP: " + player.getXP() + "     HP: " + player.getHP() + "/" + player.getMaxHP() + "      Turns Out of Combat: " + turnsOutOfCombat + "/" + turns, 0, Pirates.h - 5);
        if(interactingWithDepartment) {
            font.draw(ui.getBatch(), "Heal Cost per 1HP: " + ((Department)player.location.building).getHealCost(), Pirates.w/4f, Pirates.h/4f);
        }
        ui.getBatch().end();
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

    private void movePlayer(PlayerShip ship, Pirates.dir direction) {
        moveShip(ship, direction);
        if(player.location.building instanceof Department) {//Department
            interactingWithDepartment = true;
            showDepartmentUI();
        } else {
            interactingWithDepartment = false;
        }
        player.addXP(5*(1+turnsOutOfCombat/50));
        endTurn();
    }

    private void endTurn() {
        turns++;
        turnsOutOfCombat++;
        otherShipActions();
        tickBuffDurations(player);
        obstaclesActions();
        if(player.isInBattle()) {
            turnsOutOfCombat = 0;
            showCombatUI();
        } else if(interactingWithDepartment) {
            showDepartmentUI();
        } else {
            Random rand = new Random();
            for(College college : colleges) {
                if(!college.getCaptured() && college.ships.size() < shipLimitPerCollege && rand.nextInt(100) == 0) {
                    createNewShip(college);
                }
            }

        }
    }

    private void createNewShip(College college) {
        int[] collegeLocation = college.location.getLocation();
        Location newLocation;
        for(int i = -2; i <= 2; i++) {
            for(int j = -2; j <= 2; j++) {
                newLocation = Pirates.map[collegeLocation[0] + i][collegeLocation[1] + j];
                if(newLocation.building == null && newLocation.ship == null && newLocation.obstacles.isEmpty()) {
                    Ship ship = new OtherShip(5 + turns/1000, 3 + turns/1000, 1 + turns/1000, college, 20 + turns/1000, 20 + turns/1000, new Item[0], newLocation);
                    college.ships.add(ship);
                    Sprite shipSprite = new Sprite(ship_side);
                    shipToSpriteMap.put(ship, shipSprite);
                    shipSprite.setSize((float) properties.get("tilewidth", Integer.class), (float) properties.get("tileheight", Integer.class));
                    shipSprite.setPosition(ship.getLocation().getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, ship.getLocation().getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);
                    return;
                }
            }
        }

    }

    private void moveShip(Ship ship, Pirates.dir direction) {
            ship.move(direction);
            switch (direction) {
                case N:
                    shipToSpriteMap.put(ship, new Sprite(ship_up));
                    break;
                case E:
                    shipToSpriteMap.put(ship, new Sprite(ship_side));
                    break;
                case S:
                    shipToSpriteMap.put(ship, new Sprite(ship_down));
                    break;
                case W:
                    Sprite sprite = new Sprite(ship_side);
                    sprite.flip(true, false);
                    shipToSpriteMap.put(ship, sprite);
                    break;
            }
            shipToSpriteMap.get(ship).setSize((float) properties.get("tilewidth", Integer.class),
                    (float) properties.get("tileheight", Integer.class));
            shipToSpriteMap.get(ship).setPosition(ship.getLocation().getLocation()[0] * properties.get("tilewidth", Integer.class) + xoffset, ship.getLocation().getLocation()[1] * properties.get("tileheight", Integer.class) + yoffset);
    }

    private void generateUI() {
        ui = new Stage();
    }

    private void otherShipActions() {
        for(College college : colleges) {
            for(Ship ship : college.ships) {
                if(ship instanceof PlayerShip) { continue; }
                OtherShip otherShip = (OtherShip) ship;
                if(otherShip.inBattle instanceof PlayerShip) {
                    otherShip.attack();
                    if(player.getHP() <= 0) {
                        endGame();
                    }
                } else {
                    if(ship.getCollegeAllegiance().getHostile()) {
                            int dx = ship.getLocation().getLocation()[0] - player.location.getLocation()[0];
                            int dy = ship.getLocation().getLocation()[1] - player.location.getLocation()[1];
                            if(Math.abs(dx) >= Math.abs(dy)) {//Move towards x
                                if(dx < 0) {
                                    moveShip(ship, Pirates.dir.E);
                                } else {
                                    moveShip(ship, Pirates.dir.W);
                                }
                            } else {//Move towards y
                                if(dy < 0) {
                                    moveShip(ship, Pirates.dir.N);
                                } else {
                                    moveShip(ship, Pirates.dir.S);
                                }
                            }
                    } else {
                        Random random = new Random();
                        int num = random.nextInt(10);
                        switch (num) {
                            case 0:
                                moveShip(ship, Pirates.dir.N);
                                break;
                            case 1:
                                moveShip(ship, Pirates.dir.E);
                                break;
                            case 2:
                                moveShip(ship, Pirates.dir.S);
                                break;
                            case 3:
                                moveShip(ship, Pirates.dir.W);
                                break;
                            default:
                                break;
                        }
                    }
                }
                tickBuffDurations(ship);
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

    private void showDepartmentUI() {
        healButton.setVisible(true);
    }

    private void setMainMenu() {
        game.setScreen(new MainMenu(game));
    }

    private void endGame() {
        game.setScreen(new deathScreen(game, player.getTotalGoldEarned() + player.getXP()));
    }
}
