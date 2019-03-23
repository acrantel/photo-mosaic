//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.View
//
// Author         : Richard E. Pattis
//                  Computer Science Department
//                  Carnegie Mellon University
//                  5000 Forbes Avenue
//                  Pittsburgh, PA 15213-3891
//                  e-mail: pattis@cs.cmu.edu
//
// Maintainer     : Author
//
//
// Description:
//
//   This View for the photomosaic package should comprise a window contraining
// controls to operate this application. But, you will write it (and a Controller)
// for a later assignment. Instead, this class "fakes it" by being a driver program
// for the Model (with no Controller at all). You can test each of the methods that
// the controlls would call, in any sequence.
//
//   Note that "no access modifier" means that the method is package
// friendly: this means the member is public to all other classes in
// the calculator package, but private elsewhere.
//
// Future Plans   : More Comments
//
// Program History:
//   9/06/04: R. Pattis - Operational for 15-200
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package photomosaic;

import java.awt.Dimension;

import edu.cmu.cs.pattis.cs151xx.Prompt;

public class View // extends JFrame
{
	public static void main(String[] args) {
		View v = new View();
		v.addModel(new Model(new IdentityFilter(), new RGBMetric(), v));
		v.build();
	}

	public View() {
	}

	public void addModel(Model m) {
		model = m;
	}

	// For now (in a text-only view) echo it to the screen
	public void update() {
		System.out.println("model.getResponse() = " + model.getResponse());
	}

	// Present menu and get user's choice
	private static char menuPrompt() {
		System.out.println("\n\n");
		System.out.println("photomosaic.Model methods");
		System.out.println("  h - Help!");
		System.out.println("  ? - Comparators, Filters, and Metrics");
		System.out.println("  r - resetPictureDatabase");
		System.out.println("  l - loadMoreInPictureDatabase");
		System.out.println("  d - displayPictureDatabase");
		System.out.println("  s - shift");
		System.out.println("  < - resortPictureDatabase");
		System.out.println("  f - setFilter");
		System.out.println("  m - setMetric");
		System.out.println("  L - loadPictureToRender");
		System.out.println("  S - scalePictureToRender");
		System.out.println("  R - renderPicture");
		System.out.println("  q - quit");

		return Prompt.forChar("\nEnter Command", "h?rlds<fmLSRq");
	}

	////////////////
	//
	// Driver Program
	//
	////////////////

