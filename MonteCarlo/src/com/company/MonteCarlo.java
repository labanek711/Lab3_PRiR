package com.company;

import java.util.Random;

public class MonteCarlo extends Thread {
    double xStart, yStart, xStop, yStop;
    int liczbaStrzalow;
    double wynik;
    Random r;

    public MonteCarlo(double xStart, double yStart, double xStop, double yStop, int liczbaStrzalow) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xStop = xStop;
        this.yStop = yStop;
        this.wynik = 0;
        this.liczbaStrzalow = liczbaStrzalow;
        this.r = new Random();
    }

    public void run() {
        int wKole = 0;

        for (int i = 0; i < this.liczbaStrzalow; i++) {
//            double x = (this.xStop - this.xStart) * r.nextDouble() + this.xStart;
//            double y = (this.yStop - this.yStart) * r.nextDouble() + this.yStart;
            double x = Math.random();
            double y = Math.random();

            if ((x * x + y * y) <= 1)
                wKole++;
        }

        this.wynik = wKole;
    }

    public double getWynik() {
        return this.wynik;
    }

}

public class Main {

    public static void main(String[] args) {
        // write your code here
        MonteCarlo m1, m2, m3, m4;
        int liczbaStrzalow = 1000;
        double a = 10;

        m1 = new MonteCarlo(0,0, a/2, a/2, liczbaStrzalow);
        m2 = new MonteCarlo(a/2,0, 1, a/2, liczbaStrzalow);
        m3 = new MonteCarlo(0, a/2, a/2, a, liczbaStrzalow);
        m4 = new MonteCarlo(a/2,a/2, a, a, liczbaStrzalow);

        m1.run();
        m2.run();
        m3.run();
        m4.run();

        try {
            m1.join();
            m2.join();
            m3.join();
            m4.join();
        }catch (Exception e){

        }
        double pole = m1.getWynik() + m2.getWynik() + m3.getWynik() + m4.getWynik();
        pole = pole / ((double)liczbaStrzalow * 4) * (a * a);
        System.out.println("Pole kola = " + pole);
    }
}
