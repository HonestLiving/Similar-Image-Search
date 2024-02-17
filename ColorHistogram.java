/**
   ColorHistogram.java
   ---------------------------------------
   Programmers: Kevin Yao (300295024), Matthew Chen (300288244)
   Course: CSI2120
   ---------------------------------------
   This class creates and reads a histogram of color values in an image. 
   It can create a histogram for a ColorImage object, calculate and retrieve the normalized histogram, 
   compare histograms with other instances, and saving histograms to files, and read histograms from
   .txt files.
*/ 

import java.io.*;

public class ColorHistogram {
    private int[] histogram;
    private int depth;
    private int numOfColors;

    public ColorHistogram(int d) {
        this.depth = d;
        int size = (int) Math.pow(2, d*3);
        this.histogram = new int[size];
    }

    public ColorHistogram(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        int index = 0;
        line = reader.readLine();
        numOfColors = Integer.parseInt(line);
        this.histogram = new int[numOfColors];
        while (index < histogram.length) {
            line = reader.readLine();
            if (line != null) {
                histogram[index] = Integer.parseInt(line);
                index++;
            } else {
                break;
            }
        }

        reader.close();
    }

    public void setImage(ColorImage image) {
        //reduce pixel values and count occurrences
        int width = image.getWidth();
        int height = image.getHeight();
        int depth = image.getDepth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] pixel = image.getPixel(i, j);
                int red = pixel[0] >> (8 - depth);
                int green = pixel[1] >> (8 - depth);
                int blue = pixel[2] >> (8 - depth);
                int index = (red << (2 * depth)) + (green << depth) + blue;
                histogram[index]++;
            }
        }
    }

    public double[] getHistogram() {
        double[] normalizedHistogram = new double[histogram.length];
        int totalPixels = getTotalPixels();

        for (int i = 0; i < histogram.length; i++) {
            normalizedHistogram[i] = (double) histogram[i] / totalPixels;
        }

        return normalizedHistogram;
    }

    public double compare(ColorHistogram hist) {
        double[] hist1 = this.getHistogram();
        double[] hist2 = hist.getHistogram();
        double intersection = 0.0;

        for (int i = 0; i < hist1.length; i++) {
            intersection += Math.min(hist1[i], hist2[i]);
        }

        return intersection;
    }

    public void save(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (int i = 0; i < histogram.length; i++) {
            writer.write(histogram[i] + "\n");
        }

        writer.close();
    }

    private int getTotalPixels() {
        int total = 0;
        for (int count : histogram) {
            total += count;
        }
        return total;
    }
}
