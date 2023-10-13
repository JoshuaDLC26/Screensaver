import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BayerDither {
    private BufferedImage imageToProcess;
    final private int WHITE = Color.WHITE.getRGB();
    final private int BLACK = Color.BLACK.getRGB();
    private double[][] fourByFourDither = new double[4][4];

    public BayerDither(BufferedImage im)    {
        this.imageToProcess = im;
        hardcodeFourByFourDither(fourByFourDither);
    }

    //This is ugly but efficient. Modify to generate by recursion if time remains.
    private void hardcodeFourByFourDither(double[][] arr)   {
        arr[0][0] =  0;
        arr[0][1] = (double) 8/16;
        arr[0][2] = (double) 2/16;
        arr[0][3] = (double) 10/16;
        arr[1][0] = (double) 12/16;
        arr[1][1] = (double) 4/16;
        arr[1][2] = (double) 14/16;
        arr[1][3] = (double) 6/16;
        arr[2][0] = (double) 3/16;
        arr[2][1] = (double) 11/16;
        arr[2][2] = (double) 1/16;
        arr[2][3] = (double) 9 /16;
        arr[3][0] = (double) 15 /16;
        arr[3][1] = (double) 7 / 16;
        arr[3][2] = (double) 13 /16;
        arr[3][3] = (double) 5 / 16;
    }

    public void convert() {
        gsConvert(imageToProcess);
        final int width = imageToProcess.getWidth();
        final int height = imageToProcess.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (((imageToProcess.getRGB(i,j)) & 0xff) > (255*fourByFourDither[i%4][j%4]))    {
                    imageToProcess.setRGB(i,j, WHITE);
                }
                else {
                    imageToProcess.setRGB(i,j, BLACK);
                }
            }
        }
    }

    private void gsConvert(BufferedImage im)    {
        GrayScale gs = new GrayScale(im);
        gs.convertToGrayscale();
        this.imageToProcess = gs.getImage();
    }

    public BufferedImage getImage() {
        return imageToProcess;
    }

    //We expect the entered string to be "FILENAME.jpg", contained within the directory the program is run in.
    public void writeImage(String pathName) {
        try {
            File outputImageFile = new File(pathName);
            ImageIO.write(imageToProcess, "jpg", outputImageFile);
        } catch (IOException e) {
            System.err.println(e);
        }
    }


}