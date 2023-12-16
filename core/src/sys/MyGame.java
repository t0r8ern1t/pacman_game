package sys;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/*TODO
	пересчет размера экрана для кнопок и чтобы призраки не застревали в текстурках при масштабировании
	вьюпорт страшная вещь я не понимаю как с ним работать,,,,,,,,,,,,,,,
	for now масштабирование отключено
	поменять кнопку паузы в GameScreen там стоит свой туду коммент
	пофиксить анимацию съедания чтобы она запускалась при контакте с самой пулей а не с квадратом 40х40
	еще чтобы она не запускалась при обычном движении

на будущее:
	добавить какие-нибудь баффы при убийстве призраков типа если убил зеленого получаешь щит если синего - ускорение
	общий скор и выгрузку в файл для сохранения результатов
	чтобы уровень ресетился автоматически при победе и игра продолжалась до первой смерти
	соответственно возрастание уровня сложности (по количеству призраков и их скорости/хп или ограничить количество пуль на поле)
 */

public class MyGame extends Game {

	private SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		ScreenManager.getInstance().init(this, batch);
		ScreenManager.getInstance().setScreen(ScreenManager.ScreenType.MENU);
	}

	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
