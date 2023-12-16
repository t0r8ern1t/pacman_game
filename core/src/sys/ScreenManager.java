package sys;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME, CONTROLS, GAMEOVER
    }
    private static ScreenManager ourInstance = new ScreenManager();
    public static ScreenManager getInstance() { return ourInstance; }
    private ScreenManager() {}

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;
    private Game game;
    private GameScreen game_screen;
    private MenuScreen menu_screen;
    private ControlsScreen controls_screen;
    private GameOverScreen gameover_screen;
    private Viewport viewport;


    public void init(Game game, SpriteBatch batch) {
        this.game = game;
        game_screen = new GameScreen(batch);
        menu_screen = new MenuScreen(batch);
        controls_screen = new ControlsScreen(batch);
        gameover_screen = new GameOverScreen(batch, 0);
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
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
