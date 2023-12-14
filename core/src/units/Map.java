package units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import sys.WallCollection;

public class Map {
    public static final int CELL_SIZE = 40;
    public static final int BULLET_SIZE = 16;
    public static final int X_SIZE = 32;
    public static final int Y_SIZE = 18;
    private TextureRegion wall_texture;
    private TextureRegion bullet_texture;
    private int obstacles[][];

    public Map(TextureAtlas atlas){
        wall_texture = atlas.findRegion("square_block");
        bullet_texture = atlas.findRegion("dot");
        obstacles = new int[X_SIZE][Y_SIZE];
        setRandomWalls();
        setBullets();
    }

    public void regenerate() {
        setRandomWalls();
        setBullets();
    }

    public void setRandomWalls() {
        for (int i = 0; i < X_SIZE-5; i += 4) {
            for (int j = 0; j < Y_SIZE-5; j += 4) {
                int wall_type = MathUtils.random(0, WallCollection.values().length - 1);
                int[][] tmp = WallCollection.values()[wall_type].getWall();
                for (int ii = 0; ii <= 3; ++ii) {
                    for (int jj = 0; jj <= 3; ++jj) {
                        if (i+ii != 2 && j+jj != 15)
                            obstacles[i+ii][j+jj] = tmp[ii][jj];
                    }
                }
            }
        }
        for (int i = 0; i < X_SIZE; ++i) {
            obstacles[i][0] = 1;
            obstacles[i][Y_SIZE - 1] = 1;
        }
        for (int i = 0; i < Y_SIZE; ++i) {
            obstacles[0][i] = 1;
            obstacles[X_SIZE - 1][i] = 1;
        }
    }

    public void setBullets() {
        for (int i = 1; i < X_SIZE - 1; ++i){
            for (int j = 1; j < Y_SIZE - 1; ++j) {
                if (obstacles[i][j] != 1) {
                    obstacles[i][j] = 2;
                }
            }
        }
    }

    public boolean isBullet(float x, float y, float half_size) {
        int left_x = (int)((x - half_size)/CELL_SIZE);
        int right_x = (int)((x + half_size)/CELL_SIZE);
        int down_y = (int)((y - half_size)/CELL_SIZE);
        int up_y = (int)((y + half_size)/CELL_SIZE);
        if (left_x < 0) left_x = 0;
        if (right_x >= X_SIZE) right_x = X_SIZE - 1;
        if (down_y < 0) down_y = 0;
        if (up_y >= Y_SIZE) up_y = Y_SIZE - 1;
        for (int i = left_x; i <= right_x; ++i) {
            for (int j = down_y; j <= up_y; ++j) {
                if (obstacles[i][j] == 2) {
                    obstacles[i][j] = 0;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isAreaClear(float x, float y, float half_size) {
        int left_x = (int)((x - half_size)/CELL_SIZE);
        int right_x = (int)((x + half_size)/CELL_SIZE);
        int down_y = (int)((y - half_size)/CELL_SIZE);
        int up_y = (int)((y + half_size)/CELL_SIZE);
        if (left_x < 0) left_x = 0;
        if (right_x >= X_SIZE) right_x = X_SIZE - 1;
        if (down_y < 0) down_y = 0;
        if (up_y >= Y_SIZE) up_y = Y_SIZE - 1;

        for (int i = left_x; i <= right_x; ++i) {
            for (int j = down_y; j <= up_y; ++j) {
                if (obstacles[i][j] == 1)
                    return false;
            }
        }
        return true;
    }
    public void checkBulletWallCollision(Bullet bullet) {
        int x = (int)(bullet.getPosition().x / CELL_SIZE);
        int y = (int)(bullet.getPosition().y / CELL_SIZE);
        if (x >= 0 && x < X_SIZE && y >= 0 && y < Y_SIZE) {
            if (obstacles[x][y] == 1) bullet.deactivate();
        }
    }
    public void render(SpriteBatch batch) {
        for (int i = 0; i < X_SIZE; ++i) {
            for (int j = 0; j < Y_SIZE; ++j) {
                if (obstacles[i][j] == 1) {
                    batch.draw(wall_texture, i * CELL_SIZE, j * CELL_SIZE);
                } else if (obstacles[i][j] == 2) {
                    batch.draw(bullet_texture, i * CELL_SIZE + (CELL_SIZE - BULLET_SIZE) / 2, j * CELL_SIZE + (CELL_SIZE - BULLET_SIZE) / 2);
                }
            }
        }
    }
}
