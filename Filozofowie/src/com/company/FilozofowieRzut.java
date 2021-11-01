package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class FilozofowieRzut implements Filozofowie {
    private int iloscFilozofow;

    FilozofowieRzut(int ilosc) {
        iloscFilozofow = ilosc;
    }

    @Override
    public void uruchomProblem() {
        FilozofSemafora.usawIlosWidelcow(iloscFilozofow);

        for (int i = 0; i < iloscFilozofow; i++) {
            new FilozofSemafora(i).start();
        }
    }
}

class FilozofRzut extends Thread {
    public static Semaphore[] widelce;
    private int mojNum;
    private Random losuj = new Random();

    public FilozofRzut(int nr) {
        mojNum = nr;
    }

    public static void usawIlosWidelcow(int ilosc) {
        widelce = new Semaphore[ilosc];
        for (int i = 0; i < widelce.length; i++)
            widelce[i] = new Semaphore(1);
    }

    public void run() {
        while (true) {
            System.out.println("Myslę: " + mojNum);
            czekaj();

            podniesWidelce();
            System.out.println("Zaczynam jeść: " + mojNum);
            czekaj();

            System.out.println("Konczę jeść: " + mojNum);
            odlozWidelce();
        }
    }

    private void podniesWidelce() {
        int widelecLewy = mojNum;
        int widelecPrawy = (mojNum + 1) % widelce.length;

        boolean najpierwLewy = losuj.nextBoolean();
        boolean podnioslDwaWidelce = false;
        do {
            if (najpierwLewy)
                podnioslDwaWidelce = podniesNajpierwPotem(widelecLewy, widelecPrawy);
            else
                podnioslDwaWidelce = podniesNajpierwPotem(widelecPrawy, widelecLewy);
        } while (!podnioslDwaWidelce);
    }

    private boolean podniesNajpierwPotem(int najpierw, int potem) {
        widelce[najpierw].acquireUninterruptibly();

        if (!widelce[potem].tryAcquire()) {
            widelce[najpierw].release();
        } else {
            return true;
        }

        return false;
    }

    private void odlozWidelce() {
        int widelecLewy = mojNum;
        int widelecPrawy = (mojNum + 1) % widelce.length;
        widelce[widelecLewy].release();
        widelce[widelecPrawy].release();
    }

    private void czekaj() {
        try {
            Thread.sleep((long) (5000 * Math.random()));
        } catch (InterruptedException e) {
        }
    }
}