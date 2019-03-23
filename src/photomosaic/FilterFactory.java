//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.FilterFactory
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
// Filter in the photomosaic application. When new filters are written,
// update the methods here.
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

import java.awt.Color;

class DeltaFilter implements Filter {
	private final int[] deltas;

	public DeltaFilter(int[] deltas) {
		this.deltas = deltas;
	}

	public int getFiltered(int colorToFilter, Picture p, int x, int y) throws IllegalArgumentException {
		try {
			Color c = p.getColor(x, y);
			switch (colorToFilter) {
			case 0:
				return Math.max(0, Math.min(255, c.getRed() + deltas[0]));
			case 1:
				return Math.max(0, Math.min(255, c.getGreen() + deltas[1]));
			case 2:
				return Math.max(0, Math.min(255, c.getBlue() + deltas[2]));
			default:
				throw new IllegalArgumentException("IdentityFilter: colorToFilter(" + colorToFilter + ") illegal");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("IdentityFilter: (" + x + "," + y + ") not in picture");
		}
	}
}

class LighterFilter implements Filter {
	private final DeltaFilter d = new DeltaFilter(new int[] { 25, 25, 25 });

	public int getFiltered(int colorToFilter, Picture p, int x, int y) throws IllegalArgumentException {
		return d.getFiltered(colorToFilter, p, x, y);
	}
}

class DarkerFilter implements Filter {
	DeltaFilter d = new DeltaFilter(new int[] { -25, -25, -25 });

	public int getFiltered(int colorToFilter, Picture p, int x, int y) throws IllegalArgumentException {
		return d.getFiltered(colorToFilter, p, x, y);
	}
}

class RedderFilter implements Filter {
	DeltaFilter d = new DeltaFilter(new int[] { 25, 0, 0 });

	public int getFiltered(int colorToFilter, Picture p, int x, int y) throws IllegalArgumentException {
		return d.getFiltered(colorToFilter, p, x, y);
	}
}

class GreenerFilter implements Filter {
	DeltaFilter d = new DeltaFilter(new int[] { 0, 25, 0 });

	public int getFiltered(int colorToFilter, Picture p, int x, int y) throws IllegalArgumentException {
		return d.getFiltered(colorToFilter, p, x, y);
	}
}

class BluerFilter implements Filter {
	DeltaFilter d = new DeltaFilter(new int[] { 0, 0, 25 });

	public int getFiltered(int colorToFilter, Picture p, int x, int y) throws IllegalArgumentException {
		return d.getFiltered(colorToFilter, p, x, y);
	}
}

class FilterFactory {

	public static Filter getFilter(String name) {
		try {
			if (AVAILABLE_LIST.indexOf(name) == -1)
				throw new Exception();
			return (Filter) Class.forName("photomosaic." + name).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"FilterFactory in getFilter: name(" + name + ") illegal\nKnown names=" + AVAILABLE_LIST);
		}
	}

	public static final String AVAILABLE_LIST = "IdentityFilter GrayScaleFilter LighterFilter DarkerFilter RedderFilter GreenerFilter BluerFilter";
}
