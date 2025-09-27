package com.sugardread.sudoku.Models;

import org.springframework.data.repository.ListCrudRepository;

public interface BoardService extends ListCrudRepository<Board, Integer> {
}
