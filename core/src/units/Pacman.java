package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import sys.Direction;
import sys.GameScreen;

public class Pacman extends CharacterBase{
    private float angle;
    private int bullets_collected;
    Direction direction;

    public Pacman(GameScreen game_screen, TextureAtlas atlas){
        game = game_screen;
        gen_texture = atlas.findRegion("pacman1");
        eat_texture = atlas.findRegion("pacman2");
        curr_texture = gen_texture;
        death = atlas.findRegion("pacman_death").split(SIZE, SIZE)[0];
        position = new Vector2(100, 100);
        speed = 100;
        angle = 0;
        timer = 0;
        max_timer = 0.15f;
        bullets_collected = 0;
        tmp = new Vector2(0, 0);
        direction = Direction.STOP;
    }

    public int getBulletsCollected() { return bullets_collected; }

    public void renderScore(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Bullets left: " + bullets_collected, 10, Gdx.graphics.getHeight() - 10);
    }
    public void render(SpriteBatch batch) {
        if (speed != 0)
            batch.draw(curr_texture, position.x-SIZE/2, position.y-SIZE/2, SIZE/2, SIZE/2, SIZE, SIZE, 1, 1, angle);
        else {
            if (timer < max_timer) {
                int frame_index = (int) (timer / 0.3f) % death.length;
                batch.draw(death[frame_index], position.x - SIZE / 2, position.y - SIZE / 2);

            }
            else game.gameOver(false);
        }
    }

    public void update(float dt) {
        if (speed != 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                move(Direction.LEFT, dt);
                angle = 180;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                move(Direction.RIGHT, dt);
                angle = 0;
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                move(Direction.UP, dt);
                angle = 90;
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                move(Direction.DOWN, dt);
                angle = 270;
            } else curr_texture = gen_texture;
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) fire();
        }
        else timer += dt;
        BordersCollision();
    }

    public void changeTexture(float dt){
        timer += dt;
        if (timer >= max_timer) {
            timer = 0;
            if (curr_texture == eat_texture) curr_texture = gen_texture;
            else curr_texture = eat_texture;
        }
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

    public void damageTaken() {
        max_timer = 1.8f;
        speed = 0;
        curr_texture = gen_texture;
    }

    @Override
    public void destroy() {}
}
