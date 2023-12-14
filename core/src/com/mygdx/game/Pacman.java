package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pacman extends CharacterBase{
    protected float angle;
    Direction direction;
    public Pacman(MyGame mygame){
        game = mygame;
        gen_texture = new Texture("Pacman1.png");
        eat_texture = new Texture("Pacman2.png");
        curr_texture = gen_texture;
        position = new Vector2(100, 100);
        speed = 100;
        angle = 0;
        tmp = new Vector2(0, 0);
        direction = Direction.STOP;
    }

    public void render(SpriteBatch batch) {
        batch.draw(curr_texture, position.x-SIZE/2, position.y-SIZE/2, SIZE/2, SIZE/2, SIZE, SIZE, 1, 1, angle, 0, 0, SIZE, SIZE, false, false);
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
    public void fire() {
        game.getBulletstack().activate((int)angle, position.x, position.y);
    }

    public void damageTaken() {}

    @Override
    public void destroy() {}
}
