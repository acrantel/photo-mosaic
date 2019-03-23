//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Interface      : Decision
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
//   Clases implementing the Decision interface must define an isOK
// method, determining whether or not the object has the desired property.   
//
// Future Plans   : None
//
// Program History:
//   5/18/01: R. Pattis - Operational for 15-100
//   8/20/01: R. Pattis - JavaDoc comments added
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package edu.cmu.cs.pattis.cs151xx;





/** 
 * Clases implementing the <code>Decison</code> interface must define an
 *   <code>isOK</code> method, determining whether or note the parameter
 *   has the desired property.
 * <p>
 * A typical method will cast the object to the expected type and call
 *  some method that "really" makes the decision.
 * For example, if the object is really of type <code>String</code> we
 * might write
 * <pre><code>  public boolean isOK (Object o)
 *  {
 *    String s = (String)o;   
 *    return s.equals(s.toUppercase());  //All upper case letters
 *  }</code></pre>  
 * 
 * @author Richard E. Pattis (Computer Science Department, Carnegie Mellon)
**/
public interface Decision {

	/** 
	 * Return whether or not <code>o</code> has the desired property.
	 *
	 * @param  o  object for making the decision
	 * @return whether or not <code>o</code> has the desired property
	*/
	public boolean isOK(Object o);

}
