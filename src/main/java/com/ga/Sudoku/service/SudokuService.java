package com.ga.sudoku.service;

import com.ga.sudoku.dto.BoardMapper;
import com.ga.sudoku.dto.BoardResponse;
import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.exception.SudokuFileNotFoundException;
import com.ga.sudoku.model.SudokuBoard;
import com.ga.sudoku.model.SudokuCell;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class SudokuService {

    private static final int SIZE = 9;

    private final ObjectMapper objectMapper;
    private final BoardMapper boardMapper;
    private final SudokuSolver sudokuSolver;

    public SudokuService(ObjectMapper objectMapper, BoardMapper boardMapper, SudokuSolver sudokuSolver) {
        this.objectMapper = objectMapper;
        this.boardMapper = boardMapper;
        this.sudokuSolver = sudokuSolver;
    }

    public SudokuBoard loadPuzzle(String filename) {
        Path path = Paths.get("puzzles", filename);

        if (!Files.exists(path)) {
            throw new SudokuFileNotFoundException(
                    "Sudoku file not found: " + path.toAbsolutePath()
            );
        }

        try (InputStream inputStream = Files.newInputStream(path)) {
            BoardResponse dto = objectMapper.readValue(inputStream, BoardResponse.class);
            SudokuBoard board = BoardMapper.toEntity(dto);
            log.info("Sudoku board loaded: {}", board.getName());
            return board;
        } catch (InvalidCharacterException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidCharacterException(
                    "Failed to load puzzle: " + e.getMessage()
            );
        }
    }

    public SudokuBoard solvePuzzle(String boardName) {
        SudokuBoard board = loadPuzzle(boardName);
        SudokuBoard copy = deepCopy(board);
        if (!sudokuSolver.solve(copy)) {
            throw new InvalidCharacterException("Puzzle has no solution");
        }
        String solvedFilename = boardName.endsWith(".json")
                ? boardName.substring(0, boardName.length() - 5) + ".solution.json"
                : boardName + ".solution.json";
        savePuzzle(copy, solvedFilename);
        return copy;
    }

    private SudokuBoard deepCopy(SudokuBoard board) {
        SudokuCell[][] grid = board.getGrid();
        SudokuCell[][] newGrid = new SudokuCell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                SudokuCell cell = grid[row][col];
                newGrid[row][col] = new SudokuCell(cell.getRow(), cell.getCol(), cell.getValue());
            }
        }
        return new SudokuBoard(board.getName(), newGrid, false);
    }

    private void savePuzzle(SudokuBoard board, String filename) {
        try {
            Path dir = Paths.get("puzzles");
            Files.createDirectories(dir);
            Path path = dir.resolve(filename);
            BoardResponse dto = BoardMapper.toResponse(board);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), dto);
            log.info("Sudoku board saved: {}", path.toAbsolutePath());
        } catch (Exception e) {
            throw new InvalidCharacterException("Failed to save puzzle: " + e.getMessage());
        }
    }
}
