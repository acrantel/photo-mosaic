//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.Metric
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
//   This simple interface defines the three methods that all implementing
// classes must support. The copy method makes a copy of the object. The first
// makeSummary method examines that entire picture. The second makeSummary
// method examines only the specified rectangular subportion of the picture.
//
// The distance method compares the values stored in two metrics to determine
// how closely they match; it should have the standard distance properties
//
//   1) x.distanceTo(x) = 0
//   2) x.distanceTo(y) = y.distanceTo(x)
//   3) x.distanceTo(y) + y.distanceTo(z) >= x.distanceTo(z)
//
// Future Plans   : JavaDoc Comments
//
// Program History:
//  10/30/02: R. Pattis - Operational for 15-100
//   9/04/04: R. Pattis - Updated (simplified) for 15-200
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package photomosaic;

interface Metric {
	public Metric copy();

	public void makeSummary(Picture p);

	public void makeSummary(Picture p, int rowStart, int columnStart, int width, int height);

	public double distanceTo(Metric m);
}
