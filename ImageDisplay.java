import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.*;


public class ImageDisplay {

    private JFrame frame;
    private JLabel lbIm1;
    private JLabel lbIm2;
    private BufferedImage image;
    private InputStream inputStream;
    public static int RGB_FACTOR = 3;

    private int width = 960;
    private int height = 540;

    private int width_o = width;
    private int height_o = height;

    double factor_w = 1;
    double factor_h = 1;

    public void showIms(String[] args) {
        // Input arguments from command line .
        factor_w = Double.parseDouble(args[1]);
        factor_h = Double.parseDouble(args[2]);

        width = (int) (width * factor_w);
        height = (int) (height * factor_h);


        long fps = Long.parseLong(args[3]);

        frame = new JFrame();
        GridBagLayout gLayout = new GridBagLayout();
        frame.getContentPane().setLayout(gLayout);

        JLabel lbText1 = new JLabel("Video height" + height + " width" + width);

        lbText1.setHorizontalAlignment(SwingConstants.CENTER);
        ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        frame.getContentPane().add(lbText1, gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        // Taking input parameters for display.
        try {
            File file = new File(args[0]);
            inputStream = new FileInputStream(file);

            long len = file.length();
            byte[] bytes = new byte[(int) len];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            int count = 0;
            while (count == 0) {
                count++;
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.out.println();
                int numberOfFrames = (int) (len / (RGB_FACTOR * width * width));

                for (int index = 0; index < (numberOfFrames); index++) {
                    long lStartTime = System.currentTimeMillis();

                    int ind = (int) ((RGB_FACTOR * width_o * height_o * index));
                    for (int yAxis = 0; yAxis < height; yAxis++) {
                        for (int xAxis = 0; xAxis < width; xAxis++) {

                            byte red = bytes[ind];
                            //Shift on ce length to get green array
                            byte green = bytes[ind + height_o * width_o];
                            //Shift twice length to get blue array
                            byte blue = bytes[ind + height_o * width_o * 2];

                            int pixValue = 0xff000000 | ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
                            image.setRGB(xAxis, yAxis, pixValue);
                            ind = ind + 2;

                        }
                    }

                    ImageIcon image = new ImageIcon(this.image);
                    images.add(image);
                    long lEndTime = System.currentTimeMillis();
                    displayFrame(image, gridBagConstraints);

                    try {
                        long delay = (long) ((1000.0 / fps) - (lEndTime - lStartTime));
                        Thread.sleep((delay > 0) ? delay : 0);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Display per frame.
    private void displayFrame(ImageIcon image, GridBagConstraints c) {
        lbIm1 = new JLabel(image);
        frame.getContentPane().add(lbIm1, c);
        frame.pack();
        frame.setVisible(true);
    }

    // Main function.
    public static void main(String[] args) {
        ImageDisplay ren = new ImageDisplay();
        ren.showIms(args);
    }

}
