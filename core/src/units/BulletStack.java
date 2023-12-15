package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BulletStack {
    private TextureRegion bullet_texture;
    private int bullets_collected;
    private Bullet[] bullets;

    public void setBulletsCollected(int n) { bullets_collected = n; }

    public Bullet[] getBullets() {
        return bullets;
    }

    public BulletStack(TextureAtlas atlas) {
        bullet_texture = atlas.findRegion("dot");
        bullets_collected = 0;
        bullets = new Bullet[5];
        for (int i = 0; i < bullets.length; ++i){
            bullets[i] = new Bullet();
        }
    }

    public boolean activate(int fire_angle, float x, float y) {
        if (bullets_collected > 0) {
            for (int i = 0; i < bullets.length; ++i) {
                if (!bullets[i].isActive()) {
                    bullets[i].activate(fire_angle, x, y);
                    bullets_collected -= 1;
                    return true;
                }
            }
        }
        return false;
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