	public void build() {
		for (;;)
			try {
				char selection = menuPrompt();

				if (selection == 'q') {
					break;
				} else if (selection == 'h') {
					System.out.println();
					System.out.println("This is a general purpose program that produces photomosaics. A photomosaic is a large picture that is constructed from a collection of smaller pictures.");
					System.out.println();
					System.out.println("? - Gives you the list of Comparators, Filters, and Metrics that can be used for sorting, filtering, and comparing the database of pictures.");
					System.out.println("r - Resets the mini picture database.");
					System.out.println("l - Loads more mini pictures into the database through a file selector popup window. The width/height inputted must be equal to the width/heights of the pictures already in the database.");
					System.out.println("d - Displays the mini picture database in a new window.");
					System.out.println("s - This method scans the database of pictures: for each picture that is OK by the first parameter, a copy is made and filtered by the second parameter, and added to the database. Displays the new database of pictures.");
					System.out.println("< - Sorts the picture database according to a Comparator passed in, and displays the newly sorted database of pictures.");
					System.out.println("f - Does not modify the database of pictures, but this filter will be applied to all subsequent files that are loaded.");
					System.out.println("m - Applies the metric to each picture in the database of pictures, and applies the metric to all subsequent files that are loaded. This is the metric that will be used to render the picture. ");
					System.out.println("L - Loads the picture to render through a file selector popup window.");
					System.out.println("S - Scales the picture to render given a width and a height.");
					System.out.println("R - Renders the picture and displays the rendering.");
					System.out.println("q - Quits the application.");
				} else if (selection == '?') {
					System.out.println("Decsions    Available = " + DecisionFactory.AVAILABLE_LIST);
					System.out.println("Comparators Available = " + ComparatorFactory.AVAILABLE_LIST);
					System.out.println("Filters     Available = " + FilterFactory.AVAILABLE_LIST);
					System.out.println("Metrics     Available = " + MetricFactory.AVAILABLE_LIST);

				} else if (selection == 'r') {
					model.resetPictureDatabase();

				} else if (selection == 'l') {
					// Prompt for standard size for all pictures in the database
					int pWidth = Prompt.forInt("Enter width  for Picture DB images (in pixels)", 1, 1000, 13);
					int pHeight = Prompt.forInt("Enter height for Picture DB images (in pixels)", 1, 1000, 16);
					System.out.println("See Picture Database Selection pop-up window\n");
					model.loadMoreInPictureDatabase(pWidth, pHeight);

				} else if (selection == 'd') {
					model.displayPictureDatabase();

				} else if (selection == 's') {
					model.shift(DecisionFactory.getDecision(Prompt.forString("Enter Decision Name")),
							FilterFactory.getFilter(Prompt.forString("Enter Filter Name")),
							Prompt.forString("Enter tag for files shifted"));

				} else if (selection == '<') {
					model.sortPictureDatabase(
							ComparatorFactory.getComparator(Prompt.forString("Enter Comparator Name")));

				} else if (selection == 'f') {
					model.setFilter(FilterFactory.getFilter(Prompt.forString("Enter Filter Name")));

				} else if (selection == 'm') {
					model.setMetric(MetricFactory.getMetric(Prompt.forString("Enter Metric Name")));

				} else if (selection == 'L') {
					System.out.println("\nSee Picture to Render Selection pop-up window\n");
					model.loadPictureToRender();
				} else if (selection == 'S') {
					int width = Prompt.forInt("Enter the width of the picture to be loaded, or -1 for the original width", -1);
					int height = Prompt.forInt("Enter the height of the picture to be loaded, or -1 for the original height", -1);
					if (width <= 0) { width = Picture.NATURAL_SIZE; }
					if (height <= 0) { height = Picture.NATURAL_SIZE; }
					model.scalePictureToRender(width, height);
				} else if (selection == 'R') {
					Dimension pictureToRenderSize = model.getPictureToRenderSize();
					int pWidth = pictureToRenderSize.width;
					int pHeight = pictureToRenderSize.height;

					if (pWidth > 0) {
						Dimension databasePictureSize = model.getDatabasePictureSize();
						int dbpWidth = databasePictureSize.width;
						int dbpHeight = databasePictureSize.height;
						int dbpLength = model.getPictureDatabaseLength();
						System.out.println("Picture to Render is " + pWidth + " x " + pHeight);

						// Prompt for (and compute) rendering info for the
						// sample size
						final int sampleWidth = Prompt.forInt("Enter # pixels for width in sample", 1, pWidth,
								dbpWidth);
						final int sampleHeight = (int) (sampleWidth * dbpHeight / dbpWidth);
						System.out.println("Sample height (conforming to image database) " + sampleHeight);

						final int tilesWide = pWidth / sampleWidth;
						final int tilesHigh = pHeight / sampleHeight;
						final int tilesTotal = tilesWide * tilesHigh;
						System.out.println("Rendered picture will contain " + tilesTotal + " tiles: " + tilesWide
								+ " x " + tilesHigh + "\n");

						// Prompt for (and compute) min/max tile use
						int minTimesReuse = tilesTotal / dbpLength + (tilesTotal % dbpLength == 0 ? 0 : 1);
						int maxTimesReuse = Prompt.forInt("Enter maximum # of times to reuse any picture",
								minTimesReuse, tilesTotal, minTimesReuse);
						double minDistance = Prompt.forDouble("Enter minimum distance between picture reuse ", 1,
								Math.max(tilesWide, tilesHigh), 1);

						model.renderPicture(sampleWidth, maxTimesReuse, minDistance);
						//TODO
						char yesNo = Prompt.forChar("Save rendered picture? y/n: ", "y n Y N");
						if (yesNo == 'y' || yesNo == 'Y') {
							System.out.println("See save as popup window");
							model.saveRenderedPicture();
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void show() {
	}

	public void update(int dbpW, int dbpH, int pW, int pH, int dbpS) {
	}

	Model model;
	// private int _dbpWidth,_dbpHeight,_pWidth,_pHeight,_dbpSize;
}
