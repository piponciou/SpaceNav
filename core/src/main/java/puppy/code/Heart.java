package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Heart extends ObjetoEscena implements Activable {
    private boolean consumed = false;

    public Heart(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
        spr.setSize(size, size);
    }

    @Override
    public void update() {
        // Movimiento igual que las rocas (rebote en los bordes)
        x += xSpeed;
        y += ySpeed;

        if (x + xSpeed < 0 || x + xSpeed + spr.getWidth() > com.badlogic.gdx.Gdx.graphics.getWidth())
            xSpeed *= -1;
        if (y + ySpeed < 0 || y + ySpeed + spr.getHeight() > com.badlogic.gdx.Gdx.graphics.getHeight())
            ySpeed *= -1;

        spr.setPosition(x, y);
    }

    @Override
    public void activate(Nave4 nave) {
        if (!consumed && nave.getVidas() < 3) {
            nave.setVidas(nave.getVidas() + 1);
            consumed = true;
        }
    }

    @Override
    public void deactivate(Nave4 nave) {
        // no se ocupa, pero debe estar por la interfaz
    }

    @Override
    public boolean isConsumed() {
        return consumed;
    }
}
