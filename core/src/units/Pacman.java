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
    private float animation_timer;
    private float max_animation_timer;
    private TextureRegion death[];
    Direction direction;
    public Pacman(GameScreen game_screen, TextureAtlas atlas){
        game = game_screen;
        gen_texture = atlas.findRegion("pacman1");
        eat_texture = atlas.findRegion("pacman2");
        curr_texture = atlas.findRegion("pacman1");
        death = atlas.findRegion("death").split(SIZE, SIZE)[0];
        position = new Vector2(100, 100);
        speed = 100;
        angle = 0;
        animation_timer = 0;
        max_animation_timer = 0.1f;
        bullets_collected = 0;
        tmp = new Vector2(0, 0);
        direction = Direction.STOP;
    }

    public void renderScore(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "Bullets left: " + bullets_collected, 10, Gdx.graphics.getHeight() - 10);
    }
    public void render(SpriteBatch batch) {
        if (speed != 0)
            batch.draw(curr_texture, position.x-SIZE/2, position.y-SIZE/2, SIZE/2, SIZE/2, SIZE, SIZE, 1, 1, angle);
        else {

            if (animation_timer < max_animation_timer) {
                int frame_index = (int) (animation_timer / 0.3f) % death.length;
                batch.draw(death[frame_index], position.x - SIZE / 2, position.y - SIZE / 2);

            }
            else game.GameOver();
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
        else animation_timer += dt;
        BordersCollision();
    }

    public void changeTexture(float dt){
        animation_timer += dt;
        if (animation_timer >= max_animation_timer) {
            animation_timer = 0;
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
    public int getBulletsCollected() { return bullets_collected; }
    public void damageTaken() {
        max_animation_timer = 1.8f;
        speed = 0;
        curr_texture = gen_texture;
    }

    public boolean isEating(){ return curr_texture == eat_texture; }

    @Override
    public void destroy() {}
}
