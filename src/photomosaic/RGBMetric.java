//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.RGBMetric
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
//   This class implements all the Metric interface methods: makeSummary
// (overloaded) and distance.
//
//   makeSummary: computes the intensity of color for each pixel (by
// computing the Red, Green, and Blue values independently) and averaging
// them over all required pixels.
//
//   distance: computes the absolute values of the difference between
// two pairs of three R, G, and B values.
//
//
// Future Plans   : Javadoc Comments
//
// Program History:
//  10/30/02: R. Pattis - Operational for 15-100
//   9/04/04: R. Pattis - Updated (simplified) for 15-200
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package photomosaic;

import java.awt.Color;

class RGBMetric implements Metric {
	private int averageRed;
	private int averageGreen;
	private int averageBlue;

	public RGBMetric(int red, int green, int blue) {
		this.averageRed = red;
		this.averageGreen = green;
		this.averageBlue = blue;
	}

	public RGBMetric() {
		this(0, 0, 0);
	}

	@Override
	public Metric copy() {
		return new RGBMetric(averageRed, averageGreen, averageBlue);
	}

	@Override
	public void makeSummary(Picture p) {
		this.makeSummary(p, 0, 0, p.getWidth(), p.getHeight());
	}

	@Override
	public void makeSummary(Picture p, int rowStart, int columnStart, int width, int height) {
		int redSum = 0;
		int greenSum = 0;
		int blueSum = 0;
		for (int x = columnStart; x < columnStart + width; x++) {
			for (int y = rowStart; y < rowStart + height; y++) {
				try {
				Color rgb = p.getColor(x, y); 

				redSum += rgb.getRed();
				greenSum += rgb.getGreen();
				blueSum += rgb.getBlue();}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("rowstart:" + rowStart);
					System.out.println("colstart:" + columnStart);
					System.out.println(width + ", " + height + "(w, h");

				}
			}
		}
		averageRed = redSum / (width * height);
		averageGreen = greenSum / (width * height);
		averageBlue = blueSum / (width * height);
	}

	@Override
	/**
	 * The distance between two such metrics is just the sum of the absolute
	 * values of their component differences
	 */
	public double distanceTo(Metric m) {
		int r = averageRed - ((RGBMetric) m).averageRed;
		int g = averageGreen - ((RGBMetric) m).averageGreen;
		int b = averageBlue - ((RGBMetric) m).averageBlue;
		return Math.sqrt(r*r + g*g + b*b);
	}
}
