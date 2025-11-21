package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class DisparoTriple implements EstrategiaDisparo {
    private Texture txBala;
    private Sound soundBala;

    public DisparoTriple(Texture txBala, Sound soundBala) {
        this.txBala = txBala;
        this.soundBala = soundBala;
    }

    @Override
    public void disparar(Nave4 nave, PantallaJuego juego) {
        // Bala Izquierda
        Bullet balaIzq = new Bullet(nave.getX(),
                nave.getY() + nave.getArea().getHeight() - 5,
                -1, 3, txBala);

        // Bala Central
        Bullet balaCentro = new Bullet(nave.getX() + nave.getArea().getWidth() / 2 - 5,
                nave.getY() + nave.getArea().getHeight() - 5,
                0, 3, txBala);
        
        // Bala Derecha
        Bullet balaDer = new Bullet(nave.getX() + nave.getArea().getWidth() - 10,
                nave.getY() + nave.getArea().getHeight() - 5,
                1, 3, txBala);
        
        juego.agregarBala(balaIzq);
        juego.agregarBala(balaCentro);
        juego.agregarBala(balaDer);
        
        soundBala.play();
    }
}