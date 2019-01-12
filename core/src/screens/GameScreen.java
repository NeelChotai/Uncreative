package screens;

import com.badlogic.gdx.Game;
import com.uncreative.game.Location;
import com.uncreative.game.Pirates;

public class GameScreen extends PirateScreen{

    public GameScreen(Game game) {
        super(game);
        for(int i = 0; i < Pirates.size; i++) {
            for(int j = 0; j < Pirates.size; j++) {
                Pirates.map[i][j] = new Location(i, j);
            }
        }
    }
}
