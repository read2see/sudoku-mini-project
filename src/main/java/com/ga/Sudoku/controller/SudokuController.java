package com.ga.sudoku.controller;

import com.ga.sudoku.model.SudokuBoard;
import com.ga.sudoku.service.SudokuService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SudokuController {
    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping("/boards/{boardName}")
    public SudokuBoard getBoard(@PathVariable String boardName) {
        return sudokuService.loadPuzzle(boardName);
    }

    @GetMapping(value = "/display-board/{boardName}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String displayBoard(@PathVariable String boardName) {
        SudokuBoard board = sudokuService.loadPuzzle(boardName);
        return board.displayBoard();
    }

    @GetMapping(value = "/boards/{boardName}/solve", produces =  MediaType.TEXT_PLAIN_VALUE)
    public String solveBoard(@PathVariable String boardName) {
        SudokuBoard board =  sudokuService.solvePuzzle(boardName);
        return board.displayBoard();
    }
}
