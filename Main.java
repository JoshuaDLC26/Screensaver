import java.awt.*;
import java.io.File;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.filechooser.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//class that  rotates the image and also sets the screen
public class Main extends JFrame implements KeyListener {
    //inspiration from Homework 02 Bouncing Spheres
    public static final int FPS = 60;
    class Runner implements Runnable{
        public Runner(){}
        public void run(){
            while(true){
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }
        }

    }
    private static final long serialVersionUID = 1L;
    private static Image TestImage;
    private static Image Background;
    private static Main ri;
    private BufferedImage bf;
    private int cordX = 100;
    private int cordY = 100;
    private int cordX2 = 400;
    private int cordY2 = 400;
    private double currentAngle;
    private int duration = 30;
    private int newSeed = 0;
    Random rand = new Random(1234);
    BufferedImage image;
    BufferedImage image2;
    BufferedImage image3;


    private static Clip clip;
    private static Clip clip2;
    private static Clip clip3;
    private static Clip clip4;
    private static Clip clip5;
    private static Clip clip6;
    private static Clip clip7;
    private static Clip clip8;
    private static Clip clip9;
    private static Clip clip10;
    private Main(Image TestImage) {
        this.TestImage = TestImage;
        this.Background = Background;
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(TestImage, 0);
        mt.addImage(Background,0);
        try {
            mt.waitForID(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("Screensaver");
        setSize(1000, 750);
        imageLoader();
        setVisible(true);
        Thread mainThread = new Thread(new Runner());
        mainThread.start();

    }
    private void rotate() {
        //rotate 5 degrees at a time
        currentAngle+=5.0;
        if (currentAngle >= 360.0) {
            currentAngle = 0;
        }
        repaint();

    }

    // method that loads the image the user inputs using jFIleChooser + sets the background image
    private void imageLoader() {

        try {
            String testPath = "test.jpeg";
            //source for creating bufferedImage https://www.geeksforgeeks.org/image-processing-in-java-read-and-write/
            TestImage = ImageIO.read(new File("projectImages/output.jpeg"));
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            Background = ImageIO.read(new File("projectImages/gradientBackground.jpeg"));
            // source used for fileChooser https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                File fileChosen = chooser.getSelectedFile();
                image = ImageIO.read(fileChosen);
                //resizedImage =image.getScaledInstance(image.getWidth()/10,image.getHeight()/10,Image.SCALE_DEFAULT);
                BayerDither bd = new BayerDither(image);
                bd.convert();
            }

            JFileChooser chooser2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int result2 = chooser2.showOpenDialog(null);
            if (result2 == JFileChooser.APPROVE_OPTION) {
                File fileChosen2 = chooser2.getSelectedFile();
                image2 = ImageIO.read(fileChosen2);
                GrayScale gs = new GrayScale(image2);
                gs.convertToGrayscale();
            }
            JFileChooser chooser3 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int result3 = chooser3.showOpenDialog(null);
            if (result3 == JFileChooser.APPROVE_OPTION) {
                File fileChosen3 = chooser3.getSelectedFile();
                image3 = ImageIO.read(fileChosen3);
                GrayScale gs2 = new GrayScale(image3);
                gs2.convertToGrayscale();
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addKeyListener(this);
    }
    // method that updates the screen
    public void update(Graphics g) {
        paint(g);
    }

    //draws the images to the screen
    public void paint(Graphics g){
        bf = new BufferedImage( this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);

        try{
            animation(bf.getGraphics());
            g.drawImage(bf,0,0,null);

        }catch(Exception ex){

        }
    }


    //method that animates each image
    // the picture in the center rotates upon its center and is controlled by any of the keys being pressed
    // the two images that move around the screen are controlled by the up, down, right, and left keys and move opposite of each other
    private void animation(Graphics g) {

        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        int xBackground = (getWidth() - Background.getWidth(this))/2;
        int yBackground = (getHeight() - Background.getHeight(this))/2;
        g2d.drawImage(Background,xBackground,yBackground,this);
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        //center of rotation is center of the panel
        int xRot = this.getWidth()/2;
        int yRot = this.getHeight()/2;
        newXform.rotate(Math.toRadians(currentAngle), xRot, yRot);
        g2d.setTransform(newXform);
        //draw image centered in panel
        int xImage = (getWidth() - image.getWidth(this))/2;
        int yImage = (getHeight() - image.getHeight(this))/2;
        g.drawImage(image, xImage, yImage, this);
        g2d.setTransform(origXform);
        g.drawImage(image2, cordX, cordY, this);
        g2d.setTransform(origXform);
        g.drawImage(image3, cordX2, cordY2, this);
        //drawing random shapes around the screen
        if (duration == 0){
            duration = 30;
            newSeed = rand.nextInt(550);
        }
        else{
            duration-=1;
        }
        Random random = new Random(newSeed);
        for (int i = 0; i < random.nextInt(newSeed); i++) {
            int width_height = random.nextInt(10,30);
            g.setColor(Color.CYAN);
            int xPosition = random.nextInt(this.getWidth());
            int YPosition = random.nextInt(this.getHeight());
            int xPositionC = random.nextInt(this.getWidth());
            int YPositionC = random.nextInt(this.getHeight());
            g.fillOval(xPositionC, YPositionC, width_height, width_height);
            g.setColor(Color.getHSBColor(283, 43, 57));
            g.fillRect(xPosition, YPosition, width_height, width_height);
            g.setColor(Color.PINK);
            g.fillOval(xPositionC, YPositionC, 15, 15);
        }
        // source for time format https://www.javatpoint.com/java-get-current-date
        Font font = new Font("Monospaced", Font.PLAIN, 35);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        g2d.drawString(formatter.format(date), this.getWidth() / 4 + 60, this.getHeight() / 4 - 100);

    }


    // main method that outputs instructions in the command line to resize the image + plays the elevator music in the background
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("");
        System.out.println("Before selecting the file you want to use in your ScreenSaver you need to resize the image you will use");
        System.out.println("Use this link: https://www.simpleimageresizer.com");
        System.out.println("Use a .jpeg image or a .png image. If the image is a rectangle with width greater than height use dimensions 600x400 (this is flexible too based on scaling of image)");
        System.out.println("If the image has height greater than width, use 550x500 or more depending on how your image is shaped. DO NOT make it more than 550");
        System.out.println("Do NOT make the image more than 600-650");
        System.out.println("");
        System.out.println("When selecting images you'll be prompted to pick 3 different images 3 different times");
        System.out.println("If you want three different images to display choose 3 different images each time the directory pops up");
        System.out.println("");
        System.out.println("Press keys 1-0 to play music");
        System.out.println("Press up, down, left, right keys to animate the images ");
        ri = new Main(TestImage);
        File file = new File("Elevator.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        File file2 = new File("Music1.wav");
        AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);
        clip2 = AudioSystem.getClip();
        clip2.open(audioStream2);
        File file3 = new File("Music2.wav");
        AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(file3);
        clip3 = AudioSystem.getClip();
        clip3.open(audioStream3);
        File file4 = new File("Music3.wav");
        AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(file4);
        clip4 = AudioSystem.getClip();
        clip4.open(audioStream4);
        File file5 = new File("Music4.wav");
        AudioInputStream audioStream5 = AudioSystem.getAudioInputStream(file5);
        clip5 = AudioSystem.getClip();
        clip5.open(audioStream5);
        File file6 = new File("Music5.wav");
        AudioInputStream audioStream6 = AudioSystem.getAudioInputStream(file6);
        clip6 = AudioSystem.getClip();
        clip6.open(audioStream6);
        File file7 = new File("Music6.wav");
        AudioInputStream audioStream7 = AudioSystem.getAudioInputStream(file7);
        clip7 = AudioSystem.getClip();
        clip7.open(audioStream7);
        File file8 = new File("Music7.wav");
        AudioInputStream audioStream8 = AudioSystem.getAudioInputStream(file8);
        clip8 = AudioSystem.getClip();
        clip8.open(audioStream8);
        File file9 = new File("Music8.wav");
        AudioInputStream audioStream9 = AudioSystem.getAudioInputStream(file9);
        clip9 = AudioSystem.getClip();
        clip9.open(audioStream9);
        File file10 = new File("Music9.wav");
        AudioInputStream audioStream10 = AudioSystem.getAudioInputStream(file10);
        clip10 = AudioSystem.getClip();
        clip10.open(audioStream10);
    }
    //each time the left, right, up, down keys are pressed the image in the center rotates and the two images move up and down, left and right opposite of each other
    public void keyPressed(KeyEvent ke) {

        switch (ke.getKeyCode()) {
            case KeyEvent.VK_RIGHT: {
                cordX += 5;
                cordX2 -=5;
                ri.rotate();
            }
            break;
            case KeyEvent.VK_LEFT: {
                cordX2+=5;
                cordX -= 5;
                ri.rotate();
            }
            break;
            case KeyEvent.VK_DOWN: {
                cordY += 5;
                cordY2-=5;
                ri.rotate();
            }
            break;
            case KeyEvent.VK_UP: {
                cordY -= 3;
                cordY2+=3;
                ri.rotate();
            }
            break;
            case KeyEvent.VK_1:{
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip.start();
                clip.loop(20);
            }
            break;
            case KeyEvent.VK_2:{
                clip.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip2.start();
                clip2.loop(20);
            }
            break;
            case KeyEvent.VK_3:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip3.start();
                clip3.loop(20);
            }
            break;
            case KeyEvent.VK_4:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip4.start();
                clip4.loop(20);
            }
            break;
            case KeyEvent.VK_5:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip5.start();
                clip5.loop(20);
            }
            break;
            case KeyEvent.VK_6:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip6.start();
                clip6.loop(20);
            }
            break;
            case KeyEvent.VK_7:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip8.stop();
                clip9.stop();
                clip10.stop();
                clip7.start();
                clip7.loop(20);
            }
            break;
            case KeyEvent.VK_8:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip9.stop();
                clip10.stop();
                clip8.start();
                clip8.loop(20);
            }
            break;
            case KeyEvent.VK_9:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip10.stop();
                clip9.start();
                clip9.loop(20);
            }
            break;
            case KeyEvent.VK_0:{
                clip.stop();
                clip2.stop();
                clip3.stop();
                clip4.stop();
                clip5.stop();
                clip6.stop();
                clip7.stop();
                clip8.stop();
                clip9.stop();
                clip10.start();
                clip10.loop(20);
            }
            break;
        }
        repaint();
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }


}
