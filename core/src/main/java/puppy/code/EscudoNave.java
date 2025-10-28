package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class EscudoNave extends ObjetoEscena implements Activable {
	
	private float tiempoEscudo = 400f;

    public EscudoNave(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
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
	
	
	@Override
	public void activate(Nave4 Nave) {
		Nave.activarEscudoNave(tiempoEscudo, this);
	}
		
	public void deactivate(Nave4 Nave) {
	}
	
	@Override
	public boolean isConsumed( ) {
		return true;
	}
	
}



