package photomosaic;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import edu.cmu.cs.pattis.cs151xx.Decision;
import edu.cmu.cs.pattis.cs151xx.FileSelector;
import edu.cmu.cs.pattis.cs151xx.Prompt;

public class Model {
	/** the view used to display messages to the screen */
	private View view;
	/** a message left by whatever method executed most recently, indicating its status */
	private String message;
	/** the database of pictures to use for rendering */
	private ArrayList<PhotomosaicInfo> info = new ArrayList<PhotomosaicInfo>();
	/** the filter to apply to all incoming pictures */
	private Filter filter;
	/** the metric to use for evaluating pictures */
	private Metric metric;
	/** the picture to be rendered */
	private Picture picToRender;
	/** the rendered picture */
	private Picture renderedPicture;

	public Model(Filter filter, Metric metric, View v) {
		this.filter = filter;
		this.metric = metric;
		this.view = v;
	}

	// Refer to the view (used to call update after each button press)
	public void addView(View v) {
		view = v;
	}

	public String getResponse() {
		return message;
	}

	/**
	 * Lets the user select all the files to read (each a picture) and reads
	 * them, applying the appropriate filter/metric. If the parameters are not
	 * positive, or there are pictures already in the database and their sizes
	 * are different than the sizes specified by the parameters, return
	 * immediately with no changes to the database of pictures. Otherwise,
	 * display the new database of pictures.
	 * 
	 * @param width
	 *            The width that the loaded pictures should be scaled to.
	 * @param height
	 *            The height that the loaded pictures should be scaled to.
	 */
	public void loadMoreInPictureDatabase(int width, int height) {
		if (width <= 0 || height <= 0 || (!info.isEmpty()
				&& (width != info.get(0).getPicture().getWidth() || height != info.get(0).getPicture().getHeight()))) {
			message = "Unable to load pictures\nWidth: " + width + ", Height: " + height;
			view.update();
			return;
		}
		final String seperator = "|";
		FileSelector selector = FileSelector.getFileNamesSelector("Image Selector", "Open", ".jpg .jpeg .gif .png",
				seperator);
		String selected = selector.select();
		if (selected == null || selected.isEmpty()) {
			return;
		}
		/* The file names */
		String[] fileNames = selected.split("\\" + seperator);
		// Create and add the new PhotomosaicInfos to the ArrayList "info"
		this.message = "Attempting to load " + fileNames.length + " pictures";
		view.update();
		for (int i = 0; i < fileNames.length; i++) {
			Picture p = new Picture(fileNames[i], width, height);
			p.filter(filter);
			metric.makeSummary(p);
			info.add(new PhotomosaicInfo(metric.copy(), p));
			if (i % 50 == 0) {
				message = "  Loaded " + i + " pictures";
				view.update();
			}
		}
		this.displayPictureDatabase();
		message = "Loaded " + fileNames.length + " pictures" 
				+ "\nPicture database now contains " + info.size() + " pictures";
		view.update();
	}

	public void resetPictureDatabase() {
		info.clear();
	}

