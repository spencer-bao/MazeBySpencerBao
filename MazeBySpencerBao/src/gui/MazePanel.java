package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * Add functionality for double buffering to an AWT Panel class.
 * Used for drawing a maze.
 * 
 * @author Peter Kemper
 *
 */
public class MazePanel extends Panel implements P5Panel{
	private static final long serialVersionUID = 2787329533730973905L;
	/* Panel operates a double buffer see
	 * http://www.codeproject.com/Articles/2136/Double-buffer-in-standard-Java-AWT
	 * for details
	 */
	// bufferImage can only be initialized if the container is displayable,
	// uses a delayed initialization and relies on client class to call initBufferImage()
	// before first use
	private Image bufferImage;  
	private Graphics2D graphics; // obtained from bufferImage, 
	// graphics is stored to allow clients to draw on the same graphics object repeatedly
	// has benefits if color settings should be remembered for subsequent drawing operations
	
	/**
	 * Constructor. Object is not focusable.
	 */
	public MazePanel() {
		setFocusable(false);
		bufferImage = null; // bufferImage initialized separately and later
		graphics = null;	// same for graphics
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	/**
	 * Method to draw the buffer image on a graphics object that is
	 * obtained from the superclass. 
	 * Warning: do not override getGraphics() or drawing might fail. 
	 */
	public void update() {
		commit();
	}
	
	/**
	 * Draws the buffer image to the given graphics object.
	 * This method is called when this panel should redraw itself.
	 * The given graphics object is the one that actually shows 
	 * on the screen.
	 */
	@Override
	public void paint(Graphics g) {
		if (null == g) {
			System.out.println("MazePanel.paint: no graphics object, skipping drawImage operation");
		}
		else {
			g.drawImage(bufferImage,0,0,null);	
		}
	}

	/**
	 * Obtains a graphics object that can be used for drawing.
	 * This MazePanel object internally stores the graphics object 
	 * and will return the same graphics object over multiple method calls. 
	 * The graphics object acts like a notepad where all clients draw 
	 * on to store their contribution to the overall image that is to be
	 * delivered later.
	 * To make the drawing visible on screen, one needs to trigger 
	 * a call of the paint method, which happens 
	 * when calling the update method. 
	 * @return graphics object to draw on, null if impossible to obtain image
	 */
	public Graphics getBufferGraphics() {
		// if necessary instantiate and store a graphics object for later use
		if (!isOperational()) { // before P5: graphics == null
			if (null == bufferImage) {
				bufferImage = createImage(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
				if (null == bufferImage)
				{
					System.out.println("Error: creation of buffered image failed, presumedly container not displayable");
					return null; // still no buffer image, give up
				}		
			}
			graphics = (Graphics2D) bufferImage.getGraphics();
			if (null == graphics) {
				System.out.println("Error: creation of graphics for buffered image failed, presumedly container not displayable");
			}
			else {
				// System.out.println("MazePanel: Using Rendering Hint");
				// For drawing in FirstPersonDrawer, setting rendering hint
				// became necessary when lines of polygons 
				// that were not horizontal or vertical looked ragged
				setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
						java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
				setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
						java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
		}
		return graphics;
	}

	@Override
	public void commit() {
		paint(getGraphics());
		
	}

	@Override
	public boolean isOperational() {
		if (graphics != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setColor(int rgb) {
		Color color = new Color(rgb);
		graphics.setColor(color);		
	}
	
	public int decodeRGB(String hex) {
		return Color.decode(hex).getRGB();
	}
	
	
	
	public int convertRGB(float r, float g, float b, float a) {
		return new Color(r, g, b, a).getRGB();

	}
	
	public Font decodeFont(String string) {
		return Font.decode(string);
	}
	
//	public String convertFont(Font font) {
//		return Font.
//	}

	@Override
	public int getColor() {
		return graphics.getColor().getRGB();
	}

	
	private int calculateRGBValue(final int distance, int extensionX) {
        // compute rgb value, depends on distance and x direction
        // 7 in binary is 0...0111
        // use AND to get last 3 digits of distance
        final int part1 = distance & 7;
        final int add = (extensionX != 0) ? 1 : 0;
        final int rgbValue = ((part1 + 2 + add) * 70) / 8 + 80;
        return rgbValue;
    }
	
	@Override
	public int getWallColor(int distance, int cc, int extensionX) {
		
		final int RGB_DEF = 20;
	    final int RGB_DEF_GREEN = 60;
	    
		final int d = distance / 4;
        
		
        // calculateRGBValue(distance) from Wall
        int rgbValue = calculateRGBValue(distance, extensionX);
        
     // mod used to limit the number of colors to 6
        
        int rgb;
        switch (((d >> 3) ^ cc) % 6) {
        case 0:
            rgb = new Color(rgbValue, RGB_DEF, RGB_DEF).getRGB();
            break;
        case 1:
            rgb = new Color(RGB_DEF, RGB_DEF_GREEN, RGB_DEF).getRGB();
            break;
        case 2:
            rgb = new Color(RGB_DEF, RGB_DEF, rgbValue).getRGB();
            break;
        case 3:
            rgb = new Color(rgbValue, RGB_DEF_GREEN, RGB_DEF).getRGB();
            break;
        case 4:
            rgb = new Color(RGB_DEF, RGB_DEF_GREEN, rgbValue).getRGB();
            break;
        case 5:
            rgb = new Color(rgbValue, RGB_DEF, rgbValue).getRGB();
            break;
        default:
            rgb = new Color(RGB_DEF, RGB_DEF, RGB_DEF).getRGB();
            break;
        }
		return rgb;
	}
	
	private Color getBackgroundColor(float percentToExit, boolean top) {
		final Color greenWM = Color.decode("#115740");
		final Color goldWM = Color.decode("#916f41");
		final Color yellowWM = Color.decode("#FFFF99");
		return top? blend(yellowWM, goldWM, percentToExit) : 
			blend(Color.lightGray, greenWM, percentToExit);
	}
	
	private Color blend(Color c0, Color c1, double weight0) {
		if (weight0 < 0.1)
			return c1;
		if (weight0 > 0.95)
			return c0;
	    double r = weight0 * c0.getRed() + (1-weight0) * c1.getRed();
	    double g = weight0 * c0.getGreen() + (1-weight0) * c1.getGreen();
	    double b = weight0 * c0.getBlue() + (1-weight0) * c1.getBlue();
	    double a = Math.max(c0.getAlpha(), c1.getAlpha());

	    return new Color((int) r, (int) g, (int) b, (int) a);
	  }

	@Override
	public void addBackground(float percentToExit) {
		final int viewWidth  = 400;
		final int viewHeight = 400;
		// black rectangle in upper half of screen
		// graphics.setColor(Color.black);
		// dynamic color setting: 
		graphics.setColor(getBackgroundColor(percentToExit, true));
//		graphics.fillRect(0, 0, viewWidth, viewHeight/2);
		addFilledRectangle(0, 0, viewWidth, viewHeight/2);
		// grey rectangle in lower half of screen
		// graphics.setColor(Color.darkGray);
		// dynamic color setting: 
		graphics.setColor(getBackgroundColor(percentToExit, false));
//		graphics.fillRect(0, viewHeight/2, viewWidth, viewHeight/2);
		addFilledRectangle(0, viewHeight/2, viewWidth, viewHeight/2);

	}

	@Override
	public void addFilledRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
		
	}

	@Override
	public void addFilledPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.fillPolygon(xPoints, yPoints, nPoints);		
	}

	@Override
	public void addPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		graphics.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void addLine(int startX, int startY, int endX, int endY) {
		graphics.drawLine(startX, startY, endX, endY);
	}

	@Override
	public void addFilledOval(int x, int y, int width, int height) {
		graphics.fillOval(x, y, width, height);
		
	}

	@Override
	public void addArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		graphics.drawArc(x, y, width, height, startAngle, arcAngle);
		
	}

	@Override
	public void addMarker(float x, float y, String str) {
		Font markerFont = null;
		GlyphVector gv = markerFont.createGlyphVector(graphics.getFontRenderContext(), str);
        Rectangle2D rect = gv.getVisualBounds();
        
        x -= rect.getWidth() / 2;
        y += rect.getHeight() / 2;
        
        graphics.drawGlyphVector(gv, x, y);
		
	}

	@Override
	public void setRenderingHint(java.awt.RenderingHints.Key hintKey, Object hintValue) {
		graphics.setRenderingHint(hintKey, hintValue);
		
	}
	
	

}
