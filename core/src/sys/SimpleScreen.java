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

public abstract class SimpleScreen implements Screen {
    protected SpriteBatch batch;
    protected TextureAtlas atlas;
    protected BitmapFont font;
    protected Stage stage;

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("game_pack.atlas"));
        font = new BitmapFont(Gdx.files.internal("CooperBlack.fnt"));
        stage = new Stage();
        createButtons();
    }

    public abstract void createButtons();

    public void toMenuButton() {
        Skin skin = new Skin();
        skin.add("text_button", new TextureRegion(atlas.findRegion("text_button")));
        skin.add("text_button_down", new TextureRegion(atlas.findRegion("text_button_down")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("text_button");
        textButtonStyle.down = skin.getDrawable("text_button_down");
        textButtonStyle.font = font;
        final TextButton exit_button = new TextButton("Back to menu", textButtonStyle);
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
    public void render(float delta) {    }

    @Override
    public void resize(int width, int height) {    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

    @Override
    public void hide() {    }

    @Override
    public void dispose() {
        atlas.dispose();
        font.dispose();
        stage.dispose();
    }

    public void update(float dt) {
        stage.act(dt);
    }
}
