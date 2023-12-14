package sys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class ControlsScreen implements Screen {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Stage stage;

    public ControlsScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("game_pack.atlas"));
        font = new BitmapFont(Gdx.files.internal("CooperBlack.fnt"));
        stage = new Stage();
        createButton();
    }

    public void createButton() {
        Skin skin = new Skin();
        skin.add("text_button", new TextureRegion(atlas.findRegion("text_button")));
        skin.add("text_button_down", new TextureRegion(atlas.findRegion("text_button_down")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("text_button");
        textButtonStyle.down = skin.getDrawable("text_button_down");
        textButtonStyle.font = font;
        final TextButton exit_button = new TextButton("Exit to menu", textButtonStyle);
        exit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
            }
        });
        exit_button.setPosition((float) Gdx.graphics.getWidth() /2-220, (float) Gdx.graphics.getHeight()/5);
        stage.addActor(exit_button);
        Gdx.input.setInputProcessor(stage);
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

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        atlas.dispose();
        font.dispose();
        stage.dispose();
    }
}
