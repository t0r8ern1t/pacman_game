package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import sys.Direction;
import sys.GameScreen;

public class Pacman extends CharacterBase{
    private float angle;
    private int bullets_collected;
    private float eat_timer;
    private float max_eat_timer;
    Direction direction;
    public Pacman(GameScreen game_screen, TextureAtlas atlas){
        game = game_screen;
        gen_texture = atlas.findRegion("pacman1");
        eat_texture = atlas.findRegion("pacman2");
        curr_texture = atlas.findRegion("pacman1");
        position = new Vector2(100, 100);
        speed = 100;
        angle = 0;
        max_eat_timer = 1;
        eat_timer = 0;
        bullets_collected = 0;
        tmp = new Vector2(0, 0);
        direction = Direction.STOP;
    }

    public void renderScore(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Score: " + bullets_collected, 10, Gdx.graphics.getHeight() - 10);
    }
    public void render(SpriteBatch batch) {
        batch.draw(curr_texture, position.x-SIZE/2, position.y-SIZE/2, SIZE/2, SIZE/2, SIZE, SIZE, 1, 1, angle);
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            move(Direction.LEFT, dt);
            angle = 180;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            move(Direction.RIGHT, dt);
            angle = 0;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            move(Direction.UP, dt);
            angle = 90;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            move(Direction.DOWN, dt);
            angle = 270;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) fire();
        BordersCollision();
    }

    public void changeTexture(float dt){

    }
    public void fire() {
        game.getBulletstack().setBulletsCollected(bullets_collected);
        boolean fire = game.getBulletstack().activate((int)angle, position.x, position.y);
        if (bullets_collected > 0 && fire == true)
            bullets_collected -= 1;
    }

    public void addBullet() {
        bullets_collected += 1;
    }
    public int getBulletsCollected() { return bullets_collected; }
    public void damageTaken() {
        bullets_collected = 0;
    }

    @Override
    public void destroy() {}
}
