package sys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen extends SimpleScreen {
    private int score;
    private boolean win;

    public GameOverScreen(SpriteBatch batch, int score){
        this.batch = batch;
        this.score = score;
        this.win = false;
    }

    public void setWin(boolean res) { win = res; }

    public void setScore(int score) { this.score = score; }

    @Override
    public void createButtons() {
        toMenuButton();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        update(delta);
        stage.draw();
        batch.begin();
        font.setColor(120, 120, 0, 1);
        if (win) {
            font.draw(batch, "You killed all the ghosts and won", (float) Gdx.graphics.getWidth() / 2 - 210, (float) Gdx.graphics.getHeight() * 8 / 12);
            font.draw(batch, "You scored " + score, (float) Gdx.graphics.getWidth() / 2 - 90, (float) Gdx.graphics.getHeight() * 7 / 12);
            font.getData().setScale(0.8f);
            if (score > 10)
                font.draw(batch, "wow a real ghostbuster", (float) Gdx.graphics.getWidth() / 2 - 150, (float) Gdx.graphics.getHeight() * 6 / 12);
            else {
                font.draw(batch, "using hotkeys is cheating :/", (float) Gdx.graphics.getWidth() / 2 - 160, (float) Gdx.graphics.getHeight() * 6 / 12);
            }
        } else {
            font.draw(batch, "Haha ur ded", (float) Gdx.graphics.getWidth() / 2 - 95, (float) Gdx.graphics.getHeight() * 8 / 12);
            font.draw(batch, "You scored " + score, (float) Gdx.graphics.getWidth() / 2 - 100, (float) Gdx.graphics.getHeight() * 7 / 12);
        }
        batch.end();
    }



}
