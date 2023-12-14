package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Pacman pacman;
	private BulletStack bullet_stack;
	private EnemyStack enemy_stack;
	private Map map;
	private float game_timer;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		pacman = new Pacman(this);
		bullet_stack = new BulletStack();
		enemy_stack = new EnemyStack(this);
		map = new Map();
		for (int i = 0; i < enemy_stack.getEnemies().length; ++i) {
			int x = MathUtils.random(0, Gdx.graphics.getWidth());
			int y = MathUtils.random(0, Gdx.graphics.getHeight());
			while (!map.isAreaClear(x, y, (enemy_stack.getEnemies()[0].SIZE+7)/2)) {
				x = MathUtils.random(0, Gdx.graphics.getWidth());
				y = MathUtils.random(0, Gdx.graphics.getHeight());
			}
			enemy_stack.activate(x, y);
		}
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		map.render(batch);
		pacman.render(batch);
		enemy_stack.render(batch);
		bullet_stack.render(batch);
		batch.end();
	}

	public Map getMap() { return map; }
	public BulletStack getBulletstack() { return bullet_stack; }
	public void update(float dt) {
		pacman.update(dt);
		enemy_stack.update(dt);
		bullet_stack.update(dt);
		checkCollisions();
	}

	public void checkEnemyCollisions() {
		for (int j = 0; j < enemy_stack.getEnemies().length; ++j) {
			Enemy enemy = enemy_stack.getEnemies()[j];
			if (enemy.isActive()) {
				for (int i = 0; i < bullet_stack.getBullets().length; ++i) {
					Bullet bullet = bullet_stack.getBullets()[i];
					if (bullet.isActive()) {
						if (enemy.getPosition().dst(bullet.getPosition()) <= enemy.SIZE/2) {
							enemy.damageTaken();
							bullet.deactivate();
						}
					}
				}
				for (int i = 0; i < enemy_stack.getEnemies().length; ++i) {
					if (i != j) {
						Enemy enemy2 = enemy_stack.getEnemies()[i];
						if (enemy2.isActive()) {
							if (enemy.getPosition().dst(enemy2.getPosition()) <= enemy.SIZE) {
								enemy.setRandDirection();
							}
						}
					}
				}
				if (enemy.getPosition().dst(pacman.getPosition()) <= enemy.SIZE) {
					/*pacman.damageTaken();
					enemy.damageMade();
					pacman.stop();
					enemy.stop();*/
				}
			}
		}
	}
	public void checkCollisions() {
		for (int i = 0; i < bullet_stack.getBullets().length; ++i) {
			if (bullet_stack.getBullets()[i].isActive()) {
				map.checkBulletWallCollision(bullet_stack.getBullets()[i]);
			}
		}
		checkEnemyCollisions();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
