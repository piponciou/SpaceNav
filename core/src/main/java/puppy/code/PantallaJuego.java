package puppy.code;

import java.util.ArrayList;
import java.util.Random; 

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;

public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides;	
	private int velYAsteroides;	
	private int cantAsteroides;
	private Viewport viewport;	
	private Texture fondoGalaxy;
    private float scrollTimer = 0.0f;	
    private float scrollSpeed = 70.0f;	
	
	private Nave4 nave;
	private HealthHUD hud;
	private	 ArrayList<Ball2> balls1 = new ArrayList<>();
	private	 ArrayList<Ball2> balls2 = new ArrayList<>();
	private	 ArrayList<Bullet> balas = new ArrayList<>();
	private ArrayList<Heart> corazones = new ArrayList<>(); 
	private ArrayList<EscudoNave> escudos = new ArrayList<>();

	private Boss bossFinal = null;
	private boolean bossActivo = false;
	
	private ShapeRenderer shapeRenderer;

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,	
			int velXAsteroides, int velYAsteroides, int cantAsteroides) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		
		batch = game.getBatch();
		camera = new OrthographicCamera();
		viewport = new StretchViewport(1200, 800, camera);
		
		
		Recursos res = Recursos.getInstance();
		hud = new HealthHUD(res.txCorazon);
		
		fondoGalaxy = res.fondoGalaxy;
		fondoGalaxy.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		explosionSound = res.soundExplosion;
		explosionSound.setVolume(1,0.5f);
		gameMusic = res.musicJuego; 
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30, res.txNave,
	    				res.soundHerido,	
	    				res.txBala,	
	    				res.soundBala);	
        nave.setVidas(vidas);

        if (score >= 300) {
            nave.setEstrategiaDisparo(new DisparoTriple(res.txBala, res.soundBala));
        } else if (score >= 100) {
            nave.setEstrategiaDisparo(new DisparoDoble(res.txBala, res.soundBala));
        }

        if (ronda == 5) {
            // RONDA DEL JEFE 
            bossActivo = true;

            bossFinal = new Boss(
                    Gdx.graphics.getWidth() / 2 - 75, 
                    Gdx.graphics.getHeight() - 160, 
                    res.txJefe,
                    res.txAsteroide
            );

            
        } else {
            Random r = new Random();
            for (int i = 0; i < cantAsteroides; i++) {
                int xRandom = 10 + r.nextInt(Gdx.graphics.getWidth() - 60);
                int yRandom = 50 + r.nextInt(Gdx.graphics.getHeight() - 100);
                Ball2 bb = new Ball2(xRandom, yRandom,
                        20 + r.nextInt(10), velXAsteroides + r.nextInt(4), velYAsteroides + r.nextInt(4),
                        res.txAsteroide);
                balls1.add(bb);
                balls2.add(bb);
            }
            
            Texture corazonTexture = res.txCorazon;
            for (int i = 0; i < 3; i++) {
                Heart heart = new Heart((int) (Math.random() * Gdx.graphics.getWidth()),
                        50 + (int) (Math.random() * (Gdx.graphics.getHeight() - 50)),
                        20, velXAsteroides + (int) (Math.random() * 4), velYAsteroides + (int) (Math.random() * 4),
                        corazonTexture);
                corazones.add(heart);
            }
    	    
    	    Texture escudoTexture = res.txEscudo; 
            int cantEscudos = r.nextInt(2) + 3; 
            for (int i = 0; i < cantEscudos; i++) {
                EscudoNave shield = new EscudoNave(r.nextInt(Gdx.graphics.getWidth()),
                        50 + r.nextInt(Gdx.graphics.getHeight() - 50),
                        20, velXAsteroides + (int)(Math.random() * 4), velYAsteroides + (int)(Math.random() * 4),
                        escudoTexture);
                escudos.add(shield);
            }
        }
        shapeRenderer = new ShapeRenderer();
        
	}
        
	
	public void dibujaEncabezado() {
	  
	    hud.draw(batch, nave.getVidas());

	    game.getFont().getData().setScale(2f);		
	    game.getFont().draw(batch, "Ronda: " + ronda, 150, 770); 
	    game.getFont().draw(batch, "Score: " + this.score, Gdx.graphics.getWidth() / 2 - 100, 770);
	}
	
	@Override

	public void render(float delta) {
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    camera.update();
	    batch.setProjectionMatrix(camera.combined);

	    scrollTimer += delta * scrollSpeed;
	    if (scrollTimer > fondoGalaxy.getHeight()) {
	        scrollTimer = 0.0f;
	    }

	    batch.begin();
	    batch.draw(fondoGalaxy, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight(), 0, (int)scrollTimer, fondoGalaxy.getWidth(), fondoGalaxy.getHeight(), false, false);

	    dibujaEncabezado();

	    if (!nave.estaHerido()) {
	        for (int i = 0; i < balas.size(); i++) {
	            Bullet b = balas.get(i);
	            b.update();

	            if (bossActivo && bossFinal != null && b.getArea().overlaps(bossFinal.getArea())) {
	                explosionSound.play();
	                boolean muerto = bossFinal.recibirDano();
	                score += 50;
	                balas.remove(i);
	                i--;
	                if (muerto) {
	                    bossActivo = false;
	                    bossFinal = null;
	                    score += 1000;
	                }
	                continue;
	            }

	            for (int j = 0; j < balls1.size(); j++) {
	                if (b.checkCollision(balls1.get(j))) {
	                    explosionSound.play();
	                    balls1.remove(j);
	                    balls2.remove(j);
	                    j--;
	                    score += 10;

	                    if (score >= 300) {
	                        nave.setEstrategiaDisparo(new DisparoTriple(Recursos.getInstance().txBala, Recursos.getInstance().soundBala));
	                    } else if (score >= 100) {
	                        nave.setEstrategiaDisparo(new DisparoDoble(Recursos.getInstance().txBala, Recursos.getInstance().soundBala));
	                    }

	                    balas.remove(i);
	                    i--;
	                    break;
	                }
	            }

	            if (i >= 0) {
	                for (int h = 0; h < corazones.size(); h++) {
	                    Heart heart = corazones.get(h);
	                    if (nave.getArea().overlaps(heart.getArea())) {
	                        heart.activate(nave);
	                        if (heart.isConsumed()) {
	                            corazones.remove(h);
	                            h--;
	                        }
	                    }
	                }
	            }

	            if (i >= 0) {
	                for (int k = 0; k < escudos.size(); k++) {
	                    EscudoNave pp = escudos.get(k);
	                    if (b.getArea().overlaps(pp.getArea())) {
	                        escudos.remove(k);
	                        balas.remove(i);
	                        k--;
	                        i--;
	                        break;
	                    }
	                }
	            }

	            if (i >= 0 && b.isDestroyed()) {
	                balas.remove(b);
	                i--;
	            }
	        }

	        for (Ball2 ball : balls1) {
	            ball.update();
	        }

	        for (Heart heart : corazones) {
	            heart.update();
	        }

	        for (EscudoNave shield : escudos) {
	            shield.update();
	        }

	        if (bossActivo && bossFinal != null) {
	            bossFinal.update();
	            bossFinal.intentarDisparar(this, delta);
	        }

	        for (int i = 0; i < balls1.size(); i++) {
	            Ball2 ball1 = balls1.get(i);
	            for (int j = 0; j < balls2.size(); j++) {
	                Ball2 ball2 = balls2.get(j);
	                if (i < j) {
	                    ball1.checkCollision(ball2);
	                }
	            }
	        }
	    }

	    for (Bullet b : balas) {
	        b.draw(batch);
	    }

	    nave.draw(batch, this);

	    if (bossActivo && bossFinal != null) {
	        bossFinal.draw(batch);
	        if (!nave.estaHerido() && nave.getArea().overlaps(bossFinal.getArea())) {
	            nave.setVidas(0);
	        }
	    }

	    for (int i = 0; i < balls1.size(); i++) {
	        Ball2 b = balls1.get(i);
	        b.draw(batch);
	        if (nave.checkCollision(b)) {
	            balls1.remove(i);
	            balls2.remove(i);
	            i--;
	        }
	    }

	    for (int i = 0; i < corazones.size(); i++) {
	        Heart heart = corazones.get(i);
	        heart.draw(batch);
	        if (nave.getArea().overlaps(heart.getArea())) {
	            if (nave.getVidas() < 3) nave.setVidas(nave.getVidas() + 1);
	            corazones.remove(i);
	            i--;
	        }
	    }

	    for (int i = escudos.size() - 1; i >= 0; i--) {
	        EscudoNave shield = escudos.get(i);
	        shield.draw(batch);
	        if (nave.getArea().overlaps(shield.getArea())) {
	            shield.activate(nave);
	            if (shield.isConsumed()) {
	                escudos.remove(i);
	            }
	        }
	    }

	    if (nave.estaDestruido()) {
	        if (score > game.getHighScore())
	            game.setHighScore(score);
	        Screen ss = new PantallaGameOver(game);
	        ss.resize(1200, 800);
	        game.setScreen(ss);
	        dispose();
	    }

	    batch.end();
	    if (bossActivo && bossFinal != null) {
	        shapeRenderer.setProjectionMatrix(camera.combined);
	        shapeRenderer.begin(ShapeType.Filled);

	        shapeRenderer.setColor(Color.RED);

	        float largoBarra = 300;
	        float altoBarra = 20;
	        float xBarra = Gdx.graphics.getWidth()/2 - largoBarra/2;
	        float yBarra = Gdx.graphics.getHeight() - 30;
	        shapeRenderer.rect(xBarra, yBarra, largoBarra, altoBarra);

	        shapeRenderer.setColor(Color.GREEN);

	        float porcentajeVida = (float)bossFinal.getVida() / bossFinal.getVidaMaxima();
	        shapeRenderer.rect(xBarra, yBarra, largoBarra * porcentajeVida, altoBarra);
	        
	        shapeRenderer.end();
	    }
	    
	    if (ronda != 5 && balls1.size() == 0) {
	        Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score, velXAsteroides + 1, velYAsteroides + 1, cantAsteroides + 5);
	        ss.resize(1200, 800);
	        game.setScreen(ss);
	        dispose();
	    } else if (ronda == 5 && !bossActivo && bossFinal == null) {
	    	Screen ss = new PantallaVictoria(game, this.score); 
	        
	        ss.resize(1200, 800);
	        game.setScreen(ss);
	        dispose();
	    }
	}
	
    
    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }
	
	@Override
	public void show() {
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	    batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		this.gameMusic.stop();
		this.shapeRenderer.dispose();
	}
	public void agregarEnemigo(Ball2 b) {
	    balls1.add(b);
	    balls2.add(b);
	}
	
}