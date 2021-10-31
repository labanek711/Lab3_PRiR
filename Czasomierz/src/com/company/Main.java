package com.company;

class Czasomierz extends Thread {
    int g,m,s;
    public Czasomierz(int godziny, int minuty, int sekundy) {
        this.g = godziny;
        this.m = minuty;
        this.s = sekundy;
    }

    public void run() {
        while(true) {
            System.out.println(this.g + "-" + this.m + "-" + this.s);
            this.s++;
            try {
                Thread.sleep(1000);
            }catch(Exception e) {

            }
            if(this.s == 60) {
                this.s = 0;
                this.m++;
            }
            if(this.m == 60) {
                this.m = 0;
                this.g++;
            }
            if(this.g == 24)
                this.g = 0;
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Czasomierz zegarek = new Czasomierz(21, 1, 5);
        zegarek.start();
    }
}