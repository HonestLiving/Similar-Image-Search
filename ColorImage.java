import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ColorImage {
    private int width;
    private int height;
    private int depth; // $ of bits per pixel
    private int[][][] pixels; //3D array to store RGB values

    //constructor create an image from a PPM file
    public ColorImage(String filename) throws IOException {
        readPPM(filename);
    }

    //Read ppm
    private void readPPM(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        //PPM header
        String magicNumber = br.readLine().trim();
        if (!magicNumber.equals("P3")) {
            throw new IOException("Invalid PPM file format");
        }

        //reading width, height, and depth
        String[] dimensions = br.readLine().trim().split("\\s+");
        this.width = Integer.parseInt(dimensions[0]);
        this.height = Integer.parseInt(dimensions[1]);
        this.depth = Integer.parseInt(br.readLine().trim());

        //pixels array
        this.pixels = new int[width][height][3];

        //read pixel data
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                String[] rgb = br.readLine().trim().split("\\s+");
                pixels[i][j][0] = Integer.parseInt(rgb[0]);
                pixels[i][j][1] = Integer.parseInt(rgb[1]);
                pixels[i][j][2] = Integer.parseInt(rgb[2]);
            }
        }

        br.close();
    }

    // Getter
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    // get RGB value of a pixel at position (i, j)
    public int[] getPixel(int i, int j) {
        return pixels[i][j];
    }

    //reduce color space to d-bit representation
    public void reduceColor(int d) {
        //assuming d-bit representation means reducing each channel's value to d bits
        int maxVal = (int) Math.pow(2, d) - 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] rgb = pixels[i][j];
                for (int k = 0; k < 3; k++) {
                    rgb[k] = (rgb[k] * maxVal) / 255; //scale RGB value to d bits
                }
            }
        }
        //update depth to new depth
        depth = d;
    }

    public static void main(String[] args) {
        try {
            ColorImage image = new ColorImage("example.jpg");
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
