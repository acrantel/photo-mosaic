//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.ComparatorFactory
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
// Comparator. They all are assumed to be passed PhotomosiacInfo arguments.
// When new comparators are written, update the methods here.
//
// Future Plans   : Javadoc Comments
//                  Put classes in separate files
//
// Program History:
//   9/04/04: R. Pattis - Operational for 15-200
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package photomosaic;

import java.util.Comparator;

class ByNameComparator implements Comparator<PhotomosaicInfo> {
	public int compare(PhotomosaicInfo p1, PhotomosaicInfo p2) {
		return p1.getPicture().getFileName().compareTo(p2.getPicture().getFileName());
	}
}

class ByMetricComparator implements Comparator<PhotomosaicInfo> {
	public int compare(PhotomosaicInfo p1, PhotomosaicInfo p2) {
		Metric m = p1.getMetric().copy();

		if (p1.getMetric().distanceTo(m) < p2.getMetric().distanceTo(m))
			return -1;
		else if (p1.getMetric().distanceTo(m) > p2.getMetric().distanceTo(m))
			return +1;
		else
			return 0;
	}
}

class ByFrequencyComparator implements Comparator<PhotomosaicInfo> {
	public int compare(PhotomosaicInfo p1, PhotomosaicInfo p2) {
		return p1.getUsed() - p2.getUsed();
	}
}

class ComparatorFactory {

	public static Comparator<PhotomosaicInfo> getComparator(String name) {
		try {
			if (AVAILABLE_LIST.indexOf(name) == -1)
				throw new Exception();
			return (Comparator<PhotomosaicInfo>) Class.forName("photomosaic." + name).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"ComparatorFactory in getComparator: name(" + name + ") illegal\nKnown names=" + AVAILABLE_LIST);
		}
	}

	public static final String AVAILABLE_LIST = "ByNameComparator ByMetricComparator ByFrequencyComparator";
}
