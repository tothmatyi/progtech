import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class WumpusGame {
    private char[][] board;
    private int size;
    private int numArrows;
    private int heroRow;
    private int heroCol;
    private char heroDirection;

    private Timer timer;


    private int numWumpus;

    public WumpusGame(int size, int numWumpus) {
        this.size = size;
        this.numWumpus = numWumpus;
        this.numArrows = numWumpus;
        this.board = new char[size][size];
        this.timer = new Timer();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    board[i][j] = 'W'; // Fal az első és utolsó sorban és oszlopban
                } else {
                    board[i][j] = '_'; // Üres mező
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void addElement(int row, int col, char element) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = element;
        }
    }

    public void removeElement(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = '_';
        }
    }

    public void loadFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));

            size = scanner.nextInt();
            heroCol = scanner.next().charAt(0) - 'a' + 1;
            heroRow = scanner.nextInt();
            heroDirection = scanner.next().charAt(0);

            numWumpus = (size <= 8) ? 1 : (size <= 14) ? 2 : 3;
            numArrows = numWumpus;

            initializeBoard();

            for (int i = 1; i <= size; i++) {
                String rowString = scanner.next();
                for (int j = 1; j <= size; j++) {
                    char element = rowString.charAt(j - 1);
                    if (element != '_') {
                        addElement(i, j, element);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        }
    }

    public static void main(String[] args) {
        WumpusGame game = new WumpusGame(10, 1); // Példa: 10x10-es pálya, 1 Wumpus
        game.printBoard();

        // Pálya elemeinek módosítása
        game.addElement(2, 2, 'H'); // Hős hozzáadása
        game.addElement(3, 3, 'U'); // Wumpus hozzáadása
        game.addElement(4, 4, 'G'); // Arany hozzáadása
        game.addElement(5, 5, 'P'); // Verem hozzáadása

        System.out.println("\nAfter modifications:");
        game.printBoard();

        // Pálya betöltése fájlból
        game.loadFromFile("wumpluszinput.txt");

        System.out.println("\nAfter loading from file:");
        game.printBoard();
    }

    public void createBoard() {
        // A pálya elemeinek létrehozása a szabályok alapján

        // Hős elhelyezése
        addElement(heroRow, heroCol, 'H');

        // Wumpusok elhelyezése
        for (int i = 0; i < numWumpus; i++) {
            int wumpusRow = (i + 1) * (size - 2) / (numWumpus + 1) + 1;
            int wumpusCol = (i + 1) * (size - 2) / (numWumpus + 1) + 1;
            addElement(wumpusRow, wumpusCol, 'U');
        }

        // Arany elhelyezése
        addElement((size + 1) / 2, (size + 1) / 2, 'G');

        // Veremek elhelyezése
        for (int i = 0; i < 3; i++) {
            int pitRow, pitCol;
            do {
                pitRow = (int) (Math.random() * (size - 2)) + 1;
                pitCol = (int) (Math.random() * (size - 2)) + 1;
            } while (board[pitRow][pitCol] != '_');
            addElement(pitRow, pitCol, 'P');
        }
    }

    // ... (a többi kód)
    public void startGame() {
        createBoard(); // Pálya elemeinek létrehozása

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // A játék állapotának frissítése
                // (itt lehet implementálni a játék logikáját, például a hős mozgását vagy egyéb eseményeket)
                printBoard(); // A játéktábla kiíratása
            }
        }, 0, 1000); // A frissítési időköz: 1000 ms (1 másodperc)
    }

    public void stopGame() {
        timer.cancel();
        System.out.println("Game stopped.");
    }
}
