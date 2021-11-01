package com.company;


import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rodzaj;
        do {
            wyczyscKonsole();
            System.out.println("Wybierz problem:");
            System.out.println("1. Wykorzystanie Semaforów.");
            System.out.println("2. Niesymetryczne sięganie po widelec.");
            System.out.println("3. Wykorzystanie rzutu monetą.");
            rodzaj = scanner.nextInt();
        }while(rodzaj < 1 || rodzaj > 3);

        int ilosc;
        do{
            wyczyscKonsole();
            System.out.println("Ilość filozofów od 2 do 100:");
            ilosc = scanner.nextInt();
        }while(ilosc <2 || ilosc > 100);

        scanner.close();

        Filozofowie filozof;
        if (rodzaj == 1)
            filozof = new FilozofowieSemafory(ilosc);
        else if (rodzaj == 2)
            filozof = new FilozofowieNiesymetrycznie(ilosc);
        else if (rodzaj == 3)
            filozof = new FilozofowieRzut(ilosc);
        else {
            filozof = new FilozofowieSemafory(2);
            System.exit(1);
        }

        filozof.uruchomProblem();
    }

    private static void wyczyscKonsole()
    {
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}