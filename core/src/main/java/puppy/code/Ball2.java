package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Ball2 extends ObjetoEscena {
    
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
        spr.setSize(50,50);
    }

    @Override
    public void performUpdate() {
        // No requiere lógica extra de actualización por ahora
    }
    
    public void checkCollision(Ball2 b2) {
        if (spr.getBoundingRectangle().overlaps(b2.getArea())) {
            
            // 1. REBOTE
            // Usamos 'this.xSpeed' y 'b2.xSpeed' directamente porque son protected en ObjetoEscena
            if (this.xSpeed == 0) this.xSpeed += b2.xSpeed / 2;
            if (b2.xSpeed == 0) b2.xSpeed += this.xSpeed / 2;
            
            this.xSpeed = -this.xSpeed;
            b2.xSpeed = -b2.xSpeed;
            
            if (this.ySpeed == 0) this.ySpeed += b2.ySpeed / 2;
            if (b2.ySpeed == 0) b2.ySpeed += this.ySpeed / 2;
            
            this.ySpeed = -this.ySpeed;
            b2.ySpeed = -b2.ySpeed;

            // 2. DESPEGUE MANUAL (Anti-Temblor)
            float diffX = (this.x + spr.getWidth()/2) - (b2.x + b2.spr.getWidth()/2);
            float diffY = (this.y + spr.getHeight()/2) - (b2.y + b2.spr.getHeight()/2);
            
            float empuje = 5.0f;
            
            this.x += Math.signum(diffX) * empuje;
            this.y += Math.signum(diffY) * empuje;
            
            b2.x -= Math.signum(diffX) * empuje;
            b2.y -= Math.signum(diffY) * empuje;
            
            // Actualizar sprite inmediatamente
            this.spr.setPosition(this.x, this.y);
            b2.spr.setPosition(b2.x, b2.y);
        }
    }
    
    // Getters y Setters (Opcionales ahora, pero los dejo por si acaso los usas en otra parte)
    public int getXSpeed() { return xSpeed; }
    public void setXSpeed(int xSpeed) { this.xSpeed = xSpeed; }
    public int getySpeed() { return ySpeed; }
    public void setySpeed(int ySpeed) { this.ySpeed = ySpeed; }
}