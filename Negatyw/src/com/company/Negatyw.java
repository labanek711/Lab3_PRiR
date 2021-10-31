package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Negatyw extends Thread{
    BufferedImage obraz;
    int xStart, yStart, xStop, yStop;

    public Negatyw(BufferedImage obraz, int xStart, int yStart, int xStop, int yStop){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xStop = xStop;
        this.yStop = yStop;
        this.obraz = obraz;
    }

    @Override
    public void run() {
        for(int i = xStart; i < xStop; i++){
            for(int j = yStart; j < yStop; j++) {
                Color c = new Color(obraz.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int black = c.getBlue();
                int R, G, B;

                R = 255 - red;
                G = 255 - green;
                B = 255 - black;

                Color newColor = new Color(R, G, B);
                obraz.setRGB(i, j, newColor.getRGB());
            }
        }
    }

}

class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage image;

        File input = new File("tibia.jpg");
        image = ImageIO.read(input);
        int szerokosc = image.getWidth();
        int wysokosc = image.getHeight();
        int polSzerokosci = szerokosc / 2;
        int polWysokosci = wysokosc / 2;

        Negatyw neg1, neg2, neg3, neg4;
        neg1 = new Negatyw(image, 0, 0, polSzerokosci, polWysokosci);
        neg2 = new Negatyw(image, polSzerokosci, 0, szerokosc, polWysokosci);
        neg3 = new Negatyw(image, 0, polWysokosci, polSzerokosci, wysokosc);
        neg4 = new Negatyw(image, polSzerokosci, polWysokosci, szerokosc, wysokosc);

        neg1.start();
        neg2.start();
        neg3.start();
        neg4.start();

        try {
            neg1.join();
            neg2.join();
            neg3.join();
            neg4.join();
        } catch (Exception e) { }
        File ouptut = new File("tibiaNegatyw.jpg");
        ImageIO.write(image, "jpg", ouptut);
    }
}