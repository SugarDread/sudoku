package com.sugardread.sudoku.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;


public record Board(
        @Id
        Integer id,
        String init_board,
        String solution_board,
        Difficulty difficulty,
        Integer blanks,
        @Version
        Integer version
) {
}
