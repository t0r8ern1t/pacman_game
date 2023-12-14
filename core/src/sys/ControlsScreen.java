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
        createText();
        createButton();
    }

    public void createText() {
        batch.begin();
        font.draw(batch, "Use WASD or arrow keys to move", (float) Gdx.graphics.getWidth() /2, (float) Gdx.graphics.getHeight()/3);
        batch.end();
    }

    public void createButton() {
        Skin skin = new Skin();
        skin.add("text_button", new TextureRegion(atlas.findRegion("text_button")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("text_button");
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
        stage.draw();
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
