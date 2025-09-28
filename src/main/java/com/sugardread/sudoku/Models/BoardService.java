package com.sugardread.sudoku.Models;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final Random random = new Random();

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board findRandomByDifficulty(Difficulty difficulty) {
        List<Board> boards = boardRepository.findAllByDifficulty(difficulty);
        if (boards == null || boards.isEmpty()) {
            throw new BoardNotFound();
        }
        else {
            return boards.get(random.nextInt(boards.size()));
        }
    }
}
