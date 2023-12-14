package sys;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static sys.ScreenManager.ScreenType.GAME;
import static sys.ScreenManager.ScreenType.MENU;

public class MyGame extends Game {
	private SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch);
		ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
