package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Boss extends ObjetoEscena {
    
    private int vida = 50;
    private int vidaMaxima = 50; // Guardamos la vida máxima para la barra de vida
    private float tiempoDisparo = 0f;
    
    // CAMBIO 1: Dispara mucho más rápido (antes era 1.5f)
    private float tiempoEntreDisparos = 0.5f; 
    private Texture txProyectil; 

    public Boss(int x, int y, Texture txBoss, Texture txProyectil) {
        // CAMBIO 2: Tamaño aumentado a 250x250 (antes 150)
        super(x, y, 250, 3, 0, txBoss); 
        this.txProyectil = txProyectil;
        spr.setSize(250, 250); 
    }

    @Override
    public void performUpdate() {
        // Ajustamos la posición Y considerando el nuevo tamaño para que no se salga
        this.y = (int) (Gdx.graphics.getHeight() - spr.getHeight() - 10);
        spr.setY(this.y);
    }
    
    public void intentarDisparar(PantallaJuego juego, float delta) {
        tiempoDisparo += delta;
        if (tiempoDisparo >= tiempoEntreDisparos) {
            shoot(juego);
            tiempoDisparo = 0f;
        }
    }

    private void shoot(PantallaJuego juego) {
        // La roca sale desde el centro del nuevo tamaño
        Ball2 roca = new Ball2(
                (int)(this.x + spr.getWidth() / 2 - 20), 
                (int)(this.y - 60), 
                40, 
                MathUtils.random(-2, 2), 
                -6, // Un poco más rápidas hacia abajo
                txProyectil 
        );
        juego.agregarEnemigo(roca);
    }
    
    public boolean recibirDano() {
        vida--;
        return vida <= 0;
    }
    
    public int getVida() { return vida; }
    public int getVidaMaxima() { return vidaMaxima; }
}