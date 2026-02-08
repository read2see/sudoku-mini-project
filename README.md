# Sudoku Mini Project
A basic implementation of [Sudoku](https://sudoku.com/) using Spring Boot.

Boards are stored in `puzzles/`.

- `/api/display-board/{boardFileName}`: shows sudoku board.
example: `/api/display-board/test-board-1.json`
- `/api/boards/{boardFileName}/solve`: Solves board and saves it in `puzzles/`.
example: `/api/boards/test-board-1.json/solve`
- `/api/display-board/test-board-1.solution.json`: View solved board