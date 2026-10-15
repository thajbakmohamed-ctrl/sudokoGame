package com.ga.sudokuGame.fileHandling;

import com.ga.sudokuGame.exception.InvalidCharacterException;
import com.ga.sudokuGame.exception.SudokuFileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SudokuFileHandler {
    public int[][] readSudokuBoardFromFile(String filePath)
            throws SudokuFileNotFoundException, InvalidCharacterException {
        int[][] board = new int[9][9];

        try {
            Scanner scanner = new Scanner(new File(filePath));

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    // check if the next value is an integer
                    if (!scanner.hasNextInt()) {
                        throw new InvalidCharacterException("Invalid character in Sudoku file");
                    }
                    board[row][col] = scanner.nextInt();
                    // make sure if the number is not less than 0 and not bigger than 9
                    if (board[row][col] < 0 || board[row][col] > 9) {
                        throw new InvalidCharacterException(
                                "Invalid Sudoku number: " + board[row][col]
                        );
                    }
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            throw new SudokuFileNotFoundException("Sudoku file not found: " + filePath);
        }

        return board;
    }

    public void saveSolvedSudokuToFile(int[][] board, String filePath) {

        try {
            PrintWriter writer = new PrintWriter(new File(filePath));

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {

                    writer.print(board[row][col] + " ");
                }
                writer.println();

            }
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not save Sudoku file");
        }
    }
    public int[][] readUploadedSudokuFile(MultipartFile file)
            throws InvalidCharacterException, IOException {

        int[][] board = new int[9][9];

        // read the uploaded file content
        try (Scanner scanner = new Scanner(file.getInputStream())) {

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {

                    if (!scanner.hasNextInt()) {
                        throw new InvalidCharacterException("Invalid character or missing number in Sudoku file");
                    }

                    board[row][col] = scanner.nextInt();

                    // check if the number is between 0 and 9
                    if (board[row][col] < 0 || board[row][col] > 9) {
                        throw new InvalidCharacterException("Invalid Sudoku number: " + board[row][col]);
                    }
                }
            }

            // check that the file has exactly 81 numbers
            if (scanner.hasNext()) {
                throw new InvalidCharacterException("Sudoku file contains extra values");
            }
        }

        return board;
    }

}