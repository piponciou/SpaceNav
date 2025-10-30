package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Recursos {

    private static final Recursos instance = new Recursos();

    public Texture fondoGalaxy;
    public Texture txNave;
    public Texture txBala;
    public Texture txAsteroide;
    public Texture txCorazon;
    public Texture txEscudo;
    public Texture txExplosionGameOver;
    
    public Sound soundExplosion;
    public Sound soundHerido;
    public Sound soundBala;
    
    public Music musicJuego;
    
    public BitmapFont font;
    public BitmapFont tituloFont;

    private Recursos() {
        fondoGalaxy = new Texture(Gdx.files.internal("espaceee.jpg"));
        txNave = new Texture(Gdx.files.internal("MainShip3.png"));
        txBala = new Texture(Gdx.files.internal("Rocket2.png"));
        txAsteroide = new Texture(Gdx.files.internal("aGreyMedium4.png"));
        txCorazon = new Texture(Gdx.files.internal("heart.png"));
        txEscudo = new Texture(Gdx.files.internal("ESCUDOFINAL.png"));
        txExplosionGameOver = new Texture(Gdx.files.internal("explosion_pixelart.png"));
        
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        soundHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        soundBala = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        
        musicJuego = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
        
        font = new BitmapFont(); 
        tituloFont = new BitmapFont(Gdx.files.internal("foont.fnt"));
    }

    public static Recursos getInstance() {
        return instance;
    }

    public void dispose() {
        fondoGalaxy.dispose();
        txNave.dispose();
        txBala.dispose();
        txAsteroide.dispose();
        txCorazon.dispose();
        txEscudo.dispose();
        txExplosionGameOver.dispose();
        
        soundExplosion.dispose();
        soundHerido.dispose();
        soundBala.dispose();
        
        musicJuego.dispose();
        
        font.dispose();
        tituloFont.dispose();
    }
}