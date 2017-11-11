	import java.awt.event.KeyEvent;

	import org.lwjgl.Display;
	import org.lwjgl.LWJGLException;
	import org.lwjgl.input.Keyboard;
	import org.lwjgl.input.Mouse;
	import org.lwjgl.opengl.GL11;
	
	
	public class AGlimpseOf {
	  
		private GameWindowCallback callback;
	  	private boolean gameRunning = true;
	    private int width;
	    private int height;
	    
	    private String title = ControlRoom.GAME_TITLE;

		private TextureLoader textureLoader;
	  
		
		public AGlimpseOf() {
		}
		
		
		TextureLoader getTextureLoader() {
			return textureLoader;
		}
		
		public void setTitle(String title) {
		    this.title = title;
		    if(Display.isCreated()) {
		    	Display.setTitle(title);
		    }
		}

		
		public void setResolution(int width, int height) {
			this.width = width;
			this.height = height;
		}

		
		private boolean setDisplayMode() {
	    		try {
				// get modes

				DisplayMode[] dm = org.lwjgl.util.Display.getAvailableDisplayModes(width, height, -1, -1, -1, -1, 60, 60);

	      			org.lwjgl.util.Display.setDisplayMode(dm, new String[] {
	 				"width=" + width, "height=" + height,
	          			"freq=" + 60,
	          			"bpp=" + org.lwjgl.opengl.Display.getDisplayMode().getBitsPerPixel()
	         		}); 
	      			return true;
	    		} catch (Exception e) {
	      			e.printStackTrace();
	      			System.out.println("Unable to enter fullscreen, continuing in windowed mode");
	    		}

			return false;
		}

		/**
		 * Start the rendering process. This method will cause the 
		 * display to redraw as fast as possible.
		 */
		public void startRendering() {
			try {
				setDisplayMode();
				Display.create();
		      
				// grab the mouse, dont want that hideous cursor when we're playing!

				Mouse.setGrabbed(true);
	  
				// enable textures since we're going to use these for our sprites

				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				// disable the OpenGL depth test since we're rendering 2D graphics

				GL11.glDisable(GL11.GL_DEPTH_TEST);
				
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				
				GL11.glOrtho(0, width, height, 0, -1, 1);
				
				textureLoader = new TextureLoader();
				
				if(callback != null) {
					callback.initialise();
				}
			} catch (LWJGLException le) {
				callback.windowClosed();
			}
	    
			gameLoop();
		}

		/**
		 * Register a callback that will be notified of game window
		 * events.
		 *
		 * @param callback The callback that should be notified of game
		 * window events. 
		 */
		public void setGameWindowCallback(GameWindowCallback callback) {
			this.callback = callback;
		}
		
		/**
		 * Check if a particular key is current pressed.
		 *
		 * @param keyCode The code associated with the key to check 
		 * @return True if the specified key is pressed
		 */
		public boolean isKeyPressed(int keyCode) {
			// apparently, someone at decided not to use standard 

			// keycode, so we have to map them over:

			switch(keyCode) {
			case KeyEvent.VK_SPACE:
				keyCode = Keyboard.KEY_SPACE;
				break;
			case KeyEvent.VK_LEFT:
				keyCode = Keyboard.KEY_LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				keyCode = Keyboard.KEY_RIGHT;
				break;
			}    
			
			return org.lwjgl.input.Keyboard.isKeyDown(keyCode);
		}
	  
		/**
		 * Run the main game loop. This method keeps rendering the scene
		 * and requesting that the callback update its screen.
		 */
		private void gameLoop() {
			while (gameRunning) {
				// clear screen

				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				
				// let subsystem paint

				if (callback != null) {
					callback.frameRendering();
				}
				
				// update window contents

				Display.update();
				
				if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					gameRunning = false;
					Display.destroy();
					callback.windowClosed();
				}
			}
		}  
	
	
}