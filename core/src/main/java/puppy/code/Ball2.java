package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Ball2 extends ObjetoEscena {
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
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

    public void checkCollision(Ball2 b2) {
        if (spr.getBoundingRectangle().overlaps(b2.getArea())) {
            
            if (getXSpeed() == 0) setXSpeed(getXSpeed() + b2.getXSpeed() / 2);
            if (b2.getXSpeed() == 0) b2.setXSpeed(b2.getXSpeed() + getXSpeed() / 2);
            setXSpeed(-getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());
            if (getySpeed() == 0) setySpeed(getySpeed() + b2.getySpeed() / 2);
            if (b2.getySpeed() == 0) b2.setySpeed(b2.getySpeed() + getySpeed() / 2);
            setySpeed(-getySpeed());
            b2.setySpeed(-b2.getySpeed());
        }
    }

    public int getXSpeed() { return xSpeed; }
    public void setXSpeed(int xSpeed) { this.xSpeed = xSpeed; }
    public int getySpeed() { return ySpeed; }
    public void setySpeed(int ySpeed) { this.ySpeed = ySpeed; }
}
