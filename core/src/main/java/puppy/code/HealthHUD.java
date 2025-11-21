package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HealthHUD {
    
    private Texture heartTexture;
    
    // Configuración visual
    private int heartSize = 40;
    private int spacing = 5;    
    private int startX = 10;    
    private int startY = 20;   

    public HealthHUD(Texture heartTexture) {
        this.heartTexture = heartTexture;
    }

    public void draw(SpriteBatch batch, int vidas) {
        
        for (int i = 0; i < vidas; i++) {
            batch.draw(heartTexture, 
                       startX + (i * (heartSize + spacing)), 
                       startY, 
                       heartSize, 
                       heartSize);
        }
    }
}