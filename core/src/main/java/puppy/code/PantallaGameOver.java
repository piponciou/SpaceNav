package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.g2d.Sprite; 
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PantallaGameOver implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Texture fondoGalaxy; 
	private BitmapFont tituloFont;
	private Texture explosionTexture; 
	private Sprite EXPLOSIONA;
	private Viewport viewport;
	private static final float opacidad = 0.3f;
	

	public PantallaGameOver(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		viewport = new StretchViewport(1200, 800, camera);
		
		fondoGalaxy = new Texture(Gdx.files.internal("espaceee.jpg")); 
		
		tituloFont = new BitmapFont((Gdx.files.internal("foont.fnt")));
		tituloFont.getData().setScale(4f);
		
		explosionTexture = new Texture(Gdx.files.internal("explosion_pixelart.png")); 
        EXPLOSIONA = new Sprite(explosionTexture);
        
        EXPLOSIONA.setSize(1200, 800); 
        EXPLOSIONA.setPosition(0, 0);
        
        EXPLOSIONA.setColor(1f, 1f, 1f, opacidad);
	}

	@Override
	public void render(float delta) { 

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);
		
		game.getBatch().begin();
		game.getBatch().draw(fondoGalaxy, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		
		EXPLOSIONA.draw(game.getBatch());
		
		game.getFont().getData().setScale(3f); 
		tituloFont.draw(game.getBatch(), "HAS SIDO CAPTURADO!", 130, 550, 970, 1, true); 
		String mensajeFinal = "No lograste escapar de la defensa planetaria.\n" + 
			    "Los asteroides fueron más rápidos que tu habilidad.";
		game.getFont().draw(game.getBatch(), mensajeFinal,80,360);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 80, 150);
	
		game.getBatch().end();
		
		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,1,1,10);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
 
	
	@Override
	public void show() {
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	    game.getBatch().setProjectionMatrix(camera.combined); 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
   
}