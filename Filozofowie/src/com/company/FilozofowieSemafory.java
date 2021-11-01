package com.company;

import java.util.concurrent.Semaphore;

public class FilozofowieSemafory implements Filozofowie {
    private int iloscFilozofow;

    FilozofowieSemafory(int ilosc) {
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

class FilozofSemafora extends Thread {
    public static Semaphore[] widelce;
    private int mojNum;

    public FilozofSemafora(int nr) {
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
        widelce[widelecLewy].acquireUninterruptibly();
        widelce[widelecPrawy].acquireUninterruptibly();
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
