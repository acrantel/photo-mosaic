package photomosaic;



/**
 *The <code>Filter</code> interface specifies one method that
 *  determines the filtered RGB values to use for any pixel in a picture.
*/
interface Filter {
  /**
   * Returns the value to use for the specified color component in the
   *   specified pixel.
   * The value returned must be in the range [0,255].
   * It is allowed to access other (probably nearby) locations
   *   in the <code>Picture p</code>.
   *
   * @param colorToFilter specifies the color:
   *    0 is red, 1 is green, 2 is blue (see constants)
   * @param p specifies some <code>Picture</code>
   * @param x specifies the x coordinate of the pixel
   * @param y specifies the y coordinate of the pixel
   *
   * @return the value to use for (R, G, or B) in the filtered image
   *
   * @throws IllegalArgumentException if <code>colorToFilter</code> is
   *   not in the range [0,2] or <code>p[x,y]</code> is not in the
   *   picture
  */
  public int getFiltered (int colorToFilter, Picture p, int x, int y)
     throws IllegalArgumentException;
 
     
  /**
   * Constant specifying the red pixel value in <code>getFiltered.
  */
  final public static int RED   = 0;
     
  /**
   * Constant specifying the green pixel value in <code>getFiltered.
  */
  final public static int GREEN = 1;
     
  /**
   * Constant specifying the blue pixel value in <code>getFiltered.
  */
  final public static int BLUE  = 2;
}