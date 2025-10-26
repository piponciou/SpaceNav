package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends ObjetoEscena {

    public PowerUp(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
        spr.setSize(40,40 );
    }


	@Override
    public void update() {
        x += xSpeed;
        y += ySpeed;
        if (x + xSpeed < 0 || x + xSpeed + spr.getWidth() > com.badlogic.gdx.Gdx.graphics.getWidth())
            xSpeed *= -1;
        if (y + ySpeed < 0 || y + ySpeed + spr.getHeight() > com.badlogic.gdx.Gdx.graphics.getHeight())
            ySpeed *= -1;
        spr.setPosition(x, y);
    }
}
