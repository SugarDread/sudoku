package com.sugardread.sudoku.Controllers;

import com.sugardread.sudoku.Models.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    List<Board> findAll() {
        return boardRepository.findAll();
    }

    @GetMapping("/boards/{difficulty}")
    List<Board> findAllByDifficulty(@PathVariable Difficulty difficulty) {
        return boardRepository.findAllByDifficulty(difficulty);
    }

    @GetMapping("/board/{difficulty}/random")
    BoardForSudoku findRandomByDifficulty(@PathVariable Difficulty difficulty) {
        Board board = boardService.findRandomByDifficulty(difficulty);
        return new BoardForSudoku(board.id(), board.init_board(), board.blanks());
    }

    @GetMapping("/board/{id}")
    Board findById(@PathVariable Integer id) {
        return boardRepository.findById(id)
                .orElseThrow(BoardNotFound::new);
    }

    @PostMapping("/board/validate")
    Boolean validateMove(Integer id, Integer position, Character value) {
        Board board = findById(id);
        return board.solution_board().charAt(position) == value;
    }
}
