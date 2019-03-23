//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : photomosaic.IntensityMetric
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
//   makeSummary: computes the intensity of each pixel (by computing the
// average of its Red, Green, and Blue values) and averaging them over
// all required pixels.
//
//   distance: computes the absolute values of the difference between
// two average values.
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



class IntensityMetric implements Metric {

  private int averageIntensity;
  

  public Metric copy()
  {return new IntensityMetric();}
  
  
  public void makeSummary(Picture p)
  {makeSummary(p,0,0,p.getWidth(),p.getHeight());}
  
  
  public void makeSummary(Picture p, int rowStart, int columnStart,  int width, int height)
  {
   int sum = 0;
   for (int x = columnStart; x < columnStart+width; x++)
     for (int y = rowStart; y < rowStart+height; y++) {
       Color rgb     = p.getColor(x,y);
       int intensity = (rgb.getRed() + rgb.getGreen() + rgb.getBlue() ) / 3;
       sum += intensity;
    }
    
    averageIntensity = sum/(width*height);
  }
 
 
  public double distanceTo(Metric m)
  {return Math.abs(averageIntensity-((IntensityMetric)m).averageIntensity);}
}
