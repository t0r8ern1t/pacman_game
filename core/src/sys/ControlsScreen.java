package sys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class ControlsScreen extends SimpleScreen {

    public ControlsScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void createButtons() {
        toMenuButton();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        update(delta);
        batch.begin();
        stage.draw();
        font.draw(batch, "Use WASD or arrow keys to move", (float) Gdx.graphics.getWidth()/2-400, (float) Gdx.graphics.getHeight()*10/12);
        font.draw(batch, "Collect bullets and shoot ghosts by pressing space", (float) Gdx.graphics.getWidth()/2-400, (float) Gdx.graphics.getHeight()*9/12);
        font.draw(batch, "Don't let the ghosts touch you", (float) Gdx.graphics.getWidth()/2-400, (float) Gdx.graphics.getHeight()*8/12);
        font.draw(batch, "Levels are generated randomly, you can re-generate the level", (float) Gdx.graphics.getWidth()/2-400, (float) Gdx.graphics.getHeight()*7/12);
        font.draw(batch, "if it looks wrong", (float) Gdx.graphics.getWidth()/2-400, (float) Gdx.graphics.getHeight()*6/12);
        batch.end();
    }
}
