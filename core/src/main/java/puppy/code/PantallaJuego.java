package puppy.code;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();


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
		
		fondoGalaxy = new Texture(Gdx.files.internal("espaceee.jpg"));
		fondoGalaxy.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        nave.setVidas(vidas);
        //crear asteroides
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		
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
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            for (int j = 0; j < balls1.size(); j++) {    
		              if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play();
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : balls1) {
		          ball.update();
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<balls1.size();i++) {
		    	Ball2 ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	      }
	      //dibujar balas
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < balls1.size(); i++) {
	    	    Ball2 b=balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
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
	      //nivel completado
	      if (balls1.size()==0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score, 
					velXAsteroides+3, velYAsteroides+3, cantAsteroides+10);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
    
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

    private Nave4 nave;
    private ArrayList<Ball2> balls1 = new ArrayList<>();
    private ArrayList<Ball2> balls2 = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();
    private ArrayList<Heart> corazones = new ArrayList<>(); // <--- NUEVO

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
        camera.setToOrtho(false, 800, 640);
        //inicializar assets; musica de fondo y efectos de sonido
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        // cargar imagen de la nave, 64x64   
        nave = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30, new Texture(Gdx.files.internal("MainShip3.png")),
                        Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                        new Texture(Gdx.files.internal("Rocket2.png")),
                        Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
        nave.setVidas(vidas);

        //crear asteroides (rocas)
        for (int i = 0; i < cantAsteroides; i++) {
            Ball2 bb = new Ball2((int)(Math.random() * Gdx.graphics.getWidth()),
                    50 + (int)(Math.random() * (Gdx.graphics.getHeight() - 50)),
                    20 + (int)(Math.random() * 10), velXAsteroides + (int)(Math.random() * 4), velYAsteroides + (int)(Math.random() * 4),
                    new Texture(Gdx.files.internal("aGreyMedium4.png")));
            balls1.add(bb);
            balls2.add(bb);
        }

        //crear corazones
        Texture corazonTexture = new Texture(Gdx.files.internal("heart.png"));
        for (int i = 0; i < 3; i++) {
            Heart heart = new Heart((int)(Math.random() * Gdx.graphics.getWidth()),
                    50 + (int)(Math.random() * (Gdx.graphics.getHeight() - 50)),
                    20, velXAsteroides + (int)(Math.random() * 4), velYAsteroides + (int)(Math.random() * 4),
                    corazonTexture);
            corazones.add(heart);
        }
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:" + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore:" + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();
        if (!nave.estaHerido()) {
            // colisiones entre balas y asteroides y su destruccion  
            for (int i = 0; i < balas.size(); i++) {
                Bullet b = balas.get(i);
                b.update();
                for (int j = 0; j < balls1.size(); j++) {    
                    if (b.checkCollision(balls1.get(j))) {          
                        explosionSound.play();
                        balls1.remove(j);
                        balls2.remove(j);
                        j--;
                        score += 10;
                    }
                }
                // colisiones con corazones
                for (int h = 0; h < corazones.size(); h++) {
                    Heart heart = corazones.get(h);
                    if (b.getArea().overlaps(heart.getArea())) {
                        corazones.remove(h);
                        balas.remove(i);
                        h--; i--;
                        break;
                    }
                }
                //   b.draw(batch);
                if (b.isDestroyed()) {
                    balas.remove(b);
                    i--; //para no saltarse 1 tras eliminar del arraylist
                }
            }
            //actualizar movimiento de asteroides dentro del area
            for (int i = 0; i < balls1.size(); i++) {
                balls1.get(i).update();
            }
            //actualizar movimiento de corazones
            for (int i = 0; i < corazones.size(); i++) {
                corazones.get(i).update();
            }
            //colisiones entre asteroides y sus rebotes  
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
        //dibujar balas
        for (int i = 0; i < balas.size(); i++) {
            balas.get(i).draw(batch);
        }
        nave.draw(batch, this);
        //dibujar asteroides y manejar colision con nave
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 b = balls1.get(i);
            b.draw(batch);
            //perdió vida o game over
            if (nave.checkCollision(b)) {
                balls1.remove(i);
                balls2.remove(i);
                i--;
            }
        }
        //dibujar corazones y colision con nave para sumar vidas
        for (int i = 0; i < corazones.size(); i++) {
            Heart heart = corazones.get(i);
            heart.draw(batch);
            if (nave.getArea().overlaps(heart.getArea())) {
                if (nave.getVidas() < 3) nave.setVidas(nave.getVidas() + 1);
                corazones.remove(i);
                i--;
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
        //nivel completado
        if (balls1.size() == 0) {
            Screen ss = new PantallaJuego(game, ronda + 1, nave.getVidas(), score,
                    velXAsteroides + 3, velYAsteroides + 3, cantAsteroides + 10);
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
        gameMusic.play();
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
        this.explosionSound.dispose();
        this.gameMusic.dispose();
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	    batch.setProjectionMatrix(camera.combined);
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
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
   
}
