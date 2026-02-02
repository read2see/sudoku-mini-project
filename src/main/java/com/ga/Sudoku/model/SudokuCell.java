package com.ga.sudoku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SudokuCell {

    private int row;
    private int col;
    private int value;

    public boolean isEmpty(){
        return value == 0;
    }
}
