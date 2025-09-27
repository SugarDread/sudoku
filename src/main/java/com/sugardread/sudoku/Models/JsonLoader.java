package com.sugardread.sudoku.Models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonLoader implements CommandLineRunner {

    static private final Logger log = LoggerFactory.getLogger(JsonLoader.class);
    private final BoardService boardService;
    private final ObjectMapper objectMapper;

    JsonLoader(BoardService boardService, ObjectMapper objectMapper) {
        this.boardService = boardService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/data/boards.json")) {
            List<Board> boards = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            if (boards.size() == boardService.count()) {
                log.info("No new boards");
                return;
            }
            for (Board board : boards) {
                if (boardService.findById(board.id()).isEmpty()) {
                    boardService.save(board);
                }
            }
            log.info("Reading new boards");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load json", e);
        }
    }
}
