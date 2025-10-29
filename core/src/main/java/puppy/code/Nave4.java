package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Nave4 {
	
	private boolean destruida = false;
    private int vidas = 3;
    private float speed =  5f;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax=50;
    private int tiempoHerido;
    
    private boolean escudoEnJuego = false;
    private float tiempoEscudoRestante = 0f;
    private Activable escudoActual = null;

    
    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
    	sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	//spr.setOriginCenter();
    	spr.setBounds(x, y, 45, 45);
    }
    	
    	
    	
    public void activarEscudoNave(float duracion, Activable activable) { 
    	   this.escudoEnJuego = true;
    	   this.tiempoEscudoRestante = duracion; 
    	   this.escudoActual = activable;
     }
    	
    
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x =  spr.getX();
        float y =  spr.getY();
        if (!herido) {
	        // que se mueva con teclado
        	xVel = 0;
        	yVel = 0;
        	
	        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) xVel = -speed;
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) xVel = speed;;
        	if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) yVel = -speed;     
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) yVel = speed;
        	
	     /*   if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spr.setRotation(++rotacion);
	        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spr.setRotation(--rotacion);
	        
	        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        	xVel -=Math.sin(Math.toRadians(rotacion));
	        	yVel +=Math.cos(Math.toRadians(rotacion));
	        	System.out.println(rotacion+" - "+Math.sin(Math.toRadians(rotacion))+" - "+Math.cos(Math.toRadians(rotacion))) ;    
	        }
	        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
	        	xVel +=Math.sin(Math.toRadians(rotacion));
	        	yVel -=Math.cos(Math.toRadians(rotacion));
	        	     
	        }*/
	        
	        // que se mantenga dentro de los bordes de la ventana
	        if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth())
	        	xVel*=-1;
	        if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
	        	yVel*=-1;
	        
	        spr.setPosition(x+xVel, y+yVel);
 		    
 		    
        } else {
           spr.setX(spr.getX()+MathUtils.random(-2,2));
 		  spr.setX(x);
 		   tiempoHerido--;
 		   if (tiempoHerido<=0) herido = false;
 		 }
        
        
        if (escudoEnJuego) {
        	tiempoEscudoRestante--;
            if (tiempoEscudoRestante <= 0) {
                if (escudoActual != null) {
                    escudoActual.deactivate(this); 
                    escudoActual = null;
                }
                escudoEnJuego = false; 
            }
        }
                
                
        if (escudoEnJuego) {
        	spr.setColor(0.5f, 0.5f, 1f, 1f);
        	spr.setAlpha(0.5f); 
               } else {
                  spr.setColor(1f, 1f, 1f, 1f); 
                  spr.setAlpha(1f); 
               }
        
        spr.draw(batch);
  
        
        // disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {         
          Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala);
	      juego.agregarBala(bala);
	      soundBala.play();
        }
        
    }
       
      
    public boolean checkCollision(Ball2 b) {
   
    	Rectangle naveArea = spr.getBoundingRectangle();
        Rectangle asteroideArea = b.getArea();
    	if (escudoEnJuego && asteroideArea.overlaps(naveArea)) {
            tiempoEscudoRestante -= 10; 
            b.setXSpeed(-b.getXSpeed());
            b.setySpeed(-b.getySpeed());
            float pushX = Math.signum(b.getArea().x - naveArea.x) * 2;
            float pushY = Math.signum(b.getArea().y - naveArea.y) * 2;
            b.spr.setPosition(b.spr.getX() + pushX, b.spr.getY() + pushY);
            b.x = (int)b.spr.getX();
            b.y = (int)b.spr.getY();
            return false; 
        }
    	
        if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	
            // despegar sprites
      /*      int cont = 0;
            while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont<xVel) {
               spr.setX(spr.getX()+Math.signum(xVel));
            }   */
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
    }
    
    public boolean estaDestruido() {
       return !herido && destruida;
    }
    public boolean estaHerido() {
 	   return herido;
    }
    
    public int getVidas() {return vidas;}
    //public boolean isDestruida() {return destruida;}
    public int getX() {return (int) spr.getX();}
    public int getY() {return (int) spr.getY();}
	public void setVidas(int vidas2) {vidas = vidas2;}
	public com.badlogic.gdx.math.Rectangle getArea() {
	    return spr.getBoundingRectangle();
	}

	public boolean isEscudoActivo() { 
		return escudoEnJuego;
	}
}
	