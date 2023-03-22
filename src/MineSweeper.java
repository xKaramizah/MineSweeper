import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    Random rnd = new Random();
    Scanner input = new Scanner(System.in);
    int rowNo;
    int colNo;
    int numberOfMine;
    int difficulty;
    int winCounter;
    int rowCoord, colCoord;
    boolean isBlast = false;
    boolean isRightGuess = true;
    int[] luckyNumbers;
    String[][] mineMap;
    String[][] gameMap;

    MineSweeper(int rowNo, int colNo, String difficulty) {
        this.rowNo = rowNo;
        this.colNo = colNo;
        switch (difficulty) {
            case "easy" -> this.difficulty = 4;
            case "medium" -> this.difficulty = 3;
            case "hard" -> this.difficulty = 2;
            default -> this.difficulty = 3;
        }
        this.mineMap = new String[rowNo][colNo];
        this.gameMap = new String[rowNo][colNo];
        this.numberOfMine = this.colNo * this.rowNo / (this.difficulty);
        this.luckyNumbers = new int[numberOfMine];
        this.winCounter = (this.rowNo * this.colNo) - numberOfMine;
        fill(this.mineMap);
        fill(this.gameMap);
    }

    void run() {
        setupMap();
        printMineMap();
        System.out.println("Mayın tarlası oyununa hoş geldiniz.\n" + "Oyun başlıyor..\n");
        printGame();
        while (this.winCounter != 0) {
            if (this.isBlast) {
                this.gameMap[this.rowCoord][this.colCoord] = "X";
                printGame();
                printBlast();
                break;
            }
            guessCoordinate();
        }
    }

    void setupMap() {
        for (int i = 0; i < this.numberOfMine; i++) {
            this.luckyNumbers[i] = rnd.nextInt(this.rowNo * this.colNo) + 1;
            for (int j = 0; j < i; j++) {
                if (this.luckyNumbers[i] == this.luckyNumbers[j]) {
                    this.luckyNumbers[i] = rnd.nextInt(this.rowNo * this.colNo) + 1;
                }
            }
        }
        for (int i = 0; i < this.numberOfMine; i++) {
            int count = this.luckyNumbers[i];
            for (int j = 0; j < this.rowNo; j++) {
                for (int k = 0; k < this.colNo; k++) {
                    count--;
                    if (count == 0) {
                        this.mineMap[j][k] = "*";
                    }
                }
            }
        }
    }

    void guessCoordinate() {
        do {
            System.out.print("Satır no giriniz: ");
            this.rowCoord = input.nextInt() - 1;
            System.out.print("Sütun no giriniz: ");
            this.colCoord = input.nextInt() - 1;
            if (colCoord < 0 || colCoord > colNo || rowCoord < 0 || rowCoord > rowNo) {
                isRightGuess = false;
                System.out.println("Sınırlar dışında giriş yaptınız. Tekrar deneyiniz!");
            } else isRightGuess = true;

        } while (!isRightGuess);

        if (this.mineMap[rowCoord][colCoord].equals("*")) {
            isBlast = true;
        } else {
            this.gameMap[rowCoord][colCoord] = String.valueOf(countMines(rowCoord, colCoord));
            winCounter--;
            if (winCounter == 0) {
                System.out.println("TEBRİKLER!! OYUNU KAZANDINIZ...\n");
                for (int i = 0; i < this.rowNo; i++) {
                    for (int j = 0; j < this.colNo; j++) {
                        if (this.gameMap[i][j].equals("-")) {
                            this.gameMap[i][j] = "*";
                        }
                    }
                }
            }
            printGame();
        }
    }

    void fill(String[][] arr) {
        for (int i = 0; i < this.rowNo; i++) {
            for (int j = 0; j < this.colNo; j++) {
                arr[i][j] = "-";
            }
        }
    }

    int countMines(int row, int column) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < this.rowNo && j >= 0 && j < this.colNo) {
                    if (this.mineMap[i][j].equals("*")) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    void printMineMap() {
        System.out.println("Mayın Haritası: ");
        for (int i = 0; i < rowNo; i++) {
            for (int j = 0; j < colNo; j++) {
                System.out.print(" " + this.mineMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("===================");
    }

    void printGame() {
        System.out.print("BOARD: \n" + "   ");
        for (int j = 1; j <= colNo; j++) {
            System.out.print("  " + j);
        }
        System.out.println();
        for (int i = 0; i < colNo; i++) {
            System.out.print("----");
        }
        System.out.println();

        for (int i = 0; i < rowNo; i++) {
            System.out.print((i + 1) + " |");
            for (int k = 0; k < colNo; k++) {
                System.out.print("  " + this.gameMap[i][k]);
            }
            System.out.println("  |");
        }
        for (int i = 0; i < colNo; i++) {
            System.out.print("----");
        }
        System.out.println();
    }

    void printBlast() {
        System.out.println("GAME OVER!");
    }
}
