package com.ga.sudoku.service;

import com.ga.sudoku.dto.BoardMapper;
import com.ga.sudoku.dto.BoardResponse;
import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.exception.SudokuFileNotFoundException;
import com.ga.sudoku.model.SudokuBoard;
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

    private final ObjectMapper objectMapper;
    private final BoardMapper boardMapper;

    public SudokuService(ObjectMapper objectMapper,  BoardMapper boardMapper) {
        this.objectMapper = objectMapper;
        this.boardMapper = boardMapper;
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
}
