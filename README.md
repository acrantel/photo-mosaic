# photo-mosaic
This is a general purpose program that produces photomosaics. A photomosaic is a large picture that is constructed from a collection of smaller pictures.   

Examples    
--------
Original:   
![original picture 1](examples/original-pic1.jpg)   
Rendered:    
![photomosaic picture 1](examples/mosaic-pic1.png)   

Original:   
![original picture 2](examples/original-pic2.jpeg)   
Rendered:   
![photomosaic picture 2](examples/mosaic-pic2.png)   

Running the Program    
-------------------
A typical user-interaction with the program through the console:    
```
photomosaic.Model methods
  h - Help!
  ? - Comparators, Filters, and Metrics
  r - resetPictureDatabase
  l - loadMoreInPictureDatabase
  d - displayPictureDatabase
  s - shift
  < - resortPictureDatabase
  f - setFilter
  m - setMetric
  L - loadPictureToRender
  S - scalePictureToRender
  R - renderPicture
  q - quit

Enter Command[h?rlds<fmLSRq]: m
Enter Metric Name: QuadRGBMetric
model.getResponse() = photomosaic.QuadMetric was applied to 0 pictures.



photomosaic.Model methods
  h - Help!
  ? - Comparators, Filters, and Metrics
  r - resetPictureDatabase
  l - loadMoreInPictureDatabase
  d - displayPictureDatabase
  s - shift
  < - resortPictureDatabase
  f - setFilter
  m - setMetric
  L - loadPictureToRender
  S - scalePictureToRender
  R - renderPicture
  q - quit

Enter Command[h?rlds<fmLSRq]: l
Enter width  for Picture DB images (in pixels)[1,1000](13): 20
Enter height for Picture DB images (in pixels)[1,1000](16): 20
See Picture Database Selection pop-up window

model.getResponse() = Attempting to load 836 pictures
model.getResponse() =   Loaded 0 pictures
model.getResponse() =   Loaded 50 pictures
model.getResponse() =   Loaded 100 pictures
model.getResponse() =   Loaded 150 pictures
model.getResponse() =   Loaded 200 pictures
model.getResponse() =   Loaded 250 pictures
model.getResponse() =   Loaded 300 pictures
model.getResponse() =   Loaded 350 pictures
model.getResponse() =   Loaded 400 pictures
model.getResponse() =   Loaded 450 pictures
model.getResponse() =   Loaded 500 pictures
model.getResponse() =   Loaded 550 pictures
model.getResponse() =   Loaded 600 pictures
model.getResponse() =   Loaded 650 pictures
model.getResponse() =   Loaded 700 pictures
model.getResponse() =   Loaded 750 pictures
model.getResponse() =   Loaded 800 pictures
model.getResponse() = Loaded 836 pictures
Picture database now contains 836 pictures



photomosaic.Model methods
  h - Help!
  ? - Comparators, Filters, and Metrics
  r - resetPictureDatabase
  l - loadMoreInPictureDatabase
  d - displayPictureDatabase
  s - shift
  < - resortPictureDatabase
  f - setFilter
  m - setMetric
  L - loadPictureToRender
  S - scalePictureToRender
  R - renderPicture
  q - quit

Enter Command[h?rlds<fmLSRq]: L

See Picture to Render Selection pop-up window

model.getResponse() = Loaded C:\xxx\photo-mosaic\examples\original-pic1.jpg
Picture is 1024x680



photomosaic.Model methods
  h - Help!
  ? - Comparators, Filters, and Metrics
  r - resetPictureDatabase
  l - loadMoreInPictureDatabase
  d - displayPictureDatabase
  s - shift
  < - resortPictureDatabase
  f - setFilter
  m - setMetric
  L - loadPictureToRender
  S - scalePictureToRender
  R - renderPicture
  q - quit

Enter Command[h?rlds<fmLSRq]: R
Picture to Render is 1024 x 680
Enter # pixels for width in sample[1,1024](20): 10
Sample height (conforming to image database) 10
Rendered picture will contain 6936 tiles: 102 x 68

Enter maximum # of times to reuse any picture[9,6936](9): 300
Enter minimum distance between picture reuse [1.0,102.0](1.0): 2
model.getResponse() = Rendering 0 percent done
model.getResponse() = Rendering 10 percent done
model.getResponse() = Rendering 20 percent done
model.getResponse() = Rendering 30 percent done
model.getResponse() = Rendering 40 percent done
model.getResponse() = Rendering 50 percent done
model.getResponse() = Rendering 60 percent done
model.getResponse() = Rendering 70 percent done
model.getResponse() = Rendering 80 percent done
model.getResponse() = Rendering 90 percent done
Save rendered picture? y/n: [y n Y N]: y
See save as popup window
Saved at C:\xxx\mosaic.png
```

First, the user selects the metric that will be used to compare pictures. Here, QuadRGBMetric is selected, which is generally the best for color photos.   

Second, the user loads pictures into the database with a file-selection window. The program reads in all the small pictures the user selects, resizes them, and adds them to the database. It then displays the database in a new window.     

Third, the user specifies a picture to render through another file-selection window. This picture is displayed in a new window after loading it.    

Finally, the user renders the picture as a photomosaic, specifying the width/height of the small pictures and the amount of reuse allowed.    

Possible Commands (Help)   
------------------------
```
? - Gives you the list of Comparators, Filters, and Metrics that can be used for sorting, filtering, and comparing the database of pictures.   
r - Resets the mini picture database.   
l - Loads more mini pictures into the database through a file selector popup window. The width/height inputted must be equal to the width/heights of the pictures already in the database.   
d - Displays the mini picture database in a new window.   
s - This method scans the database of pictures: for each picture that is OK by the first parameter, a copy is made and filtered by the second parameter, and added to the database. Displays the new database of pictures.   
< - Sorts the picture database according to a Comparator passed in, and displays the newly sorted database of pictures.   
f - Does not modify the database of pictures, but this filter will be applied to all subsequent files that are loaded.   
m - Applies the metric to each picture in the database of pictures, and applies the metric to all subsequent files that are loaded. This is the metric that will be used to render the picture.    
L - Loads the picture to render through a file selector popup window.   
S - Scales the picture to render given a width and a height.   
R - Renders the picture and displays the rendering.   
q - Quits the application.   
```
