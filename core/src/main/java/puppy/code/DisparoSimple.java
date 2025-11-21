package puppy.code;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public class DisparoSimple implements EstrategiaDisparo {
    private Texture txBala;
    private Sound soundBala;

    public DisparoSimple(Texture txBala, Sound soundBala) {
        this.txBala = txBala;
        this.soundBala = soundBala;
    }

    @Override
    public void disparar(Nave4 nave, PantallaJuego juego) {
        // Lógica original: una bala desde el centro
        Bullet bala = new Bullet(nave.getX() + nave.getArea().getWidth() / 2 - 5,
                nave.getY() + nave.getArea().getHeight() - 5,
                0, 3, txBala);
        juego.agregarBala(bala);
        soundBala.play();
    }
}