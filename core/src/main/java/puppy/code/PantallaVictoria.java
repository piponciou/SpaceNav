package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;

public class PantallaVictoria implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture fondoGalaxy;
    private BitmapFont tituloFont;
    private int scoreFinal;
    private float tiempoEspera = 0f;

    public PantallaVictoria(SpaceNavigation game, int scoreFinal) {
        this.game = game;
        this.scoreFinal = scoreFinal;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(1200, 800, camera);

        Recursos res = Recursos.getInstance();
        fondoGalaxy = res.fondoGalaxy;
        tituloFont = res.tituloFont;
    }

    @Override
    public void render(float delta) {
    	
    	tiempoEspera += delta;
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        game.getBatch().draw(fondoGalaxy, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        tituloFont.getData().setScale(5f); 
        tituloFont.draw(game.getBatch(), "¡VICTORIA!", 0, 720, 1200, Align.center, true);


        game.getFont().getData().setScale(2f);
        
        String historia = "Has derribado la nave nodriza, pero de los escombros\n" +
                          "emerge una transmisión familiar...\n\n" +
                          "\"¡Jajaja! Nada mal, hijo. Eres digno de llevar mi apellido.\"\n" +
                          "¡El piloto era Claudio Cubillos! Todo fue una prueba final.\n\n" +
                          "El artefacto es tuyo. Eres el nuevo Rey de los Ladrones.";

        game.getFont().draw(game.getBatch(), historia, 0, 500, 1200, Align.center, true);

        game.getFont().getData().setScale(3f);
        game.getFont().draw(game.getBatch(), "Puntaje Final: " + scoreFinal, 0, 200, 1200, Align.center, true);

        game.getFont().getData().setScale(1.5f);
        game.getFont().draw(game.getBatch(), "Pincha en cualquier lado para volver a jugar...", 0, 100, 1200, Align.center, true);

        game.getBatch().end();

        if (tiempoEspera > 2.0f) {
			if(Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
				Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
				ss.resize(1200, 800);
				game.setScreen(ss);
				dispose();
			
			}
        }
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        game.getBatch().setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}