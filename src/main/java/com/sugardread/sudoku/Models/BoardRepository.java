package com.sugardread.sudoku.Models;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BoardRepository extends ListCrudRepository<Board, Integer> {
    List<Board> findAllByDifficulty(Difficulty difficulty);
}
