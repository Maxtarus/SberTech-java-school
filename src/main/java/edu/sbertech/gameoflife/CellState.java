package edu.sbertech.gameoflife;

public enum CellState {
    ALIVE('*'), DEAD('·');

    private final char symbol;

    CellState(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
