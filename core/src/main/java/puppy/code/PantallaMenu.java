package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private Viewport viewport;
	private Texture fondoGalaxy;
	private BitmapFont tituloFont1;
	private Texture naveInicio; 
	private Sprite VIAJE;
	private static final float opacidad = 0.3f;

	private OrthographicCamera camera;

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		viewport = new StretchViewport(1200, 800, camera);
		
		fondoGalaxy = new Texture(Gdx.files.internal("espaceee.jpg"));
		
		tituloFont1 = new BitmapFont((Gdx.files.internal("foontTitulo.fnt")));
		tituloFont1.getData().setScale(4f);
		
		naveInicio = new Texture(Gdx.files.internal("NAVE INICIO.png")); 
        VIAJE = new Sprite(naveInicio);
        
        VIAJE.setSize(1200, 800); 
        VIAJE.setPosition(0, 0);		
        
        VIAJE.setColor(1f, 1f, 1f, opacidad);
	
	}
	
	public void resize(int width, int height) {
	    viewport.update(width, height, true);
	    game.getBatch().setProjectionMatrix(camera.combined); 
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
		game.getFont().getData().setScale(3f); 

		game.getBatch().draw(fondoGalaxy, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		VIAJE.draw(game.getBatch());
		tituloFont1.draw(game.getBatch(), "COSMIC THIEF JOURNEY!", 130, 550, 970, 1, true); 

		game.getFont().draw(game.getBatch(), "Tu aventura como ladrón ha comenzado.", 100, 350);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para iniciar ...", 100, 300);
	
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