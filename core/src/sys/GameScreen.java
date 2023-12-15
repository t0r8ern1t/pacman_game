package sys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import units.*;

public class GameScreen extends SimpleScreen {
    private Pacman pacman;
    private BulletStack bullet_stack;
    private EnemyStack enemy_stack;
    private Map map;
    private Music music;
    private boolean paused;


    public Map getMap() { return map; }
    public BulletStack getBulletstack() { return bullet_stack; }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }
    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("game_pack.atlas"));
        font = new BitmapFont(Gdx.files.internal("CooperBlack.fnt"));
        pacman = new Pacman(this, atlas);
        bullet_stack = new BulletStack(atlas);
        music = Gdx.audio.newMusic(Gdx.files.internal("main_theme.mp3"));
        music.setLooping(true);
        music.play();
        map = new Map(atlas);
        enemy_stack = new EnemyStack(this, atlas);
        activateEnemies();
        stage = new Stage();
        createButtons();
        paused = false;
    }

    public void createButtons() {
        // пока пусть будет через текст
        // TODO разобраться почему эта штука не работает с ImageButton
        Skin skin = new Skin();
        skin.add("stop", new TextureRegion(atlas.findRegion("stop_button")));
        skin.add("go", new TextureRegion(atlas.findRegion("go_button")));
        skin.add("text_button", new TextureRegion(atlas.findRegion("text_button")));
        skin.add("text_button_down", new TextureRegion(atlas.findRegion("text_button_down")));
        TextButton.TextButtonStyle textImageStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textImageStyle.up = skin.getDrawable("stop");
        textImageStyle.checked = skin.getDrawable("go");
        textButtonStyle.up = skin.getDrawable("text_button");
        textButtonStyle.down = skin.getDrawable("text_button_down");
        textImageStyle.font = font;
        textButtonStyle.font = font;
        final TextButton pause_button = new TextButton("", textImageStyle);
        final TextButton exit_button = new TextButton("Exit to menu", textButtonStyle);
        final TextButton rerun_button = new TextButton("Re-generate level", textButtonStyle);

        exit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
                music.stop();
            }
        });
        rerun_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                regenerate();
            }
        });
        exit_button.setPosition((float) Gdx.graphics.getWidth() /2-220, (float) Gdx.graphics.getHeight()/3);
        rerun_button.setPosition((float) Gdx.graphics.getWidth() /2-220, (float) Gdx.graphics.getHeight()/2);

        pause_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                paused = !paused;
                if (paused) {
                    stage.addActor(exit_button);
                    stage.addActor(rerun_button);
                }
                else {
                    exit_button.remove();
                    rerun_button.remove();
                }
            }
        });
        pause_button.setPosition(Gdx.graphics.getWidth()-60, Gdx.graphics.getHeight()-60);
        stage.addActor(pause_button);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        map.render(batch);
        pacman.render(batch);
        enemy_stack.render(batch);
        bullet_stack.render(batch);
        pacman.renderScore(batch, font);
        stage.draw();
        batch.end();
    }

    public void update(float dt) {
        if (!paused) {
            pacman.update(dt);
            enemy_stack.update(dt);
            bullet_stack.update(dt);
            checkCollisions();
        }
        stage.act(dt);

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && Gdx.input.isKeyPressed(Input.Keys.Z))
            gameOver(true);
    }

    public void regenerate() {
        map.regenerate();
        pacman = new Pacman(this, atlas);
        enemy_stack = new EnemyStack(this, atlas);
        activateEnemies();
    }

    public void activateEnemies(){
        for (int i = 0; i < enemy_stack.getEnemies().length; ++i) {
            int x, y;
            do {
                x = MathUtils.random(200, Gdx.graphics.getWidth());
                y = MathUtils.random(200, Gdx.graphics.getHeight());
            } while (!map.isAreaClear(x, y, (enemy_stack.getEnemies()[0].SIZE + 7) / 2));
            enemy_stack.activate(x, y);
        }
    }

    public void gameOver(boolean res) {
        music.stop();
        ScreenManager.getInstance().setResult(pacman.getBulletsCollected(), res);
        ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.GAMEOVER);
    }

    public void enemyBulletCollision(Enemy enemy){
        for (int i = 0; i < bullet_stack.getBullets().length; ++i) {
            Bullet bullet = bullet_stack.getBullets()[i];
            if (bullet.isActive()) {
                if (enemy.getPosition().dst(bullet.getPosition()) <= enemy.SIZE/2) {
                    enemy.damageTaken();
                    bullet.deactivate();
                }
            }
        }
    }

    public void enemyEnemyCollision(Enemy enemy, int j) {
        for (int i = 0; i < enemy_stack.getEnemies().length; ++i) {
            if (i != j) {
                Enemy enemy2 = enemy_stack.getEnemies()[i];
                if (!enemy2.isDying()) {
                    if (enemy.getPosition().dst(enemy2.getPosition()) <= enemy.SIZE) {
                        enemy.setRandDirection();
                        enemy2.setRandDirection();
                    }
                }
            }
        }
    }

    public void enemyPacmanCollision(Enemy enemy) {
        if (enemy.getPosition().dst(pacman.getPosition()) <= enemy.SIZE) {
            pacman.damageTaken();
            enemy.damageMade();
            pacman.stop();
            enemy_stack.deactivateAll();

        }
    }

    public void checkCollisions() {
        for (int i = 0; i < bullet_stack.getBullets().length; ++i) {
            if (bullet_stack.getBullets()[i].isActive()) {
                map.checkBulletWallCollision(bullet_stack.getBullets()[i]);
            }
        }

        if (!enemy_stack.isActive()) gameOver(true);
        for (int j = 0; j < enemy_stack.getEnemies().length; ++j) {
            Enemy enemy = enemy_stack.getEnemies()[j];
            if (!enemy.isDying()) {
                enemyBulletCollision(enemy);
                enemyEnemyCollision(enemy, j);
                enemyPacmanCollision(enemy);
            }
        }
    }
}
