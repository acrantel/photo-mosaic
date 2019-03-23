package photomosaic;


import java.awt.Color;



/**
 *The <code>GrayScaleFilter</code> interface specifies one method that
 *  returns the same values for R, G, and B (based on a weighted
 *  sum of these values in the pixel).
 *The reason the magnitude of the coefficients, is that the human
 *  visual system has many more green receptors than red, and many
 *  more red than blue: thus, most of the intensity that we perceive
 *  from a colored image comes from its green, then red, then blue
 *  intensities.
*/
class GrayScaleFilter implements Filter {
  /**
   * Returns the value to use for the specified color component in the
   *   specified pixel.
   * The value returned must be in the range [0,255].
   * It is allowed to access other (probably nearby) locations
   *   in the <code>Picture p</code>.
   *
   * @param colorToFilter specifies the color:
   *    0 is red, 1 is green, 2 is blue (see these constants in the interface)
   * @param p specifies some <code>Picture</code>
   * @param x specifies the x coordinate of the pixel
   * @param y specifies the y coordinate of the pixel
   *
   * @return  the value to use for (R, G, and B) in a gray-scale image
   *
   * @throws IllegalArgumentException if <code>colorToFiler</code> is
   *   not in the range [0,2] or <code>p[x,y]</code> is not in the
   *   picture
  */
  public int getFiltered (int colorToFilter, Picture p, int x, int y)
     throws IllegalArgumentException
  {
    try {
      Color c = p.getColor(x,y);
      int i = (int)( 0.222*c.getRed() + 0.707*c.getGreen() + 0.071*c.getBlue() );
      switch(colorToFilter) {
        case RED   : return i;    
        case GREEN : return i;    
        case BLUE  : return i;    
        default    : throw new IllegalArgumentException("GrayScaleFilter: colorToFilter("+colorToFilter+") illegal");
      }
    }catch (IllegalArgumentException e) //from p.getColor()
    {throw new IllegalArgumentException("GrayScaleFilter: ("+x+","+y+") not in picture");}    
  }
}