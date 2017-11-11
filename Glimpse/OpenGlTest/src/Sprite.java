
import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class Sprite {
	private Texture texture;
  
	private int width;
  	private int height;
	

	public Sprite(AGlimpseOf window,String path) {
		try {
			texture = window.getTextureLoader().getTexture(path);
			
			width = texture.getImageWidth();
			height = texture.getImageHeight();
		} catch (IOException e) {
			
			System.err.println("Unable to load texture: " + path);
			System.exit(0);
		}
	}
	
	
	public int getWidth() {
		return texture.getImageWidth();
	}

	
	public int getHeight() {
		return texture.getImageHeight();
	}

	
	public void draw(int x, int y) {
		GL11.glPushMatrix();
		texture.bind();
    
		GL11.glTranslatef(x, y, 0);		
    	GL11.glColor3f(1,1,1);
		
		
    	GL11.glBegin(GL11.GL_QUADS);
		{
	      GL11.glTexCoord2f(0, 0);
	      GL11.glVertex2f(0, 0);
	      GL11.glTexCoord2f(0, texture.getRatioHeight());
	      GL11.glVertex2f(0, height);
	      GL11.glTexCoord2f(texture.getRatioWidth(), texture.getRatioHeight());
	      GL11.glVertex2f(width,height);
	      GL11.glTexCoord2f(texture.getRatioWidth(), 0);
	      GL11.glVertex2f(width,0);
		}
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
	
}