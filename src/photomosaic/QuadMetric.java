//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.QuadMetric
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
// (overloaded) and distance, decorating any Metric that it is given, by
// storing four copies of it.
//
//   makeSummary: computes the intensity of each pixel (applying the decorated
// metric to each of the quadrants (upper-left, upper-right, lower-left,
// and lower-right) in the picture
//
//   distance: computes the distance as the sum of the distances of the metric
// used for each quadrant.
//
// Future Plans   : JavaDoc Comments
//
// Program History:
//  10/30/02: R. Pattis - Operational for 15-100
//  11/06/02: R. Pattis - Simpilfied by using GrayScaleMetric[Value]
//   9/04/04: R. Pattis - Updated (simplified as a decorator) for 15-200
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package photomosaic;

class QuadMetric implements Metric {

	/** upper left, upper right, lower left, lower right */
	private Metric ul, ur, ll, lr;

	public QuadMetric(Metric m) {
		this(m.copy(), m.copy(), m.copy(), m.copy());
	}
	
	public QuadMetric(Metric ul, Metric ur, Metric ll, Metric lr) {
		this.ul = ul;
		this.ur = ur;
		this.ll = ll;
		this.lr = lr;
	}

	public Metric copy() {
		return new QuadMetric(ul.copy(), ur.copy(), ll.copy(), lr.copy());
	}

	public void makeSummary(Picture p) {
		makeSummary(p, 0, 0, p.getWidth(), p.getHeight());
	}

	public void makeSummary(Picture p, int rowStart, int columnStart, int width, int height) {
		ul.makeSummary(p, rowStart, columnStart, width / 2, height / 2);
		ur.makeSummary(p, rowStart, columnStart + height / 2, width / 2, height / 2);
		ll.makeSummary(p, rowStart + width / 2, columnStart, width / 2, height / 2);
		lr.makeSummary(p, rowStart + width / 2, columnStart + height / 2, width / 2, height / 2);
	}

	public double distanceTo(Metric m) {
		QuadMetric m2 = (QuadMetric) m;
		return ul.distanceTo(m2.ul) + ur.distanceTo(m2.ur) + ll.distanceTo(m2.ll) + lr.distanceTo(m2.lr);
	}
}
