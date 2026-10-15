
package com.ga.sudokuGame.solver;

import java.util.ArrayList;

public class SudokuSolver {

    public void solve(int[][] board) {
        ArrayList<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] == 0) {
                    int[] position = {row, col};
                    emptyCells.add(position);
                }
            }
        }

        // Start solving from the first empty cell
        solveRecursive(board, emptyCells, 0);
    }

    public boolean isValid(int[][] board, int row, int col, int num) {

        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // find the starting position of the 3x3 box
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean solveRecursive(int[][] board, ArrayList<int[]> emptyCells, int index) {
        //
        if (index == emptyCells.size()) {
            return true;
        }
        // know the postion
        int[] position = emptyCells.get(index);
        int row = position[0];
        int col = position[1];
        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;


                if (solveRecursive(board, emptyCells, index + 1)) {
                    return true;

                }
                board[row][col] = 0;
            }

        }
        return false;

    }
}
