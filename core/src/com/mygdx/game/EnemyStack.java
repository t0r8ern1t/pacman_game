package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EnemyStack {
    private Enemy[] enemies;

    public EnemyStack(MyGame game) {
        enemies = new Enemy[5];
        for (int i = 0; i < enemies.length; ++i){
            enemies[i] = new Enemy(game);
        }
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void activate(float x, float y) {
            for (int i = 0; i < enemies.length; ++i) {
                if (!enemies[i].isActive()) {
                    enemies[i].activate(x, y);
                    break;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < enemies.length; ++i) {
            if (enemies[i].isActive()) {
                enemies[i].render(batch);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < enemies.length; ++i) {
            if (enemies[i].isActive()) {
                enemies[i].update(dt);
            }
        }
    }
}
