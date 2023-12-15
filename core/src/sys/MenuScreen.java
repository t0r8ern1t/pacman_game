package sys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen extends SimpleScreen {

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void createButtons() {
        Skin skin = new Skin();
        skin.add("text_button", new TextureRegion(atlas.findRegion("text_button")));
        skin.add("text_button_down", new TextureRegion(atlas.findRegion("text_button_down")));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("text_button");
        textButtonStyle.down = skin.getDrawable("text_button_down");
        textButtonStyle.font = font;
        final TextButton play_button = new TextButton("Play", textButtonStyle);
        final TextButton controls_button = new TextButton("Controls", textButtonStyle);
        final TextButton exit_button = new TextButton("Exit", textButtonStyle);
        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAME);
            }
        });
        controls_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.CONTROLS);
            }
        });
        exit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        play_button.setPosition((float) Gdx.graphics.getWidth() /2 - 220, (float) (Gdx.graphics.getHeight() * 3) /5);
        controls_button.setPosition((float) Gdx.graphics.getWidth() /2 - 220, (float) (Gdx.graphics.getHeight() * 2) /5);
        exit_button.setPosition((float) Gdx.graphics.getWidth() /2 - 220, (float) (Gdx.graphics.getHeight()) /5);
        stage.addActor(play_button);
        stage.addActor(controls_button);
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
        font.getData().setScale(2f);
        font.draw(batch, "Pacman", (float) Gdx.graphics.getWidth() /2 - 125, (float) (Gdx.graphics.getHeight())*11/12);
        font.getData().setScale(1f);
        font.draw(batch, "(but make it a shooter)", (float) Gdx.graphics.getWidth() /2 - 190, (float) (Gdx.graphics.getHeight())*10/12);

        batch.end();
    }
}
