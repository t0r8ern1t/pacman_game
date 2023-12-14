package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public abstract class CharacterBase {
    public static final int SIZE = 32;

    protected MyGame game;
    protected Texture gen_texture;
    protected Texture eat_texture;
    protected Texture curr_texture;
    protected Vector2 position;
    protected Vector2 tmp;
    protected float speed;
    public void BordersCollision() {
        if (position.x - SIZE /2 <= 0) position.x = SIZE/2;
        if (position.x + SIZE/2 >= Gdx.graphics.getWidth()) position.x = Gdx.graphics.getWidth() - SIZE/2;
        if (position.y - SIZE/2 <= 0) position.y = SIZE/2;
        if (position.y + SIZE/2 >= Gdx.graphics.getHeight()) position.y = Gdx.graphics.getHeight() - SIZE/2;
    }

    public void move(Direction direction, float dt) {
        tmp.set(position);
        tmp.add(speed*direction.getVx()*dt, speed*direction.getVy()*dt);
        if (game.getMap().isAreaClear(tmp.x, tmp.y, (SIZE+7)/2)){
            tmp.add(speed*direction.getVx()*dt, speed*direction.getVy()*dt);
            position.set(tmp);
        }
        if (this instanceof Pacman) {
            if (game.getMap().isBullet(position.x, position.y, SIZE/2)){
                System.out.println(1);
            }
        }
    }

    public Vector2 getPosition() {
        return position;
    }


    public void stop() { speed = 0; }

    public abstract void destroy();
}

