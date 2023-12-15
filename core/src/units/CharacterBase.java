package units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import sys.GameScreen;

public abstract class CharacterBase {
    public static final int SIZE = 32;
    protected GameScreen game;
    protected TextureRegion gen_texture;
    protected TextureRegion eat_texture;
    protected TextureRegion curr_texture;
    protected TextureRegion death[];
    protected Vector2 position;
    protected Vector2 tmp;
    protected float timer;
    protected float max_timer;
    protected float speed;


    public Vector2 getPosition() {
        return position;
    }

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
            if (this instanceof Pacman) {
                if (game.getMap().isBullet(position.x, position.y, SIZE/2)){
                    ((Pacman) this).addBullet();
                }
                ((Pacman) this).changeTexture(dt);
            }
        }

    }

    public void stop() { speed = 0; }

    public abstract void destroy();
}

