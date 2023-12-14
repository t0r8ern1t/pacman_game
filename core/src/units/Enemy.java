package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import sys.Direction;
import sys.GameScreen;

public class Enemy extends CharacterBase {
    private TextureRegion hp_texture;
    Direction direction;
    private int max_hp;
    private int curr_hp;
    private float timer;
    private float max_timer;
    private boolean active;
    private Vector3 last_position;

    public Enemy(GameScreen game_screen, TextureAtlas atlas){
        game = game_screen;
        gen_texture = atlas.findRegion("purple_ghost_down");
        eat_texture = atlas.findRegion("ghost_alert");
        hp_texture = atlas.findRegion("hpbar");
        curr_texture = atlas.findRegion("purple_ghost_down");
        position = new Vector2(0, 0);
        last_position = new Vector3(0, 0, 0);
        speed = 80;
        max_hp = 10;
        curr_hp = max_hp;
        timer = 0;
        max_timer = 3;
        active = false;
        tmp = new Vector2(0, 0);
        direction = Direction.UP;
    }
    public void render(SpriteBatch batch) {
        batch.draw(curr_texture, position.x-SIZE/2, position.y-SIZE/2, SIZE, SIZE);
        if (curr_hp < max_hp) batch.draw(hp_texture, position.x-SIZE/2, position.y+SIZE/2,32*(float) curr_hp / max_hp, 8);
    }
    public boolean isActive() { return active; }

    public void deactivate() { active = false;}

    public void setRandDirection() {
        direction = Direction.values()[MathUtils.random(0, Direction.values().length - 2)];
    }
    public void activate(float x, float y) {
        active = true;
        speed = 80;
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
            destroy();
        }
    }

    public void damageMade() {
        curr_texture = eat_texture;
    }
    public void update(float dt) {
        timer += dt;
        if (timer >= max_timer) {
            timer = 0;
            max_timer = MathUtils.random(2.0f, 6.0f);
            setRandDirection();
        }
        move(direction, dt);
        //position.add(speed*direction.getVx()*dt, speed*direction.getVy()*dt);
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
    }
}
