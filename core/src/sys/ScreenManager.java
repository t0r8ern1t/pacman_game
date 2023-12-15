package sys;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME, CONTROLS, GAMEOVER
    }
    private static ScreenManager ourInstance = new ScreenManager();
    public static ScreenManager getInstance() { return ourInstance; }
    private ScreenManager() {}
    private Game game;
    private GameScreen game_screen;
    private MenuScreen menu_screen;
    private ControlsScreen controls_screen;
    private GameOverScreen gameover_screen;

    public void init(Game game, SpriteBatch batch) {
        this.game = game;
        game_screen = new GameScreen(batch);
        menu_screen = new MenuScreen(batch);
        controls_screen = new ControlsScreen(batch);
        gameover_screen = new GameOverScreen(batch, 0);
    }

    public void setResult(int n, boolean res) {
        gameover_screen.setScore(n);
        gameover_screen.setWin(res);
    }
    public void setScreen(ScreenType type) {
        Screen curr_screen = game.getScreen();
        switch (type) {
            case GAME:
                game.setScreen(game_screen);
                break;
            case MENU:
                game.setScreen(menu_screen);
                break;
            case CONTROLS:
                game.setScreen(controls_screen);
                break;
            case GAMEOVER:
                game.setScreen(gameover_screen);
                break;
        }
        if (curr_screen != null)
            curr_screen.dispose();
    }
}
