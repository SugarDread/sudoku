package com.sugardread.sudoku.Componets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugardread.sudoku.Models.Board;
import com.sugardread.sudoku.Models.BoardRepository;
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
    private final BoardRepository boardRepository;
    private final ObjectMapper objectMapper;

    JsonLoader(BoardRepository boardRepository, ObjectMapper objectMapper) {
        this.boardRepository = boardRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/data/boards.json")) {
            List<Board> boards = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            if (boards.size() == boardRepository.count()) {
                log.info("No new boards");
                return;
            }
            for (Board board : boards) {
                if (boardRepository.findById(board.id()).isEmpty()) {
                    boardRepository.save(board);
                }
            }
            log.info("Reading new boards");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load json", e);
        }
    }
}
