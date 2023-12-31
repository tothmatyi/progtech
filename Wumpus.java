package wumpus;
import java.io.*;
import java.util.*;

public class LabirintusJatek {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] labirintus = null;
        int N = 0;
        char hősKezdetiIrány = 'N';
        int hősSor = -1;
        int hősOszlop = -1;
        int wumpuszokSzama = 0;

        while (true) {
            System.out.println("Válassz egy lehetőséget:");
            System.out.println("1. Pályaszerkesztés");
            System.out.println("2. Fájlból beolvasás");
            System.out.println("3. Kilépés");
            int valasztas = scanner.nextInt();
            scanner.nextLine(); // Beolvasás után ürítjük a sort

            switch (valasztas) {
                case 1:
                    System.out.print("Adja meg a labirintus méretét (N): ");
                    N = scanner.nextInt();
                    scanner.nextLine();

                    labirintus = new String[N][N];

                    // Pálya inicializálása
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            if (i == 0 || i == N - 1 || j == 0 || j == N - 1) {
                                labirintus[i][j] = "F";
                            } else {
                                labirintus[i][j] = "_";
                            }
                        }
                    }

                    // Falakat hozzáadni
                    for (int i = 1; i < N - 1; i++) {
                        for (int j = 1; j < N - 1; j++) {
                            System.out.print("Add meg a(z) " + (char) ('a' + j - 1) + i + " pozíció elemét (F/H/U/P/G/_): ");
                            String elem = scanner.nextLine();
                            labirintus[i][j] = elem;

                            if (elem.equals("H")) {
                                hősSor = i;
                                hősOszlop = j;
                            } else if (elem.equals("U")) {
                                wumpuszokSzama++;
                            }
                        }
                    }

                    // Pálya kiírása
                    for (int i = 0; i < N; i++) {
                        for (int j = 0; j < N; j++) {
                            System.out.print(labirintus[i][j] + " ");
                        }
                        System.out.println();
                    }

                    System.out.println("Pályaszerkesztés befejezve.");
                    break;

                case 2:
                    try {
                        File file = new File("wumpusinput.txt");
                        Scanner fileScanner = new Scanner(file);

                        N = fileScanner.nextInt();
                        hősOszlop = (int) fileScanner.next().charAt(0) - 'a' + 1;
                        hősSor = fileScanner.nextInt();
                        hősKezdetiIrány = fileScanner.next().charAt(0);

                        labirintus = new String[N][N];

                        for (int i = 0; i < N; i++) {
                            String sor = fileScanner.next();
                            for (int j = 0; j < N; j++) {
                                labirintus[i][j] = sor.charAt(j) + "";
                                if (labirintus[i][j].equals("U")) {
                                    wumpuszokSzama++;
                                }
                            }
                        }

                        // Validáció
                        if (hősOszlop <= 0 || hősOszlop > N || hősSor <= 0 || hősSor > N) {
                            System.out.println("Hős kezdőpozíciója érvénytelen.");
                            continue;
                        }

                        if (wumpuszokSzama < 1 || (N >= 9 && wumpuszokSzama > 2) || (N >= 15 && wumpuszokSzama > 3)) {
                            System.out.println("Wumpuszok száma érvénytelen.");
                            continue;
                        }

                        System.out.println("Pálya beolvasva:");
                        for (int i = 0; i < N; i++) {
                            for (int j = 0; j < N; j++) {
                                System.out.print(labirintus[i][j] + " ");
                            }
                            System.out.println();
                        }

                    } catch (FileNotFoundException e) {
                        System.out.println("Hiba a fájl olvasása közben.");
                    }
                    break;

                case 3:
                    System.out.println("Kilépés a programból.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Érvénytelen választás.");
                    break;
            }
        }
    }
}
