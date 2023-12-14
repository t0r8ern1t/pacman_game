package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletStack {
    private Texture bullet_texture;
    private int bullets_collected;
    private Bullet[] bullets;

    public BulletStack() {
        bullet_texture = new Texture("dot.png");
        bullets_collected = 10;
        bullets = new Bullet[5];
        for (int i = 0; i < bullets.length; ++i){
            bullets[i] = new Bullet();
        }
    }

    public Bullet[] getBullets() {
        return bullets;
    }

    public void activate(int fire_angle, float x, float y) {
        if (bullets_collected > 0) {
            for (int i = 0; i < bullets.length; ++i) {
                if (!bullets[i].isActive()) {
                    bullets[i].activate(fire_angle, x, y);
                    bullets_collected -= 1;
                    break;
                }
            }
        }
    }

    public void bulletCollected() {
        bullets_collected += 1;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < bullets.length; ++i) {
            if (bullets[i].isActive()) {
                batch.draw(bullet_texture, bullets[i].getPosition().x - bullets[i].SIZE / 2, bullets[i].getPosition().y - bullets[i].SIZE / 2);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < bullets.length; ++i) {
            if (bullets[i].isActive()) {
                bullets[i].update(dt);
            }
        }
    }
}