	public void displayPictureDatabase() {
		if (info.size() == 0) {
			message = "Picture database is empty";
			view.update();
			return;
		}
		/* The number of rows/columns of pictures in the catalog */
		int columns = ((int) Math.sqrt(info.size()));
		int rows = (int) Math.ceil(info.size() * 1.0 / columns);
		/* The width & height of the pictures */
		int width = this.getDatabasePictureSize().width;
		int height = this.getDatabasePictureSize().height;
		/* The picture to overlay the mini pictures onto */
		Picture catalog = Picture.makeEmptyPicture("Catalog", columns * width, rows * height);
		int i = 0;
		outer: for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				if (i >= info.size()) {
					break outer;
				}
				catalog.overlay(col * width, row * height, info.get(i).getPicture());
				i++;
			}
		}
		catalog.display(0, 0);
	}
	
	/**
	 * This method scans the database of pictures: for each picture that is OK 
	 * by the first parameter, a copy is made and filtered by the second parameter, 
	 * and added to the database. Display the new database of pictures.
	 * @param decision An object from a class implementing Decision to decide which 
	 * pictures in the database should be filtered/added
	 * @param filter Filters the images that are OK by the first parameter
	 * @param forString A String to tag the label of each new picture with (its 
	 * original name with this appended).
	 */
	public void shift(Decision decision, Filter filter, String forString) {
		int i = 0;
		int origSize = info.size();
		for (int j = 0; j < origSize; j++) {
			if (decision.isOK(info.get(j))) {
				Picture newPic = info.get(j).getPicture().copy(info.get(j).getPicture().getFileName() + forString);
				newPic.filter(filter);
				this.metric.makeSummary(newPic);
				info.add(new PhotomosaicInfo(this.metric.copy(), newPic));
				i++;
			}
		}
		this.displayPictureDatabase();
		message = "" + i + " pictures were added to the database";
		view.update();

	}

	/**
	 * One parameter, an object constructed from a class implementing
	 * Comparator. Sort the database of pictures using this parameter and
	 * redisplay it.
	 * 
	 * @param comparator
	 */
	public void sortPictureDatabase(Comparator<PhotomosaicInfo> comparator) {
		info.sort(comparator);
		this.displayPictureDatabase();
	}

	/**
	 * One parameter, an object constructed from a class implementing Filter. Do
	 * not modify the database of pictures, but apply the filter to all
	 * subsequent files that are loaded. If the filter is a null reference, do
	 * nothing.
	 * 
	 * @param filter
	 */
	public void setFilter(Filter filter) {
		if (filter == null)
			return;
		this.filter = filter;
	}

	/**
	 * One parameter, an object constructed from a class implementing Metric.
	 * Apply the metric to each picture in the database of pictures, and apply
	 * the metric to all subsequent files that are loaded (it will also be used
	 * in renderPicture). If the metric is a null reference, do nothing.
	 * 
	 * @param metric
	 */
	public void setMetric(Metric metric) {
		if (metric == null)
			return;
		this.metric = metric;
		for (PhotomosaicInfo p : info) {
			metric.makeSummary(p.getPicture());
			p.setMetric(metric.copy());
		}
		message = metric.getClass().getName() + " was applied to " + info.size() + " pictures.";
		view.update();
	}

	/**
	 * Let the user select all the file to read (a picture) and read it (if the 
	 * user presses cancel, do not change the state of the model). Otherwise, 
	 * apply the filter only (not metric) to the picture and display it.
	 */
	public void loadPictureToRender() {
		loadPictureToRender(Picture.NATURAL_SIZE, Picture.NATURAL_SIZE);
	}
	
	/**
	 * Let the user select all the file to read (a picture) and read it (if the 
	 * user presses cancel, do not change the state of the model). Otherwise, 
	 * apply the filter only (not metric) to the picture and display it.
	 */
	public void loadPictureToRender(int width, int height) {
		FileSelector selector = FileSelector.getFileNameSelector("Image Selector", "Open", ".jpg .jpeg .gif .png");
		String fileName = selector.select();
		if (fileName == null || fileName.isEmpty()) {
			return;
		}
		Picture pic = new Picture(fileName, width, height);
		pic.filter(filter);
		this.picToRender = pic;
		pic.display(0, 0);
		message = "Loaded " + fileName + "\nPicture is " + pic.getWidth() + "x" + pic.getHeight();
		view.update();
	}
	
	/**
	 * Scales the picture to be rendered to a user inputted size, then displays it
	 * @param width The width to scale picToRender to
	 * @param height The height to scale picToRender to
	 */
	public void scalePictureToRender(int width, int height) {
		if (picToRender == null) {
			message = "Picture to render was null.";
			view.update();
			return;
		}
		picToRender = picToRender.scale(width, height);
		picToRender.display(0, 0);
		message = "Scaled picture to render to width (" + width + ") and height (" + height + ")";
		view.update();
	}
	
	public Dimension getPictureToRenderSize() {
		if (this.picToRender == null) {
			return new Dimension(0, 0);
		} else {
			return new Dimension(picToRender.getWidth(), picToRender.getHeight());
		}
	}

	public Dimension getDatabasePictureSize() {
		if (info.isEmpty()) {
			return new Dimension(0, 0);
		} else {
			return new Dimension(info.get(0).getPicture().getWidth(), info.get(0).getPicture().getHeight());
		}
	}

	public int getPictureDatabaseLength() {
		return info.size();
	}

	/**
	 * Three parameters, the sample width, the maximum number of times each
	 * small picture can be used, and the minimum distance between reuses. If
	 * there is no picture to render, or no metric has been set, or any of the
	 * parameters don't make sense, the picture will not be rendered.
	 * Otherwise, the picture will be rendered and displayed in a new window. 
	 * 
	 * @param sampleWidth The width to use for each of the mini pictures
	 * @param maxTimesReuse The maximum number of times each small picture can be reused
	 * @param minDistance The minimum distance between reuses
	 */
	public void renderPicture(int sampleWidth, int maxTimesReuse, double minDistance) {
		if (picToRender == null || metric == null || info.size() == 0 || sampleWidth <= 0 || maxTimesReuse <= 0) {
			message = "Couldn't render picture";
			view.update();
			return;
		}
		// make a copy of the picture database so we can remove items after they
		// have been used the maximum number of times
		ArrayList<PhotomosaicInfo> newInfo = new ArrayList<PhotomosaicInfo>(info);
		int renderedWidth = getPictureToRenderSize().width;
		int renderedHeight = getPictureToRenderSize().height;
		int sampleHeight = (int) (sampleWidth * getDatabasePictureSize().getHeight()
				/ getDatabasePictureSize().getWidth());
		Picture renderedPicture = Picture.makeEmptyPicture("Rendered" + picToRender.getFileName(), renderedWidth,
				renderedHeight);
		// the number of columns / rows of mini pictures
		int columns = (int) (renderedWidth * 1.0 / sampleWidth);
		int rows = (int) (renderedHeight * 1.0 / sampleHeight);
		// used for rendering heartbeat
		int i = 0;
		final int total = rows * columns;
		final int[] beats = {0, total/10, total*2/10, total*3/10, total*4/10, total*5/10,
				total*6/10, total*7/10, total*8/10, total*9/10, total};
		// iterate through all the regions, and overlay the best fit over each region
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				Point curPoint = new Point(c, r);
				int columnStart = (c * sampleWidth);
				int rowStart = (r * sampleHeight);
				metric.makeSummary(this.picToRender, rowStart, columnStart, sampleWidth, sampleHeight);
				PhotomosaicInfo bestFit = getBestFit(curPoint, minDistance, metric, newInfo);
				bestFit.addUsed(curPoint);
				// Check if the bestFit has been used the max number of times, 
				// and if so, remove it from newInfo
				if (bestFit.getUsed() >= maxTimesReuse) {
					newInfo.remove(bestFit);
				}
				// Overlay the mini picture onto the rendered picture
				renderedPicture.overlay(columnStart, rowStart, bestFit.getPicture().scale(sampleWidth, sampleHeight));
				// Print the rendering heartbeat
				for (int j = 0; j < beats.length; j++) {
					if (i == beats[j]) { 
						message = "Rendering " + (j*10) + " percent done";
						view.update();
						break;
					}
				}
				i++;
			}
		}

		renderedPicture.display(0, 0);
		this.renderedPicture = renderedPicture;
	}
	
	/**
	 * Saves the currently rendered picture in a file. If renderedPicture
	 * is null, return.
	 */
	public void saveRenderedPicture() {
		if (this.renderedPicture == null)
			return;
		JFileChooser c = new JFileChooser();
		int approved = c.showSaveDialog(null);
		if (approved == JFileChooser.APPROVE_OPTION) {
			try {
				// retrieve image
				BufferedImage bi = this.renderedPicture.getBufferImage();
				File outputfile = new File(c.getSelectedFile().getAbsolutePath() + ".png");
				ImageIO.write(bi, "png", outputfile);
				System.out.println("Saved at " + outputfile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Picture getRenderedPicture() {
		return renderedPicture;
	}

	public boolean farEnough(PhotomosaicInfo pic, Point newPoint, double minDistance) {
		for (Point p : pic.getUsedPoints()) {
			if (newPoint.distance(p) < minDistance) {
				return false;
			}
		}
		return true;
	}

	public PhotomosaicInfo getBestFit(Point newPoint, double minDistance, Metric metric, ArrayList<PhotomosaicInfo> infos) {
		// return the first PhotomosaicInfo that is far enough
		PhotomosaicInfo best = null;
		double bestDistance = -1;
		for (PhotomosaicInfo i : infos) {
			if (farEnough(i, newPoint, minDistance) && best == null) {
				best = i;
				bestDistance = best.getMetric().distanceTo(metric);
			} else if (farEnough(i, newPoint, minDistance) && 
					bestDistance > i.getMetric().distanceTo(metric)) {
				best = i;
				bestDistance = i.getMetric().distanceTo(metric);
			}

		}
		return best;
	}
}