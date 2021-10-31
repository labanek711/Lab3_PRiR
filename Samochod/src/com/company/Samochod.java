package com.company;

import java.util.concurrent.atomic.AtomicBoolean;

//Uzupełnij powyższy szkielet klasy
public class Samochod extends Thread {

    private String nrRej;
    private int pojZbiornika;
    private int paliwo;

//    private Thread car;
    private final AtomicBoolean czyDziala = new AtomicBoolean();


    public Samochod(String nr, int _pojZbiornika, int paliwo) {
        this.nrRej = nr;
        this.pojZbiornika = _pojZbiornika;
        this.paliwo = paliwo;
    }

    public void tankowanie(int _paliwo) {
        paliwo = 100;
        System.out.println("zatankowałem do pełna");

        //czyDziala.set(true);
    }

    public void start() { //start samochodu, uruchamiamy wątek zużycia paliwa
        super.start();
    }

    public final void stopp() { //zatrzymanie samochodu, zatrzymujemy wątek zużycia paliwa
        System.out.println("ZATRZYMUJE POJAZD");
        czyDziala.set(false);
        tankowanie(paliwo);
    }

    public void uruchom() throws InterruptedException {
        czyDziala.set(true);
        int iter = 0;

        while (czyDziala.get()) {

            Thread.sleep(1500);

            System.out.println("Jedziemy samochodem " + nrRej + " i mamy " + paliwo + "paliwa");
            paliwo -= 1;
            iter++;
            if (paliwo == 20) {
                System.out.println("potrzeba zatankowac");
            }
            if (paliwo == 0) {
                stopp();
            }


        }
    }

    @Override
    public void run() { //kod, który wykonuje się w odrębnym wątku, co 1 s programu zużywany jest 1 litr paliwa
        System.out.println("jestem samochodem"+nrRej);

        try {
            uruchom();
        } catch (InterruptedException e) {
            stopp();
            e.printStackTrace();
        }
    }

// symulacja działania klasy Samochod dla 1,2,3, … samochodów
}

class TestSamochod{

    public static void main(String[] args)  {
        var citroen = new Samochod("111",100,100);
        var bmw = new Samochod("222",100,40);
        var audi = new Samochod("333",300,200);

        citroen.start();
        bmw.start();
        audi.start();

    }
}
