package com.sugardread.sudoku.Controllers;

import com.sugardread.sudoku.Models.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }

    @GetMapping("")
    List<Board> findAll() {
        return boardRepository.findAll();
    }

    @GetMapping("/{difficulty}")
    List<Board> findAllByDifficulty(@PathVariable Difficulty difficulty) {
        return boardRepository.findAllByDifficulty(difficulty);
    }

    @GetMapping("/{difficulty}/random")
    Board findRandomByDifficulty(@PathVariable Difficulty difficulty) {
        return boardService.findRandomByDifficulty(difficulty);
    }

}
