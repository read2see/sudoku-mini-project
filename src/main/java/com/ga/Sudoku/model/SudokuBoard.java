package com.ga.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SudokuBoard {

    private String name;
    private SudokuCell[][] grid;
    private boolean isSolved;

    public String displayBoard() {
        StringBuilder out = new StringBuilder();
        if (name != null && !name.isBlank()) {
            out.append("Board: ").append(name).append("\n");
        }
        if (grid == null || grid.length == 0) {
            out.append("(empty board)");
            return out.toString();
        }
        for (int row = 0; row < grid.length; row++) {
            if (row > 0 && row % 3 == 0) {
                out.append("------+-------+------\n");
            }
            for (int col = 0; col < grid[row].length; col++) {
                if (col > 0 && col % 3 == 0) {
                    out.append("| ");
                }
                int value = grid[row][col].getValue();
                out.append(value == 0 ? ". " : value + " ");
            }
            out.append("\n");
        }
        return out.toString();
    }

    public SudokuBoard(SudokuBoard sudokuBoard){
        this.name = sudokuBoard.name;
        this.grid = sudokuBoard.grid;
        this.isSolved = false;
    }
}
