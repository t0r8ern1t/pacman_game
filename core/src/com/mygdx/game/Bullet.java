package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public static final int SIZE = 16;
    private Vector2 position;
    private Vector2 speed;
    private int value;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() { return position; }

    public int getValue() { return value; }

    public Bullet() {
        position = new Vector2(0, 0);
        speed = new Vector2(0, 0);
        value = 1;
        active = false;
    }

    public void activate(int fire_angle, float x, float y) {
        active = true;
        position.x = x;
        position.y = y;
        switch (fire_angle) {
            case 0:
                speed.x = 1;
                speed.y = 0;
                break;
            case 90:
                speed.x = 0;
                speed.y = 1;
                break;
            case 180:
                speed.x = -1;
                speed.y = 0;
                break;
            case 270:
                speed.x = 0;
                speed.y = -1;
        }
    }

    public void deactivate() {
        active = false;
    }

    public void update(float dt) {
        position.mulAdd(speed, 300*dt);
        if (position.x < 0 || position.x > 1280 || position.y < 0 || position.y > 720) {
            deactivate();
        }
    }
}
