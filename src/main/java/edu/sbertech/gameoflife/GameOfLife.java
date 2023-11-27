package edu.sbertech.gameoflife;

public class GameOfLife {
    private final int rows;
    private final int cols;
    private static char[][] grid;

    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = generateOriginalGeneration();
    }

    private char[][] generateOriginalGeneration() {
        char[][] gameGrid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameGrid[i][j] = (char) Math.round(Math.random());
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameGrid[i][j] = gameGrid[i][j] == 1
                        ? CellState.ALIVE.getSymbol()
                        : CellState.DEAD.getSymbol();
            }
        }

        return gameGrid;
    }

    private boolean isCellInGrid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private static boolean isCellAlive(int row, int col) {
        return grid[row][col] == CellState.ALIVE.getSymbol();
    }

    private static boolean isCellDead(int row, int col) {
        return grid[row][col] == CellState.DEAD.getSymbol();
    }

    private boolean isGameEnded() {
        int deadCells = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isCellDead(i, j)) {
                    deadCells++;
                }
            }
        }
        return deadCells == rows * cols;
    }

    private int countAliveNeighbours(int row, int col) {
        int aliveNeighbours = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                int neighbourRow = row + i;
                int neighbourCol = col + j;

                if (isCellInGrid(neighbourRow, neighbourCol) && isCellAlive(neighbourRow, neighbourCol)) {
                    aliveNeighbours++;
                }
            }
        }
        return aliveNeighbours;
    }

    private void generateNextGeneration() {
        char[][] nextGenerationGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int aliveNeighbours = countAliveNeighbours(i, j);

                if (GameOfLifeRules.hasAliveCellNotEnoughNeighbors(i, j, aliveNeighbours)
                        || GameOfLifeRules.hasAliveCellTooManyNeighbors(i, j, aliveNeighbours)
                ) {
                    nextGenerationGrid[i][j] = CellState.DEAD.getSymbol();
                } else if (GameOfLifeRules.hasDeadCellEnoughNeighbors(i, j, aliveNeighbours)) {
                    nextGenerationGrid[i][j] = CellState.ALIVE.getSymbol();
                } else {
                    nextGenerationGrid[i][j] = grid[i][j];
                }
            }
        }

        grid = nextGenerationGrid;
    }

    public void printGameGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printNextCellGenerationGameGrids(int generationsNumber) {
        for (int i = 0; i < generationsNumber; i++) {
            System.out.printf("%d generation:\n", i + 1);
            this.generateNextGeneration();
            this.printGameGrid();

            if (isGameEnded()) {
                System.out.println("Unfortunately, life is over");
                break;
            }
        }
    }

    static class GameOfLifeRules {
        static boolean hasAliveCellNotEnoughNeighbors(int row, int col, int aliveNeighbours) {
            return isCellAlive(row, col) && aliveNeighbours < 2;
        }

        static boolean hasAliveCellTooManyNeighbors(int row, int col, int aliveNeighbours) {
            return isCellAlive(row, col) && aliveNeighbours > 3;
        }

        static boolean hasDeadCellEnoughNeighbors(int row, int col, int aliveNeighbours) {
            return isCellDead(row, col) && aliveNeighbours == 3;
        }
    }
}
