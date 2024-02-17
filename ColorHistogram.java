import java.io.*;
import java.util.Arrays;

public class ColorHistogram {
    private int[] histogram;
    private int depth;
    private int[][][] pixels;

    public ColorHistogram(int d) {
        this.depth = d;
        int size = (int) Math.pow(2, d);
        this.histogram = new int[size];
    }

    public ColorHistogram(String filename) throws IOException {
        loadHistogram(filename);
    }

    public void setImage(int[][][] pixels) {
        this.pixels = pixels;
        int width = pixels.length;
        int height = pixels[0].length;

        Arrays.fill(histogram, 0); //reset histogram

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] pixel = pixels[i][j];
                int index = toIndex(pixel, depth);
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

    private void loadHistogram(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        int index = 0;
        while ((line = reader.readLine()) != null && index < histogram.length) {
            histogram[index++] = Integer.parseInt(line);
        }

        reader.close();
    }

    private int toIndex(int[] pixel, int d) {
        int index = 0;
        for (int i = 0; i < pixel.length; i++) {
            index |= (pixel[i] >> (8 - d)) << (2 * (pixel.length - i - 1) * d);
        }
        return index;
    }

    private int getTotalPixels() {
        int total = 0;
        for (int count : histogram) {
            total += count;
        }
        return total;
    }
}
