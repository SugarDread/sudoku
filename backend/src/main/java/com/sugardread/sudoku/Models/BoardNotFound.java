package com.sugardread.sudoku.Models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BoardNotFound extends RuntimeException {
    public BoardNotFound() {
        super("Board not found");
    }
}
