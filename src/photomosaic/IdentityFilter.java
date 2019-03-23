package photomosaic;


import java.awt.Color;



/**
 *The <code>IdentityFilter</code> interface specifies one method that
 *  returns the same R, G, and B values as the pixel it is given.
*/
class IdentityFilter implements Filter {
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
      switch(colorToFilter) {
        case RED   : return c.getRed();    
        case GREEN : return c.getGreen();    
        case BLUE  : return c.getBlue();
        default    : throw new IllegalArgumentException("IdentityFilter: colorToFilter("+colorToFilter+") illegal");
      }
    }catch (IllegalArgumentException e) //from p.getColor()
    {throw new IllegalArgumentException("IdentityFilter: ("+x+","+y+") not in picture");}    
  }
}