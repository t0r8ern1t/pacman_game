package units;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import sys.GameScreen;

public class EnemyStack {
    private Enemy[] enemies;

    public EnemyStack(GameScreen game_screen, TextureAtlas atlas) {
        enemies = new Enemy[5];
        for (int i = 0; i < enemies.length; ++i){
            enemies[i] = new Enemy(game_screen, atlas);
        }
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void deactivateAll() {
        for (int i = 0; i < enemies.length; ++i) {
            enemies[i].stop();
        }
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
