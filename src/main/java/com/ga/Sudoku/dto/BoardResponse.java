package com.ga.sudoku.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardResponse {

    private String name;

    @JsonProperty("is_solved")
    private boolean solved;

    private int[][] grid;
}
