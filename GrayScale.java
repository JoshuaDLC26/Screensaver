import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GrayScale  {
    private BufferedImage imageToProcess;
    private static boolean DEBUG = false;

    public GrayScale(BufferedImage inputImage) {
        this.imageToProcess = inputImage;
    }

    public void convertToGrayscale() {
        final int width = imageToProcess.getWidth();
        final int height = imageToProcess.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imageToProcess.setRGB(i, j, argbToLuminosity(imageToProcess.getRGB(i, j)));
            }
        }
    }

    //For error checking.
    public void printPixelValues(BufferedImage im1) {
        final int width = im1.getWidth();
        final int height = im1.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.println("im 1 value:" + im1.getRGB(i, j));
            }
        }
    }

    //We calculate grayscale values via luminosity rather than a direct conversion to intensity.
    //A direct conversion of average RGB to grayscale values is not accurate to human eye perception.
    //See the following for a reference and description of coefficients used below:
    //https://en.wikipedia.org/wiki/Relative_luminance

    //Also note. The following implements the canonical method of extracting rgb values from data stored in the argb conventional format.
    //For a reference, see the following:
    //https://stackoverflow.com/questions/8436196/explaining-bitwise-shifting-argb-values-in-java
    private int argbToLuminosity(int argb) {
        //Extract rgb values via bit shifting.
        int r = (argb >> 16) & 0xff;
        int g = (argb >> 8) & 0xff;
        int b = argb & 0xff;
        int luminosityValue = (int) ((((argb >> 16) & 0xff)) * .2126 + ((argb >> 8) & 0xff) * .7152 + (argb & 0xff) * .0722);
        Color luminosityColor = new Color(luminosityValue, luminosityValue, luminosityValue);
        return luminosityColor.getRGB();
    }

    public BufferedImage getImage() {
        return imageToProcess;
    }

    //We expect the entered string to be "FILENAME.jpg", ...
    public void writeImage(String pathName) {
        try {
            File outputImageFile = new File(pathName);
            ImageIO.write(imageToProcess, "jpg", outputImageFile);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}