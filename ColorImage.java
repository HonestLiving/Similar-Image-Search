import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ColorImage {
    private int width;
    private int height;
    private int depth; // Number of bits per pixel
    private int[][][] pixels; // 3D array to store RGB values

    // Constructor to create an image from a file
    public ColorImage(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.depth = 24; // Assuming 24 bits per pixel (8 bits per channel)

        this.pixels = new int[width][height][3];

        // Populate pixels array with RGB values from BufferedImage
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                // Extract red, green, and blue components
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                // Store RGB values in the pixels array
                pixels[i][j] = new int[] {red, green, blue};
            }
        }
    }

    // Getter methods
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    // Method to get RGB value of a pixel at position (i, j)
    public int[] getPixel(int i, int j) {
        return pixels[i][j];
    }

    // Method to reduce color space to d-bit representation
    public void reduceColor(int d) {
        // Assuming d-bit representation means reducing each channel's value to d bits
        int maxVal = (int) Math.pow(2, d) - 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] rgb = pixels[i][j];
                for (int k = 0; k < 3; k++) {
                    rgb[k] = (rgb[k] * maxVal) / 255; // Scale RGB value to d bits
                }
            }
        }
        // Update depth to reflect new bit depth
        depth = d;
    }

    public static void main(String[] args) {
        try {
            ColorImage image = new ColorImage("example.jpg");
            // Example usage
            System.out.println("Width: " + image.getWidth());
            System.out.println("Height: " + image.getHeight());
            System.out.println("Depth: " + image.getDepth());
            int[] pixel = image.getPixel(0, 0);
            System.out.println("Pixel at (0, 0): R=" + pixel[0] + ", G=" + pixel[1] + ", B=" + pixel[2]);
            image.reduceColor(4); // Reduce color space to 4-bit representation
            System.out.println("New depth after reducing color space: " + image.getDepth());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
