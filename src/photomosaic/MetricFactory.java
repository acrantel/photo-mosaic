//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.MetricFactory
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
//   This class implements a factory pattern for classes implementing
// Metric in the photomosaic application. When new metrics are written,
// update the methods here.
//
//   The code for a decorated metric is a bit more complicated, because
// it involves two classes, one decorating the other.
//
// Future Plans   : Javadoc Comments
//
// Program History:
//   9/04/04: R. Pattis - Operational for 15-200
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package photomosaic;

//Student must update this class

class MetricFactory {

	public static Metric getMetric(String name) {
		if (AVAILABLE_LIST.contains(name)) {

		}
		try {
			if (name.equals("QuadIntensityMetric")) {
				return new QuadMetric(new IntensityMetric());
			} else if (name.equals("QuadRGBMetric")) {
				return new QuadMetric(new RGBMetric());
			}

			if (AVAILABLE_LIST.indexOf(name) == -1) {
				throw new Exception();
			}
			return (Metric) Class.forName("photomosaic." + name).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"MetricFactory in getMetric: name(" + name + ") illegal\nKnown names=" + AVAILABLE_LIST);
		}
	}

	public static final String AVAILABLE_LIST = "IntensityMetric QuadIntensityMetric RGBMetric QuadRGBMetric";

}
