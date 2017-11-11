
import org.lwjgl.opengl.GL11;

public class Texture {
    /** The GL target type */
    private int target; 
    /** The GL texture ID */
    private int textureID;
    /** The height of the image */
    private int height;
    /** The width of the image */
    private int width;
    /** The width of the texture */
    private int texWidth;
    /** The height of the texture */
    private int texHeight;
    /** The ratio of the width of the image to the texture */
    private float widthRatio;
    /** The ratio of the height of the image to the texture */
    private float heightRatio;
   
    
    public Texture(int target,int textureID) {
        this.target = target;
        this.textureID = textureID;
    }
    
   
    public void bind() {
      GL11.glBindTexture(target, textureID); 
    }
    
   
    public void setHeight(int height) {
        this.height = height;
        setTextureHeight();
    }
    
    
    public void setWidth(int width) {
        this.width = width;
        setTextureWidth();
    }
    
    
    public int getImageHeight() {
        return height;
    }
    
   
    public int getImageWidth() {
        return width;
    }
    
    
    public float getRatioHeight() {
        return heightRatio;
    }
    
    
    public float getRatioWidth() {
        return widthRatio;
    }
    
    
    public void setTextureHeight(int texHeight) {
        this.texHeight = texHeight;
        setTextureHeight();
    }
    
   
    public void setTextureWidth(int texWidth) {
        this.texWidth = texWidth;
        setTextureWidth();
    }
    
    
    private void setTextureHeight() {
        if (texHeight != 0) {
            heightRatio = ((float) height)/texHeight;
        }
    }
    
   
    private void setTextureWidth() {
        if (texWidth != 0) {
            widthRatio = ((float) width)/texWidth;
        }
    }
}