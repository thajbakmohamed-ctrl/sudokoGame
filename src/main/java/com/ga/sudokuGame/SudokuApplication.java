package com.ga.sudokuGame;

import com.ga.sudokuGame.exception.InvalidCharacterException;
import com.ga.sudokuGame.exception.SudokuFileNotFoundException;
import com.ga.sudokuGame.fileHandling.SudokuFileHandler;
import com.ga.sudokuGame.model.SudokuCell;
import com.ga.sudokuGame.solver.SudokuSolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ga.sudokuGame.model.sudoko;

@SpringBootApplication
public class SudokuApplication {

	public static void main(String[] args) {
		SpringApplication.run(SudokuApplication.class, args);
		// create a file handler object
		SudokuFileHandler fileHandler = new SudokuFileHandler();
		sudoko game = new sudoko();
		try {
			// Choose one Sudoku puzzle to solve
			// String filePath = "sudoku/src/main/resources/puzzle1.txt";
			// String filePath = "sudoku/src/main/resources/puzzle2.txt";
			//String filePath = "sudoku/src/main/resources/puzzle3.txt";
			 String filePath = "sudoku/src/main/resources/puzzle4.txt";

			int[][] board = fileHandler.readSudokuBoardFromFile(filePath);

			SudokuCell[][] cells = new SudokuCell[9][9];
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					cells[row][col] = new SudokuCell(row, col, board[row][col]);
				}
			}
			System.out.println("Sudoku Before Solving:");
			game.printSudokuBoard(board);

			SudokuSolver solver = new SudokuSolver();
			solver.solve(board);
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					cells[row][col].setValue(board[row][col]);
				}
			}
			System.out.println("Sudoku After Solving:");

			game.printSudokuBoard(board);

			String solutionFilePath = filePath.replace(".txt", ".solution.txt");
			fileHandler.saveSolvedSudokuToFile(board, solutionFilePath);

			System.out.println("Solution file: " + solutionFilePath);

		} catch (SudokuFileNotFoundException e) {
			System.out.println(e.getMessage());

		} catch (InvalidCharacterException e) {
			System.out.println(e.getMessage());
		}
	}
}