//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.image.BufferedImage;
import org.w3c.dom.css.RGBColor;
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


public class RotateImages extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static Image TestImage;
    private static Image Background;
    private static RotateImages ri;
    private JPanel jpanel;
    private BufferedImage bf;
    private int cordX = 100;
    private int cordY = 100;
    private int cordX2 = 400;
    private int cordY2 = 400;

    private double currentAngle;

    Random rand = new Random();
    static JLabel l;
    BufferedImage image;
    BufferedImage image1;

    BufferedImage image2;

    public RotateImages(Image TestImage) {
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
        //setBackground(Color.BLUE);
        imageLoader();
        setVisible(true);

    }
    public void rotate() {
        //rotate 5 degrees at a time
        currentAngle+=5.0;
        if (currentAngle >= 360.0) {
            currentAngle = 0;
        }
        repaint();

    }


    public void imageLoader() {
        try {
            String testPath = "test.jpeg";
            ///Users/daniela/Downloads/Screen Saver Final Project/src/projectImages/output.jpeg;
            //TestImage = ImageIO.read(new File("/Users/daniela/Downloads/Screen Saver Final Project/src/projectImages/output.jpeg"));
            TestImage = ImageIO.read(new File("projectImages/output.jpeg"));
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            chooser.setMultiSelectionEnabled(true);
            Background = ImageIO.read(new File("projectImages/gradientBackground.jpeg"));
            //TestImage = ImageIO.read(new File());
            // BufferedImage image = null;
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION){
                File fileChosen = chooser.getSelectedFile();
                //String fileName = fileChosen.getAbsolutePath();
                image = ImageIO.read(fileChosen);
            }
            JFileChooser chooser1 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            //TestImage = ImageIO.read(new File());
            // BufferedImage image = null;
            int result1 = chooser.showOpenDialog(null);
            if (result1 == JFileChooser.APPROVE_OPTION){
                File fileChosen1 = chooser1.getSelectedFile();
                //String fileName = fileChosen.getAbsolutePath();
                image1 = ImageIO.read(fileChosen1);
            }
            JFileChooser chooser2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            //TestImage = ImageIO.read(new File());
            // BufferedImage image = null;
            int result2 = chooser2.showOpenDialog(null);
            if (result2 == JFileChooser.APPROVE_OPTION){
                File fileChosen2 = chooser.getSelectedFile();
                //String fileName = fileChosen.getAbsolutePath();
                image2 = ImageIO.read(fileChosen2);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addKeyListener(this);
    }

    public void update(Graphics g){
        paint(g);
    }

    public void paint(Graphics g){
        bf = new BufferedImage( this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);

        try{
            animation(bf.getGraphics());
            g.drawImage(bf,0,0,null);
        }catch(Exception ex){

        }
    }

    public void animation(Graphics g) {
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
        int x = (getWidth() - TestImage.getWidth(this))/2;
        int y = (getHeight() - TestImage.getHeight(this))/2;
        int xImage = (getWidth() - image.getWidth(this))/2;
        int yImage = (getHeight() - image.getHeight(this))/2;
        g2d.drawImage(image,xImage,yImage, this);
        //g2d.drawImage(TestImage, x, y, this);
        g2d.setTransform(origXform);
        g.drawImage(TestImage, cordX, cordY, this);
        g2d.setTransform(origXform);
        g.drawImage(TestImage, cordX2, cordY2, this);
        for (int i = 0; i < rand.nextInt(100); i++) {
            int width_height = rand.nextInt(50);

            g.setColor(Color.CYAN);
            int xPosition = rand.nextInt(this.getWidth());
            int YPosition = rand.nextInt(this.getHeight());
            int xPositionC = rand.nextInt(this.getWidth());
            int YPositionC = rand.nextInt(this.getHeight());
            g.fillOval(xPositionC,YPositionC , width_height, width_height);
            g.fillRect(xPosition,YPosition, width_height, width_height);
            g.setColor(Color.orange);
            g.fillOval(xPositionC,YPositionC, 5,5);
        }
    }


    //    public static void main(String[] args) {
//        ri = new RotateImages(TestImage);
//
//    }
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        System.out.println("Before selecting the file you want to use in your ScreenSaver you need to resize the image you will use");
        System.out.println("Use this link: https://www.simpleimageresizer.com");
        System.out.println("Use a .jpeg image. If the image is a rectangle with width greater than height use dimensions 600x400 (this is flexible too based on scaling of image)");
        System.out.println("Do NOT make the image more than 600-650");
        ri = new RotateImages(TestImage);
        File file = new File("Elevator.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        while (true){
            clip.loop(10000000);
        }
    }
    //    public void fileChooser(){
//        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//
//        // allow multiple file selection
//        j.setMultiSelectionEnabled(true);
//
//        // invoke the showsSaveDialog function to show the save dialog
//        int r = j.showSaveDialog(null);
//
//        if (r == JFileChooser.APPROVE_OPTION) {
//            // get the Selected files
//            File files[] = j.getSelectedFiles();
//
//            int t = 0;
//            // set text to blank
//            l.setText("");
//
//            // set the label to the path of the selected files
//            while (t++ < files.length)
//                l.setText(l.getText() + " " + files[t - 1].getName());
//        }
//        // if the user cancelled the operation
//        else
//            l.setText("the user cancelled the operation");
//    }
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
        }
        repaint();
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }
}
