/**
   ColorImage.java
   ---------------------------------------
   Programmers: Kevin Yao (300295024), Matthew Chen (300288244)
   Course: CSI2120
   ---------------------------------------
   This class Constructs a ColorImage object from a specified image file. 
   The image file must be in jpg format.
*/ 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ColorImage {
    private int width;
    private int height;
    private int depth;
    private int[][][] pixels;

    //reads jpg
    public ColorImage(String filename) throws IOException {
        File currentFolder = new File(System.getProperty("user.dir")); //get current directory
        File queryImages = new File(currentFolder, "queryImages");  //known directory name
        File imageFile = new File(queryImages, filename);

        BufferedImage image = ImageIO.read(imageFile);
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.depth = 8; //given

        this.pixels = new int[width][height][3];

        //processes the BufferedImage
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] rgb = pixels[i][j];
                for (int k = 0; k < 3; k++) {
                    rgb[k] = rgb[k] >> (8 - d); //given bitshift formula in instructions
                }
            }
        }
        depth = d;
    }
}
