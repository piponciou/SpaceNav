package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite; 


public class PantallaGameOver implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Texture fondoGalaxy;
	private float scrollTimer = 0.0f; 
	private float scrollSpeed = 40.0f; 
	private BitmapFont tituloFont;
	private Texture explosionTexture; 
	private Sprite EXPLOSIONA;
	private static final float opacidad = 0.3f;
	

	public PantallaGameOver(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
		
		fondoGalaxy = new Texture(Gdx.files.internal("espaceee.jpg")); 
		fondoGalaxy.setWrap(TextureWrap.Repeat, TextureWrap.Repeat); 
		
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
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);
		
		scrollTimer += delta * scrollSpeed;
	    if (scrollTimer > fondoGalaxy.getHeight()) 
	        scrollTimer = 0.0f;

		game.getBatch().begin();
		
		game.getBatch().draw(fondoGalaxy,0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0,(int)scrollTimer,fondoGalaxy.getWidth(),fondoGalaxy.getHeight(), false, false);
		
		EXPLOSIONA.draw(game.getBatch());
		
		game.getFont().getData().setScale(3f); 
		tituloFont.draw(game.getBatch(), "HAS SIDO CAPTURADO!", 130, 550, 970, 1, true); 
		String mensajeFinal = "No lograste escapar de la defensa planetaria.\n" + 
			    "Los asteroides fueron más rápidos que tu habilidad.";
		game.getFont().draw(game.getBatch(), mensajeFinal,80,360);
		//game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para reiniciar ...", 110, 300);
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
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