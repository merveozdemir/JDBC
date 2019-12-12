package com.uniyaz;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DBConnection dbConn = new DBConnection();
        if (!dbConn.IsConnected()) {
            System.out.println("Veritabanına bağlantı sağlanamadı");
        } else {
            boolean programCalisiyor = true;
            while (programCalisiyor) {
                System.out.println("1. Hero Ekle");
                System.out.println("2. Movie Ekle");
                System.out.println("3. Hero toplam bütçe hesapla");
                System.out.println("4. Hero film sayısı hesapla");
                System.out.println("5. Hero listele");
                System.out.println("6. Movie listele");
                System.out.println("0. Çıkış");

                Scanner scanner = new Scanner(System.in);
                int secim = scanner.nextInt();
                scanner.nextLine();
                switch (secim){
                    case 1:
                        System.out.println("Name");
                        String ad = scanner.nextLine();

                        System.out.println("Surname");
                        String soyad = scanner.nextLine();

                        Hero hero = new Hero(ad,soyad);
                        dbConn.heroEkle(hero);
                        break;
                    case 2:
                        dbConn.heroListele();
                        System.out.println("Hero id giriniz: ");
                        int heroId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Movie Name");
                        String movieName = scanner.nextLine();

                        System.out.println("Budget");
                        BigDecimal budget = scanner.nextBigDecimal();

                        Movie movie = new Movie(movieName,budget);
                        dbConn.movieEkle(heroId, movie);
                        break;
                    case 3:
                        dbConn.heroToplamButceHesapla();
                        break;
                    case 4:
                        dbConn.heroFilmSayısıHesapla();
                        break;
                    case 5:
                        dbConn.heroListele();
                        break;
                    case 6:
                        dbConn.movieListele();
                        break;
                    case 0:
                        programCalisiyor = false;
                        System.out.println("Çıkış...");
                        break;
                }



            }
        }
    }
}
