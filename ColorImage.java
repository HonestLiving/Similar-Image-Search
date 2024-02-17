import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ColorImage {
    private int width;
    private int height;
    private int depth;
    private int[][][] pixels;

    public ColorImage(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.depth = 8;

        this.pixels = new int[width][height][3];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                pixels[i][j] = new int[] {red, green, blue};
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int[] getPixel(int i, int j) {
        return pixels[i][j];
    }

    public void reduceColor(int d) {
        int maxVal = (int) Math.pow(2, d) - 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] rgb = pixels[i][j];
                for (int k = 0; k < 3; k++) {
                    rgb[k] = (rgb[k] * maxVal) / 255;
                }
            }
        }
        depth = d;
    }

    public static void main(String[] args) {
        try {
            ColorImage image = new ColorImage("example.jpg");
            System.out.println("Width: " + image.getWidth());
            System.out.println("Height: " + image.getHeight());
            System.out.println("Depth: " + image.getDepth());
            int[] pixel = image.getPixel(5, 5);
            System.out.println("Pixel at (0, 0): R=" + pixel[0] + ", G=" + pixel[1] + ", B=" + pixel[2]);
            image.reduceColor(3);
            System.out.println("New depth after reducing color space: " + image.getDepth());
            System.out.println("Pixel at (0, 0): R=" + pixel[0] + ", G=" + pixel[1] + ", B=" + pixel[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
