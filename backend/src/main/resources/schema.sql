CREATE TABLE IF NOT EXISTS Board (
    id INT,
    init_board varchar(90) NOT NULL,
    solution_board varchar(90) NOT NULL,
    difficulty varchar(10) NOT NULL,
    blanks INT NOT NULL,
    version INT,
    PRIMARY KEY (id)
);

