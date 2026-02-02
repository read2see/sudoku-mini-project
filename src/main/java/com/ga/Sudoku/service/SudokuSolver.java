package com.ga.sudoku.service;

import com.ga.sudoku.model.SudokuBoard;
import com.ga.sudoku.model.SudokuCell;
import org.springframework.stereotype.Component;

@Component
public class SudokuSolver {

    private static final int SIZE = 9;
    private static final int BOX_SIZE = 3;

    public boolean solve(SudokuBoard board) {
        SudokuCell[][] grid = board.getGrid();
        if (grid == null || grid.length != SIZE) {
            return false;
        }
        boolean solved = solveGrid(grid);
        if (solved) {
            board.setSolved(true);
        }
        return solved;
    }

    private boolean solveGrid(SudokuCell[][] grid) {
        int[] empty = findEmpty(grid);
        if (empty == null) {
            return true;
        }
        int row = empty[0];
        int col = empty[1];
        for (int num = 1; num <= SIZE; num++) {
            if (isValid(grid, row, col, num)) {
                grid[row][col].setValue(num);
                if (solveGrid(grid)) {
                    return true;
                }
                grid[row][col].setValue(0);
            }
        }
        return false;
    }

    private int[] findEmpty(SudokuCell[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col].isEmpty()) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private boolean isValid(SudokuCell[][] grid, int row, int col, int num) {
        for (int c = 0; c < SIZE; c++) {
            if (grid[row][c].getValue() == num) {
                return false;
            }
        }
        for (int r = 0; r < SIZE; r++) {
            if (grid[r][col].getValue() == num) {
                return false;
            }
        }
        int boxRow = (row / BOX_SIZE) * BOX_SIZE;
        int boxCol = (col / BOX_SIZE) * BOX_SIZE;
        for (int r = boxRow; r < boxRow + BOX_SIZE; r++) {
            for (int c = boxCol; c < boxCol + BOX_SIZE; c++) {
                if (grid[r][c].getValue() == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
