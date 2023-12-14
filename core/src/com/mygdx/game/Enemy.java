package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends CharacterBase {
    private Texture hp_texture;
    Direction direction;
    private int max_hp;
    private int curr_hp;
    private float timer;
    private float max_timer;
    private boolean active;

    public Enemy(MyGame mygame){
        game = mygame;
        gen_texture = new Texture("purple_ghost_down.png");
        eat_texture = new Texture("ghost_alert.png");
        hp_texture = new Texture("hpbar.png");
        curr_texture = gen_texture;
        position = new Vector2(500, 500);
        speed = 80;
        max_hp = 5;
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

    public void setRandDirection() {
        direction = Direction.values()[MathUtils.random(0, Direction.values().length - 2)];
    }
    public void activate(float x, float y) {
        active = true;
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
            max_timer = MathUtils.random(2, 4);
            setRandDirection();
        }
        move(direction, dt);
        //position.add(speed*direction.getVx()*dt, speed*direction.getVy()*dt);
        BordersCollision();
    }
}
