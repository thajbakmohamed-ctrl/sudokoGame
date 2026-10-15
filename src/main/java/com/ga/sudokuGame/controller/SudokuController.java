package com.ga.sudokuGame.controller;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.ga.sudokuGame.exception.InvalidCharacterException;
import com.ga.sudokuGame.exception.SudokuFileNotFoundException;
import com.ga.sudokuGame.fileHandling.SudokuFileHandler;
import com.ga.sudokuGame.model.sudoko;
import com.ga.sudokuGame.solver.SudokuSolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/sudoku")

public class SudokuController {


    @PostMapping("/solve")
    public String solveSudoku(@RequestParam("file") MultipartFile file) {


        //create a file handler
        SudokuFileHandler fileHandler = new SudokuFileHandler();
        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.endsWith(".txt")) {
            return "Invalid file name , Please upload a .txt file.";
        }

        try {
            int[][] board = fileHandler.readUploadedSudokuFile(file);
            SudokuSolver solver = new SudokuSolver();
            solver.solve(board);
            String solutionFileName = fileName.replace(".txt", ".solution.txt");
            String solutionFilePath = "sudoku/src/main/resources/" + solutionFileName;
            fileHandler.saveSolvedSudokuToFile(board, solutionFilePath);
            // create a Sudoku object to format the solved board
            sudoko game = new sudoko();

            // convert the solved board into an ASCII grid
            String formattedBoard = game.formatSudokuBoard(board);

            return "Sudoku is solved successfully!\n\n" + formattedBoard;

        } catch (InvalidCharacterException e) {
            return e.getMessage();
        }
        catch (IOException e) {
            return "Error reading uploaded file: " + e.getMessage();
        }
    }


}
