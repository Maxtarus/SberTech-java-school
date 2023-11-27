package edu.sbertech.gameoflife;

import java.util.Scanner;

public class GameOfLifeApplication {

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input the number of rows of the game grid: ");
            int rows = scanner.nextInt();
            System.out.print("Input the number of columns of the game grid: ");
            int cols = scanner.nextInt();
            var game = new GameOfLife(rows, cols);
            System.out.println("\nOriginal generation:");
            game.printGameGrid();
            System.out.print("Input the number of generations: ");
            int generationsNumber = scanner.nextInt();
            System.out.println();
            game.printNextCellGenerationGameGrids(generationsNumber);
        }
    }
}