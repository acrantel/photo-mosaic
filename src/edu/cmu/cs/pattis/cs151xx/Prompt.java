//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : Prompt
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
//   The Prompt class is a (static) method library supplying simple methods
// for prompting the user (in the console window) for various types of
// information. All these methods reprompt the user if the response to the
// prompt is considered "illegal" (e.g., not the right type, not in range).
//
//   This class now includes a main driver for testing all its methods.
//
// Future Plans   : Low/High and Default values for Integer
//
// Program History:
//   8/20/01: R. Pattis - Operational for 15-100
//  12/24/01: R. Pattis - Added low/high for doubles, defaults for all
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

package edu.cmu.cs.pattis.cs151xx;



import java.io.*;							//For BufferedReader, InputStreamReader, IOException
import java.math.BigInteger;  //For BigInteger constructor





/** 
 * The <code>Prompt</code> class is a (static) method library supplying
 *   simple methods for prompting the user (in the console window) for
 *   various types of information.
 * <p>
 * All these methods reprompt the user if the response to the prompt
 *   is considered "illegal" (e.g., not the right type, or not correct
 *   according to semantic information).
 * Some methods specify allowable values (these appear in brackets).
 * Some methods specify default values (these appear in parentheses)
 * <p>
 * For example, <code>Prompt.forChar("Enter char", "aeiou", 'a')</code>
 *   displays as <code>Enter char[aeiou](a):</code> allowing the user to
 *   enter only a vowel character, with <code>a</code> as the default if
 *   the user immediately presses return.
 * <p>
 * These methods do not throw exceptions.
 * 
 * @author Richard E. Pattis (Computer Science Department, Carnegie Mellon)
*/
public class Prompt {



	/** 
	 * Returns the user's <code>int</code> response to the prompt message.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *
	 * @return the value entered by the user
	*/
  public static int forInt (String message)
  {
    for(;;)
      try {
        System.out.print(message+": ");
        return Integer.parseInt(keyboard.readLine());
      }
      catch (NumberFormatException e)
        {System.out.println("NumberFormatException: Please enter a valid integer ("+e.getMessage()+" was not valid)");}
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }

  
  
	/** 
	 * Returns the user's <code>int</code> response to the prompt message,
	 *   but only if it is in the range [low,high].
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values of <code>low</code> and 
	 *          <code>high</code>)
	 * @param  low  specifies the minimum value the user can enter
	 * @param  high specifies the maximum value the user can enter
	 *
	 * @return the value entered by the user
	*/
  public static int forInt (String message, int low, int high)
  {
    for(;;)
      try {
        int answer = Prompt.forInt(message+"["+low+","+high+"]");
        if (low <= answer && answer <= high)
          return answer;
        else
          throw new Exception("Please enter a value in the specified range");
      }
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }

  
  
	/** 
	 * Returns the user's <code>int</code> response to the prompt message,
	 *   or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static int forInt (String message, int defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"("+defaultValue+"): ");
        String answer = keyboard.readLine();
        if (answer.equals(""))
          return defaultValue;
        else
          return Integer.parseInt(answer);
      }
      catch (NumberFormatException e)
        {System.out.println("NumberFormatException: Please enter a valid integer ("+e.getMessage()+" was not valid)");}
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }

  
  
	/** 
	 * Returns the user's <code>int</code> response to the prompt message (
	 *   but only if it is in the range [low,high]), or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values of <code>low</code> and 
	 *          <code>high</code>)
	 * @param  low  specifies the minimum value the user can enter
	 * @param  high specifies the maximum value the user can enter
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static int forInt (String message, int low, int high, int defaultValue)
  {
    for(;;)
      try {
        int answer = Prompt.forInt(message+"["+low+","+high+"]",defaultValue);
        if (low <= answer && answer <= high)
          return answer;
        else
          throw new Exception("Please enter a value in the specified range");
      }
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }

  
  
	/** 
	 * Returns the user's <code>double</code> response to the prompt message.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *
	 * @return the value entered by the user
	*/
  public static double forDouble (String message)
  {
    for(;;)
      try {
        System.out.print(message+": ");
        return Double.parseDouble(keyboard.readLine());
      }
      catch (NumberFormatException e)
        {System.out.println("NumberFormatException: Please enter a valid double ("+e.getMessage()+" was not valid)");}
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }

  
  
	/** 
	 * Returns the user's <code>double</code> response to the prompt message,
	 *   but only if it is in the range [low,high].
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values of <code>low</code> and 
	 *          <code>high</code>)
	 * @param  low  specifies the minimum value the user can enter
	 * @param  high specifies the maximum value the user can enter
	 *
	 * @return the value entered by the user
	*/
  public static double forDouble (String message, double low, double high)
  {
    for(;;)
      try {
        double answer = Prompt.forDouble(message+"["+low+","+high+"]");
        if (low <= answer && answer <= high)
          return answer;
        else
          throw new Exception("Please enter a value in the specified range");
      }
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }

  
  
