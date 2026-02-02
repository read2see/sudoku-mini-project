package com.ga.sudoku.dto;

import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.model.SudokuBoard;
import com.ga.sudoku.model.SudokuCell;
import org.springframework.stereotype.Component;

@Component
public final class BoardMapper {

    private static final int SIZE = 9;

    private BoardMapper() {
    }

    public static SudokuBoard toEntity(BoardResponse dto) {
        if (dto == null || dto.getGrid() == null) {
            throw new InvalidCharacterException("Puzzle grid is missing");
        }

        int[][] grid = dto.getGrid();
        if (grid.length != SIZE) {
            throw new InvalidCharacterException("Grid must have 9 rows");
        }

        SudokuCell[][] cells = new SudokuCell[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            if (grid[row] == null || grid[row].length != SIZE) {
                throw new InvalidCharacterException("Row " + row + " must have 9 columns");
            }

            for (int col = 0; col < SIZE; col++) {
                int value = grid[row][col];
                if (value < 0 || value > 9) {
                    throw new InvalidCharacterException(
                            "Invalid value at (" + row + "," + col + "): " + value
                    );
                }
                cells[row][col] = new SudokuCell(row, col, value);
            }
        }

        return new SudokuBoard(
                dto.getName() != null ? dto.getName() : "",
                cells,
                dto.isSolved()
        );
    }

    public static BoardResponse toResponse(SudokuBoard board) {
        if (board == null) {
            return null;
        }
        return new BoardResponse(
                board.getName(),
                board.isSolved(),
                gridToIntArray(board.getGrid())
        );
    }

    private static int[][] gridToIntArray(SudokuCell[][] grid) {
        if (grid == null || grid.length != SIZE) {
            return new int[0][0];
        }
        int[][] result = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            if (grid[row] != null && grid[row].length == SIZE) {
                for (int col = 0; col < SIZE; col++) {
                    result[row][col] = grid[row][col].getValue();
                }
            }
        }
        return result;
    }
}
