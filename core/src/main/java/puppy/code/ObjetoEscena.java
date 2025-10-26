package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoEscena {
    protected int x;
    protected int y;
    protected int xSpeed;
    protected int ySpeed;
    protected Sprite spr;

    public ObjetoEscena(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        spr = new Sprite(tx);
        this.x = x;
        if (x-size < 0) this.x = x+size;
        if (x+size > Gdx.graphics.getWidth()) this.x = x-size;

        this.y = y;
        if (y-size < 0) this.y = y+size;
        if (y+size > Gdx.graphics.getHeight()) this.y = y-size;

        spr.setPosition(this.x, this.y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public abstract void update();

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }
}
