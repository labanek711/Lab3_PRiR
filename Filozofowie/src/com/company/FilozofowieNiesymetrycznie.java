package com.company;

import java.util.concurrent.Semaphore;

public class FilozofowieNiesymetrycznie implements Filozofowie {
    private int iloscFilozofow;

    FilozofowieNiesymetrycznie(int ilosc) {
        iloscFilozofow = ilosc;
    }

    @Override
    public void uruchomProblem() {
        FilozofNiesymetryczny.usawIlosWidelcow(iloscFilozofow);

        for (int i = 0; i < iloscFilozofow; i++) {
            new FilozofNiesymetryczny(i).start();
        }
    }
}

class FilozofNiesymetryczny extends Thread {
    public static Semaphore[] widelce;
    private int mojNum;

    public FilozofNiesymetryczny(int nr) {
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
        if (mojNum == 0) {
            widelce[widelecPrawy].acquireUninterruptibly();
            widelce[widelecLewy].acquireUninterruptibly();
        } else {
            widelce[widelecLewy].acquireUninterruptibly();
            widelce[widelecPrawy].acquireUninterruptibly();
        }
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