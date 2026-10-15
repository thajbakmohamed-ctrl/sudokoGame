
package com.ga.sudokuGame.model;

public class sudoko {

    int[][] board = new int[9][9];

    public void printSudokuBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                System.out.print(board[row][col] + " ");
                if ((col + 1) % 3 == 0 && col != 8) {
                    System.out.print("| ");
                }
            }

            System.out.println();
            if ((row + 1) % 3 == 0 && row != 8) {
                System.out.println("-----------------------");
            }
        }
    }

    // format the sudoku board as a text
    public String formatSudokuBoard(int[][] board) {

        StringBuilder formattedBoard = new StringBuilder();
        formattedBoard.append("+-------+-------+-------+\n");

        for (int row = 0; row < 9; row++) {

            formattedBoard.append("| ");

            for (int col = 0; col < 9; col++) {

                formattedBoard.append(board[row][col]).append(" ");

                // add a separator after every 3 columns
                if ((col + 1) % 3 == 0) {
                    formattedBoard.append("| ");
                }
            }
            formattedBoard.append("\n");
            if ((row + 1) % 3 == 0) {
                formattedBoard.append("+-------+-------+-------+\n");
            }
        }

        return formattedBoard.toString();
    }

    public int[][] getBoard() {
        return board;
    }
}