	/** 
	 * Returns the user's <code>double</code> response to the prompt message,
	 *   or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static double forDouble (String message, double defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"("+defaultValue+"): ");
        String answer = keyboard.readLine();
        if (answer.equals(""))
          return defaultValue;
        else
          return Double.parseDouble(answer);
      }
      catch (NumberFormatException e)
        {System.out.println("NumberFormatException: Please enter a valid double ("+e.getMessage()+" was not valid)");}
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }

  
  
	/** 
	 * Returns the user's <code>double</code> response to the prompt message
	 *  (but only if it is in the range [low,high]), or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values of <code>low</code> and 
	 *          <code>high</code>)
	 * @param  low  specifies the minimum value the user can enter
	 * @param  high specifies the maximum value the user can enter
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static double forDouble (String message, double low, double high, double defaultValue)
  {
    for(;;)
      try {
        double answer = Prompt.forDouble(message+"["+low+","+high+"]",defaultValue);
        if (low <= answer && answer <= high)
          return answer;
        else
          throw new Exception("Please enter a value in the specified range");
      }
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }

  
  
	/** 
	 * Returns the user's <code>String</code> response to the prompt message.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *
	 * @return the value entered by the user
	*/
  public static String forString (String message)
  {
    for(;;)
      try {
        System.out.print(message+": ");
        return keyboard.readLine();
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }


  
	/** 
	 * Returns the user's <code>String</code> response to the prompt message,
	 *   or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static String forString (String message, String defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"("+defaultValue+"): ");
        String answer = keyboard.readLine();
        return (answer.equals("") ? defaultValue : answer);
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }


  
	/** 
	 * Returns the user's <code>String</code> response to the prompt message,
	 *   but only if it is in the array of legal Strings.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values in <code>legal</code>)
	 * @param  legal specifies the legal values the user can entery
	 *
	 * @return the value entered by the user
	*/
  public static String forString (String message, String[] legal)
  {
    for(;;)
      try {
        System.out.print(message+"(");
        for (int i=0; i<legal.length; i++)
          System.out.print(legal[i]+(i==legal.length-1 ? "): " : " "));
        String answer = keyboard.readLine();
        for (int i=0; i<legal.length; i++)
          if (answer.equals(legal[i]))
            return answer;
        System.out.println("Please enter one of the legal Strings");
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }


  
	/** 
	 * Returns the user's <code>String</code> response to the prompt message,
	 *   (but only if it is in the list of legal strings), or the default
	 *   value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values in <code>legal</code>)
	 * @param  legal specifies the legal values the user can entery
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user
	*/
  public static String forString (String message, String[] legal, String defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"(");
        for (int i=0; i<legal.length; i++)
          System.out.print(legal[i]+(i==legal.length-1 ? "): " : " "));
        String answer = keyboard.readLine();
        if (answer.length() == 0)
          return defaultValue;
        for (int i=0; i<legal.length; i++)
          if (answer.equals(legal[i]))
            return answer;
        System.out.println("Please enter one of the legal Strings");
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }


  
	/** 
	 * Returns the user's <code>boolean</code> response to the prompt message.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *
	 * @return the value entered by the user
	*/
  public static boolean forBoolean (String message)
  {
    for(;;)
      try {
        System.out.print(message+": ");
        String temp = keyboard.readLine();
        if (temp.equals("true"))
          return true;
        if (temp.equals("false"))
          return false;
        System.out.println("Please enter true or false");
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }
  
  
  
	/** 
	 * Returns the user's <code>boolean</code> response to the prompt message,
	 *   or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static boolean forBoolean (String message, boolean defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"("+defaultValue+"): ");
        String temp = keyboard.readLine();
        if (temp.equals(""))
          return defaultValue;
        if (temp.equals("true"))
          return true;
        if (temp.equals("false"))
          return false;
        System.out.println("Please enter true or false");
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }
  
  
  
	/** 
	 * Returns the user's <code>char</code> response to the prompt message.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *
	 * @return the value entered by the user
	*/
  public static char forChar (String message)
  {
    for(;;)
      try {
        System.out.print(message+": ");
        String answer = keyboard.readLine();
        if (answer.length()==0)
          throw new Exception("Please enter one character");
        else
          return answer.charAt(0);
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }



	/** 
	 * Returns the user's <code>char</code> response to the prompt message,
	 *   or the default value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static char forChar (String message, char defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"("+defaultValue+"): ");
        String answer = keyboard.readLine();
        if (answer.length()==0)
          return defaultValue;
        else
          return answer.charAt(0);
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }



	/** 
	 * Returns the user's <code>char</code> response to the prompt message,
	 *   but only if it is in the list of legal characters.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values in <code>legal</code>)
	 * @param  legal specifies the legal values the user can enter
	 *
	 * @return the value entered by the user
	*/
  public static char forChar (String message, String legal)
  {
    for(;;)
      try {
        System.out.print(message+"["+legal+"]: ");
        String answer = keyboard.readLine();
        if (answer.length()==0 || legal.indexOf(answer.charAt(0))==-1 )
          throw new Exception("Please enter one legal character");
        else
          return answer.charAt(0);
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }



	/** 
	 * Returns the user's <code>char</code> response to the prompt message
	 *   (but only if it is in the list of legal characters), or the default
	 *   value.
	 * 
	 * @param  message specifies the prompt displayed to the user
	 *         (augmented by the values in <code>legal</code>)
	 * @param  legal specifies the legal values the user can enter
	 * @param  defaultValue specifies the value to return if the user
	 *         presses return without entering a value
	 *
	 * @return the value entered by the user (or <code>defaultValue</code>)
	*/
  public static char forChar (String message, String legal, char defaultValue)
  {
    for(;;)
      try {
        System.out.print(message+"["+legal+"]("+defaultValue+"): ");
        String answer = keyboard.readLine();
        if (answer.length()==0 && legal.indexOf(defaultValue)!=-1)
          return defaultValue;
        if (answer.length()!=0 && legal.indexOf(answer.charAt(0))!=-1)
          return answer.charAt(0);
        else
          throw new Exception("Please enter one legal character");
      }
      catch (IOException           e)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
      catch (Exception e)
        {System.out.println(e.getMessage());}
  }



	/** 
	 * Returns the user's <code>BigInteger</code> response to the prompt
	 *   message.
	 * 
	 * @param  message specifies the prompt displayed to the user
	*/
  public static BigInteger forBigInteger (String message)
  {
    for(;;)
      try {
        System.out.print(message+": ");
        return new BigInteger(keyboard.readLine());
      }
      catch (NumberFormatException E)
        {System.out.println("NumberFormatException: Please enter a valid integer ("+E.getMessage()+" was not valid)");}
      catch (IOException           E)
        {System.out.println("IOException: Terminating Program"); System.exit(0);}
  }



  //declare and initialize connection to keyboard shared by all methods
  
  private static BufferedReader keyboard =  new BufferedReader (new InputStreamReader(System.in));
  
 
  
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

	/** 
	 * Driver for testing methods in this class.
	 * 
	 * @param  args (ignored)
	*/
  public static void main(String[] args)
  {
     for(;;) {
       System.out.println("Prompt Driver");
       char s = Prompt.forChar("Enter type to prompt: i(nt), d(ouble), b(oolean), c(char), S(tring)","idbcS");
       
       if (s == 'i') {
         int answer = 0;
         s = intMenuPrompt();
	       if (s == 'p')
	         answer = Prompt.forInt("  Enter int");
	       else if (s == 'd')
	         answer = Prompt.forInt("  Enter int",0);
	       else if (s == 'r')
	         answer = Prompt.forInt("  Enter int",1,10);
	       else if (s == 'R')
	         answer = Prompt.forInt("  Enter int",1,10,1);
	     
	       System.out.println("  entered = " + answer);
	     }
	     
	     
       else if (s == 'd') {
         double answer = 0.;
         s = intMenuPrompt();
	       if (s == 'p')
	         answer = Prompt.forDouble("  Enter double");
	       else if (s == 'd')
	         answer = Prompt.forDouble("  Enter double",0.);
	       else if (s == 'r')
	         answer = Prompt.forDouble("  Enter double",0.,2.);
	       else if (s == 'R')
	         answer = Prompt.forDouble("  Enter double",0.,2.,.5);
	     
	       System.out.println("  entered = " + answer);
	     }
	     
	     
       else if (s == 'S') {
         String answer = "";
         s = booleanMenuPrompt();
	       if (s == 'p')
	         answer = Prompt.forString("  Enter String");
	       else if (s == 'd')
	         answer = Prompt.forString("  Enter String","default");
	     
	       System.out.println("  entered = " + answer);
	     }
	     
	     
       else if (s == 'b') {
         boolean answer = false;
         s = booleanMenuPrompt();
	       if (s == 'p')
	         answer = Prompt.forBoolean("  Enter boolean");
	       else if (s == 'd')
	         answer = Prompt.forBoolean("  Enter boolean",false);
	     
	       System.out.println("  entered = " + answer);
	     }
	     
	     
       else if (s == 'c') {
         char answer = '?';
         s = intMenuPrompt();
	       if (s == 'p')
	         answer = Prompt.forChar("  Enter char");
	       else if (s == 'd')
	         answer = Prompt.forChar("  Enter char",'!');
	       else if (s == 'r')
	         answer = Prompt.forChar("  Enter char","aeiou");
	       else if (s == 'R')
	         answer = Prompt.forChar("  Enter char","aeiou",'a');
	     
	       System.out.println("  entered = " + answer);
	     }
	     
	     
	   System.out.println();
	   }
  }
  
  private static char intMenuPrompt()
  {
     System.out.println("  p - plain");
     System.out.println("  d - with default");
     System.out.println("  r - with range");
     System.out.println("  R - with range/default");
     
     return Prompt.forChar("Enter Selection", "pdrR");
  }


  private static char booleanMenuPrompt()
  {
     System.out.println("  p - plain");
     System.out.println("  d - with default");
     
     return Prompt.forChar("Enter Selection", "pd");
  }
  
  
  
  
}


