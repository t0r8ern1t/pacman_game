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

public class GameOverScreen implements Screen {
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Stage stage;
    private int score;

    public GameOverScreen(SpriteBatch batch, int score){
        this.batch = batch;
        this.score = score;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("game_pack.atlas"));
        font = new BitmapFont(Gdx.files.internal("CooperBlack.fnt"));
        stage = new Stage();
        createButton();
    }

    public void setScore(int score) { this.score = score; }

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
        exit_button.setPosition((float) Gdx.graphics.getWidth() /2-220, (float) Gdx.graphics.getHeight()/3);
        stage.addActor(exit_button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        update(delta);
        stage.draw();
        batch.begin();
        font.setColor(120, 120, 0, 1);
        font.draw(batch, "Haha ur ded", (float) Gdx.graphics.getWidth() /2-220, (float) Gdx.graphics.getHeight()*8/12);
        font.draw(batch, "You scored " + score, (float) Gdx.graphics.getWidth() /2-220, (float) Gdx.graphics.getHeight()*7/12);
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
