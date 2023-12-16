package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import sys.GameScreen;

public class Enemy extends CharacterBase {
    private TextureRegion hp_texture;
    Direction direction;
    private int max_hp;
    private int curr_hp;
    private boolean active;
    private boolean dying;
    private Vector3 last_position;

    public Enemy(GameScreen game_screen, TextureAtlas atlas, int type){
        game = game_screen;
        eat_texture = atlas.findRegion("ghost_alert");
        hp_texture = atlas.findRegion("hpbar");
        position = new Vector2(0, 0);
        last_position = new Vector3(0, 0, 0);
        dying = false;
        timer = 0;
        max_timer = 3f;
        active = false;
        tmp = new Vector2(0, 0);
        direction = Direction.UP;
        switch (type){
            case 0:
                gen_texture = atlas.findRegion("purple_ghost");
                death = atlas.findRegion("purple_ghost_death").split(SIZE, SIZE)[0];
                speed = 80;
                max_hp = 5;
                break;
            case 1:
                gen_texture = atlas.findRegion("blue_ghost");
                death = atlas.findRegion("blue_ghost_death").split(SIZE, SIZE)[0];
                speed = 120;
                max_hp = 1;
                break;
            case 2:
                gen_texture = atlas.findRegion("green_ghost");
                death = atlas.findRegion("green_ghost_death").split(SIZE, SIZE)[0];
                speed = 50;
                max_hp = 10;
                break;
        }
        curr_hp = max_hp;
        curr_texture = gen_texture;
    }

    public void render(SpriteBatch batch) {
        if (curr_hp > 0) {
            batch.draw(curr_texture, position.x - SIZE / 2, position.y - SIZE / 2, SIZE, SIZE);
            if (curr_hp < max_hp)
                batch.draw(hp_texture, position.x - SIZE / 2, position.y + SIZE / 2, 32 * (float) curr_hp / max_hp, 8);
        }
        else {
            if (timer < max_timer) {
                int frame_index = (int) (timer / 0.3f) % death.length;
                batch.draw(death[frame_index], position.x - SIZE / 2, position.y - SIZE / 2);
            }
            else destroy();
        }
    }

    public boolean isActive() { return active; }

    public boolean isDying() { return dying; }

    public void setRandDirection() {
        direction = Direction.values()[MathUtils.random(0, Direction.values().length - 2)];
    }

    public void activate(float x, float y) {
        active = true;
        curr_texture = gen_texture;
        curr_hp = max_hp;
        position.set(x, y);
        timer = 0;
        setRandDirection();
    }

    @Override
    public void destroy(){
        active = false;
    }

    public void damageTaken() {
        curr_hp -= 1;
        if (curr_hp <= 0) {
            max_timer = 1.8f;
            timer = 0;
            dying = true;
        }
    }

    public void damageMade() {
        curr_texture = eat_texture;
    }

    public void update(float dt) {
        if (curr_hp > 0) {
            timer += dt;
            if (timer >= max_timer) {
                timer = 0;
                max_timer = MathUtils.random(2.0f, 6.0f);
                setRandDirection();
            }
            move(direction, dt);
            BordersCollision();
            if (Math.abs(position.x - last_position.x) < 0.5 && Math.abs(position.y - last_position.y) < 0.5) {
                last_position.z += dt;
                if (last_position.z > 0.5) {
                    setRandDirection();
                }
            } else {
                last_position.x = position.x;
                last_position.y = position.y;
                last_position.z = 0;
            }
        } else timer += dt;
    }
}
