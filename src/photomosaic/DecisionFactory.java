//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.DecisionFactory
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
// Decision in the photomosaic application. When new metrics are written,
// update the methods here.
//
//   The code for a decorated metric is a bit more complicated, because
// it involves two classes, one decorating the other.
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

import edu.cmu.cs.pattis.cs151xx.Decision;

class NotShiftedDecision implements Decision {
	public boolean isOK(Object o) {
		PhotomosaicInfo p = ((PhotomosaicInfo) o);
		return p.getPicture().getFileName().indexOf("shifted") == -1;
	}
}

class AllDecision implements Decision {
	public boolean isOK(Object o) {
		return true;
	}
}

class DecisionFactory {

	public static Decision getDecision(String name) {
		try {
			if (AVAILABLE_LIST.indexOf(name) == -1)
				throw new Exception();
			return (Decision) Class.forName("photomosaic." + name).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"DecisionFactory in getDecision: name(" + name + ") illegal\nKnown names=" + AVAILABLE_LIST);
		}
	}

	public static final String AVAILABLE_LIST = "AllDecision NotShiftedDecision";

}
