package com.example.chinesechess;

/**
 * Represents the game board.
 */
public class Board {

    //The length and width of the board.
    private int length, width;

    //Storage for game pieces.
    private Object[][] board;

    public Board(int length, int width)
    {
        this.length = length;
        this.width = width;
        board = new Object[length][width];
    }
}
