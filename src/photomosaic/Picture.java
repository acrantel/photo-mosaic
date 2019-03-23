package photomosaic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The <code>Picture</code> class stores an image read from a .gif/.jpeg (or
 * .jpg) file. We can query each instance about the picture it stores (including
 * the RGB values at any of its pixels) and command it to change any RBG values
 * of its pixels. We can also display this picture in its own frame.
 */
public class Picture {

	/**
	 * Constructs a picture with the required state.
	 *
	 * @param fileName
	 *            specifies the file name that stores the picture
	 * @param width
	 *            specifies the width of the picture (in pixels)
	 * @param height
	 *            specifies the height of the picture (in pixels)
	 * @param bufferImage
	 *            specifies the RGB values of all the pixels in the image
	 */
	private Picture(String fileName, int width, int height, BufferedImage bufferImage) {
		// Just copy parameters to instance variables
		_fileName = fileName;
		_width = width;
		_height = height;
		_bufferImage = bufferImage;
	}

	/**
	 * Constructs a picture from the required state. This version determines the
	 * <code>bufferImage</code> by reading the .gif/.jpeg (or .jpg) in
	 * <code>fileName</code>. If width/height are
	 * <code>Picture.NATURAL_SIZE</code> then these values are replaced by those
	 * stored in the file itself (and the picture is not scaled). Otherwise the
	 * picture is scaled to conform to these sizes.
	 *
	 * @param fileName
	 *            specifies the file name from which to read the picture
	 * @param width
	 *            specifies the width to make the picture (in pixels)
	 * @param height
	 *            specifies the height to make the picture (in pixels)
	 *
	 * @throws IllegalArgumentException
	 *             if the file cannot be found (or it doesn't store a picture in
	 *             it: i.e. not a real .gif/.jpg (or .jpg) file)
	 */
	public Picture(String fileName, int width, int height) throws IllegalArgumentException {
		// Try to read the IconImage from the file; if cannot, width is -1
		ImageIcon temp = new ImageIcon(_tk.getImage(fileName));
		if (temp.getIconWidth() == -1)
			throw new IllegalArgumentException(
					"Picture constructor: ImageIcon could not be read from file: " + fileName);

		// If the width/height parameters are NATURAL, reset them to be the
		// actual width/height of the picture read from the file
		if (width == NATURAL_SIZE)
			width = temp.getIconWidth();
		if (height == NATURAL_SIZE)
			height = temp.getIconHeight();

		// If the picture needs to be scaled, do so.
		if (width != temp.getIconWidth() || height != temp.getIconHeight())
			temp = new ImageIcon(temp.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

		// Create a buffered image of the picture (for getting/setting pixel
		// values)
		// Initialize the instance variables
		_fileName = fileName;
		_width = width;
		_height = height;
		_bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = _bufferImage.createGraphics();
		temp.paintIcon(null, g, 0, 0);

	}

	/**
	 * Returns an empty picture with the required state.
	 *
	 * @param label
	 *            specifies the label of the picture (it is not read directly
	 *            from a file)
	 * @param width
	 *            specifies the width of the picture (in pixels)
	 * @param height
	 *            specifies the width of the picture (in pixels)
	 *
	 * @return an empty picture with the required state
	 */
	public static Picture makeEmptyPicture(String label, int width, int height) {
		return new Picture(label, width, height, new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
	}

	/**
	 * Returns a copy of this picture. Sometimes if you want a picture and a
	 * variant of it (with slightly different RGB values): first you copy it and
	 * then change the values in the copy.
	 *
	 * @param copyName
	 *            specifies the label (fileName) for the copy
	 *
	 * @return a copy of this picture
	 */
	public Picture copy(String copyName) {
		// Create a new buffered image and into it copy the old one
		BufferedImage biCopy = new BufferedImage(_width, _height, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < _width; x++)
			for (int y = 0; y < _height; y++)
				biCopy.setRGB(x, y, _bufferImage.getRGB(x, y));

		// Return a new Picture with everything the same except the buffered
		// image
		return new Picture(copyName, _width, _height, biCopy);
	}

	/**
	 * Alters the <code>Picture</code> so that each pixel is filtered according
	 * to <code>f</code>. A <code>null</code> leaves the picture unchanged.
	 *
	 * @param f
	 *            specifies a class that implements the <code>filter</code>
	 *            interface (containing a <code>getFiltered</code> method
	 */
	public void filter(Filter f) {
		if (f == null)
			return;

		Picture temp = copy("");
		for (int x = 0; x < _width; x++)
			for (int y = 0; y < _height; y++) {
				int r = f.getFiltered(Filter.RED, temp, x, y);
				int g = f.getFiltered(Filter.GREEN, temp, x, y);
				int b = f.getFiltered(Filter.BLUE, temp, x, y);
				setColor(x, y, r, g, b);
			}

	}

	/**
	 * Returns the normalized color: a value between 0 and 255 inclusive, with
	 * values outside the bounds brought inside the bounds.
	 *
	 * @return the normalized color
	 */
	private static int normalize(int color) {
		return Math.min(255, Math.max(color, 0));
	}

	/**
	 * Alters the <code>bufferImage</code> so that each pixel has its RGB values
	 * shifted by <code>dR</code>, <code>dG</code>, and <code>dB</code>
	 * respectively. The resulting values are normalized. This method shifts the
	 * average color value down (negative values) or up (positive values):
	 * often, for gray scale pictures, all three values are the same.
	 *
	 * @param dR
	 *            specifies the amount (positive or negative) to shift the R
	 *            value
	 * @param dG
	 *            specifies the amount (positive or negative) to shift the G
	 *            value
	 * @param dB
	 *            specifies the amount (positive or negative) to shift the B
	 *            value
	 */
	public void shift(int dR, int dG, int dB) {
		for (int x = 0; x < _width; x++)
			for (int y = 0; y < _height; y++) {
				Color rgb = getColor(x, y);
				int red = normalize(rgb.getRed() + dR);
				int green = normalize(rgb.getGreen() + dG);
				int blue = normalize(rgb.getBlue() + dB);
				setColor(x, y, red, green, blue);
			}
	}

	/**
	 * Overlay this picture in the specified location with the entire picture
	 * <code>p</code>. That is, change the <code>bufferImage</code> of this
	 * picture to include the one specified by <code>p</code> (which should be
	 * smaller).
	 *
	 * @param upperLeftX
	 *            specifies the where in this picture the overlay starts
	 * @param upperLeftY
	 *            specifies the where in this picture the overlay starts
	 * @param p
	 *            specifies the picture to overlay onto this one
	 *
	 * @throws IllegalArgumentException
	 *             if <code>p</code> will not completely fit in the picture
	 *             (some of its pixels would be outside the picture)
	 */
	public void overlay(int upperLeftX, int upperLeftY, Picture p) throws IllegalArgumentException {
		if (upperLeftX < 0 || upperLeftX + p._width > _bufferImage.getWidth() || upperLeftY < 0
				|| upperLeftY + p._height > _bufferImage.getHeight())
			throw new IllegalArgumentException(
					"Picture.overlay failed: upperLeftX/p's width:upperLeftY/p's height illegal: " + upperLeftX + "/"
							+ p._width + ":" + upperLeftY + "/" + p._height + "(this picture's size="
							+ _bufferImage.getWidth() + ":" + _bufferImage.getHeight() + ")");
		for (int x = 0; x < p._width; x++)
			for (int y = 0; y < p._height; y++)
				setColor(upperLeftX + x, upperLeftY + y, p.getColor(x, y));
	}

	/**
	 * Extract a portion of this picture from the specified location to be of
	 * the specified size.
	 *
	 * @param label
	 *            specifies the label of the picture (it is not read directly
	 *            from a file)
	 * @param upperLeftX
	 *            specifies the where in this picture the extraction starts
	 * @param upperLeftY
	 *            specifies the where in this picture the extraction starts
	 * @param width
	 *            specifies the width of the extracted picture
	 * @param height
	 *            specifies the height of the extracted picture
	 *
	 * @throws IllegalArgumentException
	 *             if the specified extraction does not lie completely in this
	 *             picture
	 */
	public Picture extract(String label, int upperLeftX, int upperLeftY, int width, int height)
			throws IllegalArgumentException {
		if (upperLeftX < 0 || upperLeftX + width > _bufferImage.getWidth() || upperLeftY < 0
				|| upperLeftY + height > _bufferImage.getHeight())
			throw new IllegalArgumentException("Picture.extract failed: upperLeftX/width:upperLeftY/height illegal: "
					+ upperLeftX + "/" + width + ":" + upperLeftY + "/" + height + "(this picture's size="
					+ _bufferImage.getWidth() + ":" + _bufferImage.getHeight() + ")");
		Picture answer = makeEmptyPicture(label, width, height);
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				answer.setColor(x, y, getColor(upperLeftX + x, upperLeftY + y));

		return answer;
	}

	/**
	 * Returns the <code>Color</code> (RGB values) for any coordinate.
	 *
	 * @param x
	 *            specifies the x (horizontal) index of the RGB value
	 * @param y
	 *            specifies the y (vertical) index of the RGB value
	 *
	 * @return the <code>Color</code> (RGB values) for any coordinate
	 *
	 * @throws IllegalArgumentException
	 *             if <code>x</code> or <code>y</code> is not a legal coordinate
	 *             inside the pictur.
	 */
	public Color getColor(int x, int y) throws IllegalArgumentException {
		if (x < 0 || x > _bufferImage.getWidth() || y < 0 || y > _bufferImage.getHeight())
			throw new IllegalArgumentException("Picture.getColor x:y illegal: " + x + ":" + y + "("
					+ _bufferImage.getWidth() + ":" + _bufferImage.getHeight() + ")");
		int rgb = _bufferImage.getRGB(x, y);
		int red = (rgb & (255 << 16)) >> 16;
		int green = (rgb & (255 << 8)) >> 8;
		int blue = (rgb & 255);
		return new Color(red, green, blue);
	}

	/**
	 * Sets the <code>Color</code> (RGB values) for any coordinate with three
	 * RGB values.
	 *
	 * @param x
	 *            specifies the x (horizontal) index of the RGB value
	 * @param y
	 *            specifies the y (vertical) index of the RGB value
	 * @param red
	 *            specifies the new R value for the pixel
	 * @param green
	 *            specifies the new G value for the pixel
	 * @param blue
	 *            specifies the new B value for the pixel
	 *
	 * @throws IllegalArgumentException
	 *             if <code>x</code> or <code>y</code> is not a legal coordinate
	 *             in the picture or if <code>r</code>, <code>g</code>, or
	 *             <code>b</code> are not in [0,255]
	 */
	public void setColor(int x, int y, int red, int green, int blue) throws IllegalArgumentException {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255)
			throw new IllegalArgumentException("Picture.setColor R:G:B illegal: " + red + ":" + green + ":" + blue);
		if (x < 0 || x > _bufferImage.getWidth() || y < 0 || y > _bufferImage.getHeight())
			throw new IllegalArgumentException("Picture.setColor x:y illegal: " + x + ":" + y + "("
					+ _bufferImage.getWidth() + ":" + _bufferImage.getHeight() + ")");

		_bufferImage.setRGB(x, y, (255 << 24) | (red << 16) | (green << 8) | blue);
	}

	/**
	 * Sets the <code>Color</code> (RGB values) for any coordinate with one
	 * <b>Color</b> object.
	 *
	 * @param x
	 *            specifies the x (horizontal) index of the RGB value
	 * @param y
	 *            specifies the y (vertical) index of the RGB value
	 * @param c
	 *            specifies the new RGB values (as a <code>Color</code>)
	 *
	 * @throws IllegalArgumentException
	 *             if <code>x</code> or <code>y</code> is not a legal coordinate
	 *             in the picture
	 */
	public void setColor(int x, int y, Color c) throws IllegalArgumentException {
		if (x < 0 || x > _bufferImage.getWidth() || y < 0 || y > _bufferImage.getHeight())
			throw new IllegalArgumentException("Picture.setColor x:y illegal: " + x + ":" + y + "("
					+ _bufferImage.getWidth() + ":" + _bufferImage.getHeight() + ")");
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		_bufferImage.setRGB(x, y, (255 << 24) | (r << 16) | (g << 8) | b);
	}

	/**
	 * Returns the name of the file this picture came from.
	 *
	 *
	 */
	public String getFileName() {
		return _fileName;
	}

	/**
	 * Sets the name of the file for picture to <code>newFileName</code>.
	 *
	 * @return the name of the file this picture came from
	 */
	public void setFileName(String newFileName) {
		_fileName = newFileName;
	}

	/**
	 * Returns the width of this picture: number of horizontal pixels.
	 *
	 * @return the width of this picture: number of horizontal pixels
	 */
	public int getWidth() {
		return _width;
	}

	/**
	 * Returns the height of this picture: number of vertical pixels.
	 *
	 * @return the height of this picture: number of vertical pixels
	 */
	public int getHeight() {
		return _height;
	}

	/**
	 * Returns the buffered image of this picture. See the
	 * <code>BufferedImage</code> class for details.
	 *
	 * @return the buffered image of this picture
	 */
	public BufferedImage getBufferImage() {
		return _bufferImage;
	}

	/**
	 * Returns the image of this picture. See the <code>Image</code> class for
	 * details.
	 *
	 * @return the buffered image of this picture
	 */
	public Image getImage() {
		int[] pix = new int[_width * _height];

		for (int x = 0; x < _width; x++)
			for (int y = 0; y < _height; y++) {
				int rgb = _bufferImage.getRGB(x, y);
				pix[y * _width + x] = rgb;
			}

		return _jp.createImage(new MemoryImageSource(_width, _height, pix, 0, _width));
	}

	public Picture scale(int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(_bufferImage, 0, 0, width, height, null);
		g.dispose();
		return new Picture(_fileName, width, height, resizedImage);
	}

	/**
	 * Display the image of this picture in a window whose upper-left hand
	 * appears on the screen at coordinage (x,y). The picture is labelled by the
	 * file name it came from, and its width and height.
	 *
	 * @param x
	 *            specifies the x (horizontal) location of the upper-left hand
	 *            corner of the picture
	 * @param y
	 *            specifies the y (vertical) location of the upper-left hand
	 *            corner of the picture
	 */
	public void display(int x, int y) {
		display(new JFrame(), x, y);
	}

	/**
	 * Display the image of this picture in a window (in a <code>JFrame</code>)
	 * whose upper-left hand appears on the screen at coordinage (x,y); if both
	 * are not >0, the <code>JFrame</code> remains in its same location. The
	 * picture is labelled by the file name it came from, and its width and
	 * height.
	 *
	 * @param x
	 *            specifies the x (horizontal) location of the upper-left hand
	 *            corner of the picture
	 * @param y
	 *            specifies the y (vertical) location of the upper-left hand
	 *            corner of the picture
	 */
	public void display(JFrame toDisplay, int x, int y) {
		toDisplay.setTitle(_fileName + ": " + _width + " x " + _height);
		toDisplay.setSize(_width + 10, _height + 30);
		if (x >= 0 && y >= 0)
			toDisplay.setLocation(x, y);
		toDisplay.getContentPane().removeAll();
		toDisplay.getContentPane().add(new GIFPainter(this));
		toDisplay.show();
	}

	// Fields (Instance)

	private String _fileName;
	private int _width, _height;
	private BufferedImage _bufferImage;

	// Fields (Static)

	static Toolkit _tk = Toolkit.getDefaultToolkit();
	static JPanel _jp = new JPanel();

	/**
	 * This value be used in the constructor(s) to specify that the actual
	 * height/width of the picture should be used. Otherwise it will be scaled
	 * to the specified width/height.
	 */
	public static final int NATURAL_SIZE = -1;

	// GIFPainter is used in display; it overrides the paint method
	// of a standard JPanel to draw the image in the content
	// pane of the JFrame.
	private class GIFPainter extends JPanel {
		public GIFPainter(Picture p) {
			picture = p;
		}

		public void paint(Graphics g) {
			super.paint(g);
			setBackground(Color.white);
			g.drawImage(picture.getImage(), 0, 0, null);
		}

		private Picture picture;
	}

}
