package puppy.code;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class DisparoDoble implements EstrategiaDisparo {
    private Texture txBala;
    private Sound soundBala;

    public DisparoDoble(Texture txBala, Sound soundBala) {
        this.txBala = txBala;
        this.soundBala = soundBala;
    }

    @Override
    public void disparar(Nave4 nave, PantallaJuego juego) {
        // Lógica nueva: Dos balas paralelas desde los costados
        Bullet balaIzq = new Bullet(nave.getX(),
                nave.getY() + nave.getArea().getHeight() - 5,
                0, 3, txBala);
        
        Bullet balaDer = new Bullet(nave.getX() + nave.getArea().getWidth() - 10,
                nave.getY() + nave.getArea().getHeight() - 5,
                0, 3, txBala);
        
        juego.agregarBala(balaIzq);
        juego.agregarBala(balaDer);
        soundBala.play();
    }
}