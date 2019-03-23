package photomosaic;

import java.awt.Point;
import java.util.ArrayList;

class PhotomosaicInfo {
	private Metric metric;
	private Picture picture;
	private ArrayList<Point> usedPlaces = new ArrayList<Point>();

	public PhotomosaicInfo(Metric metric, Picture picture) {
		// TODO
		this.metric = metric;
		this.picture = picture;
	}

	public Picture getPicture() {
		return picture;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public void resetUsed() {
		usedPlaces.clear();
	}
	
	public int getUsed()
	{
		return usedPlaces.size();
	}

	public void addUsed(Point p) {
		usedPlaces.add(p);
	}
	
	public ArrayList<Point> getUsedPoints() {
		return usedPlaces;
	}

}
