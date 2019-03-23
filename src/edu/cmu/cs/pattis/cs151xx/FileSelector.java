//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : FileSelector
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
//   Objects constructed from the <code>FileSelector</code> class are
// used to display a GUI file selector and make a selection.  Objects are not
// constructed directly through the constructor: it is private! Instead,
// they are constructed through one of the two static methods:
// getFileNameSelector or getFileNamesSelector; the only difference in
// these names is ...Name... vs ...Names...
//
// Note that each time select is called, its GUI is set
//   to the same directory as when the last selection was made.
//
// Future Plans   : Javadoc private constructor/fields
//                  Allow recursive traversal for files in
//                    getFileNamesRecursiveSelector
//
// Program History:
//   5/18/01: R. Pattis - Operational for 15-100
//   8/20/01: R. Pattis - JavaDoc comments added
//   9/04/04: R. Pattis - Revamped to allow persistence
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package edu.cmu.cs.pattis.cs151xx;



import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;



/** 
 * Objects constructed from the <code>FileSelector</code> class are used
 *   to display a GUI file selector and make a selection.
 * Objects are not constructed directly through the constructor: it is
 *   private!
 * Instead, they are constructed through one of the two
 *   <code>static</code> methods: <code>getFileNameSelector</code> or
 *   <code>getFileNamesSelector</code>; the only difference in these
 *   names is <code>...Name...</code> vs <code>...Names...</code>.
 * <p>
 * To construct and object and use it to have the user make a selection,
 *    follow the form
 * <code><pre>  FileSelector fs = FileSelector.getFileNameSelector("Label","Action","*");
 *  String fileName = fs.select();</pre></code>
 * <p>
 * Note that each time <code>select</code> is called, its GUI is made
 *   visible and set to the same directory as when the last selection
 *   was made.
 * 
 * @author Richard E. Pattis (Computer Science Department, Carnegie Mellon)
*/
public class FileSelector
{
   private FileSelector(String  titleLabel,
                        String  actionLabel,
                        boolean multipleFiles,
                        String  extensions,
                        String  separator)
   {
     this.titleLabel    = titleLabel;
     this.actionLabel   = actionLabel;
     this.multipleFiles = multipleFiles;
     this.extensions    = extensions;
     this.separator     = separator;
     this.fileChooser   = getImageChooser(500,0,0);
   }
   
   
   
   /**
    * Returns a <code>FileSelector</code> object which can be used to
    *   select a single file.
    *
    * @param titleLabel  specifies the title of the file chooser window
    * @param actionLabel specifies the lable on the action button (what
    *                      the user presses when the selected file is
    *                      highlighted)
    * @param extensions  specifies the allowable extensions (case
    *                      insensitive): e.g.,
    *                       <code>".jpg .jpeg .gif"</code>; "*" means
    *                       all extensions
    *
    * @return a <code>FileSelector</code> object which can be used to
    *   select a single file
   */
   public static FileSelector getFileNameSelector (String titleLabel, String actionLabel, String extensions)
   {return new FileSelector(titleLabel,actionLabel,false,extensions,"");}
   
   
   
   /**
    * Returns a <code>FileSelector</code> object which can be used to
    *   select any number of files (including one).
    *
    * @param titleLabel  specifies the title of the file chooser window
    * @param actionLabel specifies the lable on the action button (what
    *                      the user presses when the selected file is
    *                      highlighted)
    * @param extensions  specifies the allowable extensions (case
    *                      insensitive): e.g.,
    *                       <code>".jpg .jpeg .gif"</code>; "*" means
    *                       all extensions
    * @param separator   specifies the separator used to separate the
    *                      file names when <code>select</code> returns
    *                      its result.
    *
    * @return a <code>FileSelector</code> object which can be used to
    *   select any number of files
   */
   public static FileSelector getFileNamesSelector (String titleLabel, String actionLabel, String extensions, String separator)
   {return new FileSelector(titleLabel,actionLabel,true,extensions,separator);}
   
   
   
   /**
    * Returns one or more file names (constrained by the
    *   <code>FileSelector</code> this method is called on).
    * If no files are selected when one file is expected, <code>null</code>
    *   is returned; if multiple files are expected, an empty
    *   <code>String</code> is returned.
    * If multiple files are allowed, their names are separated by the
    *   <code>separator</code> parameter in the constructor.
    * Use a <code>StringTokenizer</code> to retreive the individual names.
    *
    * @return one or more file names (constrained by the
    *   <code>FileSelector</code> this method is called on)
   */
   public String select ()
   {
    if (!multipleFiles) {
      int choice  = fileChooser.showDialog(null,actionLabel);
      File toLoad =  (choice == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile() : null);
      return (toLoad == null ? null : toLoad.getPath());

    }else{
      StringBuffer answer = new StringBuffer();
      
      int choice    = fileChooser.showDialog(null,actionLabel);
      File[] toLoad = (choice == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFiles() : null);
      
      if (toLoad == null)
        return "";
        
        
      int filesIn = 0;
      for (int i=0; i<toLoad.length; i++)
        if ( toLoad[i].isFile() )
          //Add name to answer
          answer.append((filesIn++==0?"":separator)+toLoad[i].getPath());
        else
          try {
            //Load files from folder, but not recursively (not files in subfolders)
            File[] fileNames = toLoad[i].listFiles();
            for (int j=0; j<fileNames.length; j++) {
              String fileName = fileNames[j].getCanonicalPath();
              int    index = fileName.lastIndexOf(".");
              String extension = (index == -1 ? "" : fileName.substring(fileName.lastIndexOf(".")).toLowerCase());
             if (extensions.indexOf(extension) != -1)
               answer.append((filesIn++==0?"":separator)+fileName);
            }
          } catch (IOException ioe) {}
        
      return answer.toString();
    }
   }
   
  /**
   * Returns a <code>JFileChooser</code> set up appropriately for
   *   choosing files with the options specified at construction.
   *
   * @param size           specifies size of window
   * @param x              specifies x coordinate of window
   * @param y              specifies y coordinate of window
   *
   * @return set up appropriately for
   *   choosing files with the options specified at construction
  */
  private JFileChooser getImageChooser(int size, int x, int y)
  {
    JFileChooser imageChoices  = new JFileChooser();
    
 	  imageChoices.setLocation(x,y);
    imageChoices.setPreferredSize(new Dimension(size,size)); 
	  imageChoices.setDialogTitle(titleLabel);
    imageChoices.setCurrentDirectory(new File("."));
    imageChoices.setFileSelectionMode(
      multipleFiles ? JFileChooser.FILES_AND_DIRECTORIES : JFileChooser.FILES_ONLY);
    imageChoices.setMultiSelectionEnabled(multipleFiles);
            
	  //Create a new JFileChooser and set it up correctly
    imageChoices.setAcceptAllFileFilterUsed(false);
    
    //Set FileFilter to examine only some files
    imageChoices.setFileFilter(new FileFilter(){
      public boolean accept(File f)
      { 
        if (extensions.equals("*"))
          return true;
        int index = f.getName().lastIndexOf(".");
        String extension = (index == -1 ? "" :
                                          f.getName().substring(f.getName().lastIndexOf(".")).toLowerCase());
        return f.isDirectory() ||
               extensions.indexOf(extension) != -1;
      }
      
      public String getDescription()
      {return extensions;}
    }); 
    
    return imageChoices;
  } 
 
 

  //Instance variables
  
  private final JFileChooser fileChooser;
  
  private final String  titleLabel;
  private final String  actionLabel;
  private final boolean multipleFiles;
  private final String  extensions;
  private final String  separator;
}