/**
   SimilaritySearch.java
   ---------------------------------------
   Programmers: Kevin Yao (300295024), Matthew Chen (300288244)
   Course: CSI2120
   ---------------------------------------
   This class takes the query image filename and the image dataset directory as command line 
   arguments and searches for the 5 most similar images to the query image.
*/ 

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SimilaritySearch {
    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println("Please enter: java SimilaritySearch <fileName> <folderName>");
            return;
        }
        
        //get the filename and directory path from cmd
        String fileName = args[0];
        String folderName = args[1];    
        ArrayList<String> intersections = new ArrayList<String>();
        ArrayList<Double> intersectionsValue = new ArrayList<Double>();

        ColorImage img;
        try {
            img = new ColorImage(fileName);
            img.reduceColor(3);
            ColorHistogram queryImg = new ColorHistogram(3);
            queryImg.setImage(img);
            queryImg.getHistogram();
            queryImg.save(fileName + " histogram");

            File currentFolder = new File(System.getProperty("user.dir")); // Get current directory
            File dataset = new File(currentFolder, folderName);
            File[] allFiles = dataset.listFiles(); // List all files in the directory
            for (File file : allFiles) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    ColorHistogram datasetImg = new ColorHistogram(file.getName());
                    intersections.add(file.getName() + " " + queryImg.compare(datasetImg));
                    intersectionsValue.add(queryImg.compare(datasetImg));
                }
            }            
            Collections.sort(intersectionsValue);
            Collections.reverse(intersectionsValue);

            for (int i = 0; i < 5; i++) {
                for (String element : intersections) {
                    String[] parts = element.split(" "); //split the element based on a space
                    String name = parts[0]; //extract file name
                    double intersectionValue = Double.parseDouble(parts[1]); //extract double
                    if (intersectionsValue.get(i) == intersectionValue) {
                        System.out.println(i + ". " + name + ": " + intersectionsValue.get(i));
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


}
