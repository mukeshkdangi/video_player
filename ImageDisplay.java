import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.*;


public class ImageDisplay {

    private JFrame frame;
    private JLabel lbIm1;
    private JLabel lbIm2;
    private BufferedImage img;
    private InputStream is;
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

        // Use labels to display the images
        frame = new JFrame();
        GridBagLayout gLayout = new GridBagLayout();
        frame.getContentPane().setLayout(gLayout);
        JLabel lbText1 = new JLabel("Video height" + height + " width" + width);
        lbText1.setHorizontalAlignment(SwingConstants.CENTER);

        //Creation of array list for obtaining pixel values.
        ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();

        //Generation of the GUI for display.
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
            is = new FileInputStream(file);

            long len = file.length();
            byte[] bytes = new byte[(int) len];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            // Processing code begins here.
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            int count = 0;
            while (count == 0) {
                count++;
                System.out.println();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                for (int i = 0; i < (len / (3.0 * width * width)); i++ ) {

                    long lStartTime = System.currentTimeMillis();

                    int ind = (int) ((3 * width * height * i));
                    for (int y = 0; y < height; y++) {

                        for (int x = 0; x < width; x++) {
                            byte r = bytes[ind];
                            byte g = bytes[ind + height_o * width_o];
                            byte b = bytes[ind + height_o * width_o * 2];

                            int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                            img.setRGB(x, y, pix);// set the value to the location.
                            ind++;

                        }
                    }
                    ImageIcon image = new ImageIcon(img);
                    images.add(image);// Addition of pixel values to the frames created.
                    long lEndTime = System.currentTimeMillis();// Calculation of the amount of time taken for processing inside for loop.
                    long output = lEndTime - lStartTime;
                    displayFrame(image, gridBagConstraints);// Display per frame.

                    try {
                        long delay = (long) ((1000.0 / fps) - output);
                        Thread.sleep((delay > 0) ? delay : 0);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
