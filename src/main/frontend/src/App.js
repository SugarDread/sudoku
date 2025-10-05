import React, { useState, useEffect } from 'react';
import Board from './components/Board';
import NumberPad from './components/NumberPad';
import { sudokuApi } from './services/sudokuApi';
import './styles/App.css';

function App() {

  //STATES

  const MAX_MISTAKES = 3;

  const [id, setId] = useState(null);

  const [board, setBoard] = useState([]);

  const [initialBoard, setInitialBoard] = useState([]);

  const [selectedCell, setSelectedCell] = useState(null);

  const [errors, setErrors] = useState(new Set());

  const [loading, setLoading] = useState(false);

  const [difficulty, setDifficulty] = useState("EASY");

  const [gameOver, setGameOver] = useState(false);

  const [mistakesCounter, setCounter] = useState(null);

  const [blanksCounter, setBlanks] = useState(null)



  //EFFETCTS

  useEffect(() => {
    loadNewBoard("EASY");
  }, []);


  //FUNCTIONS

  const loadNewBoard = async (newDifficulty) => {
    try {
      setLoading(true);

      console.log('Starting to load new board')

      const boardData = await sudokuApi.getNewBoard(newDifficulty);

      const parsedBoard = parseBoardString(boardData.init_board);

      setId(boardData.id);
      setBoard(parsedBoard);
      setInitialBoard(parsedBoard);
      setSelectedCell(null);
      setErrors(new Set());
      setGameOver(false);
      setCounter(0);
      setBlanks(boardData.blanks);

    } catch (error) {
      console.error('Error loading board: ', error);
    } finally {
      setLoading(false);
    }
  };

  const parseBoardString = (boardString) => {
    const result = [];

    for (let i = 0; i < 9; i++) {
      const rowString = boardString.slice(i * 9, (i + 1) * 9);

      const row = rowString.split('').map(Number);
      result.push(row);
    }

    return result;
  };

  const handleCellSelect = (row, col) => {
    if (initialBoard[row][col] !== 0) return;

    setSelectedCell({ row, col });
  };

  const handleNumberInput = async (number) => {
    if (!selectedCell) return;

    const { row, col } = selectedCell;

    if (initialBoard[row][col] !== 0) {
      console.log("Error");
      return;
    }

    try {
      const validationResult = await sudokuApi.validateMove(
        id,
        row * 9 + col,
        number
      );

      if (validationResult) {
        setBlanks(blanksCounter - 1);
        const newBoard = [...board];

        newBoard[row][col] = number;
        setBoard(newBoard);

        const newErrors = new Set(errors);
        newErrors.delete(`${row}-${col}`);
        setErrors(newErrors);

        console.log(board);

        if (blanksCounter === 1) {
          setGameOver(true);
          console.log("You Win");
        }

      } else {
        const newErrors = new Set(errors);
        newErrors.add(`${row}-${col}`);
        setErrors(newErrors);
        setCounter(mistakesCounter + 1);

        if (mistakesCounter + 1 >= MAX_MISTAKES) {
          setGameOver(true);
        }
      }
    } catch (error) {
      console.error('Validation error:', error);
    }
  };

  const handleDifficultyChange = (newDifficulty) => {
    setDifficulty(newDifficulty);
    loadNewBoard(newDifficulty);
  };


  return (
    <div className="app">
      <h1>Sudoku</h1>
      <div className='game-container'>
        <div className='game-board'>
            <div className="board">
                <div className={`blur-${gameOver}`}>
                    <Board
                        board={board}
                        initialBoard={initialBoard}
                        selectedCell={selectedCell}
                        errors={errors}
                        onCellSelect={handleCellSelect}
                    />
                </div>

                <div className={`game-over-window-${gameOver && blanksCounter !== 0}`}>
                    You lose
                </div>

                <div className={`win-window-${gameOver && blanksCounter === 0}`}>
                    You win
                </div>
            </div>

          <NumberPad onNumberSelect={handleNumberInput} />
        </div>

        <div className='game-controls'>
          <button className="new-game-btn" onClick={() => loadNewBoard(difficulty)}>
            New Game
          </button>

          <div className="difficulty-buttons">
            <button className={`difficulty-btn ${difficulty === "EASY" ? "active" : ''}`} onClick={() => handleDifficultyChange("EASY")}>EASY</button>
            <button className={`difficulty-btn ${difficulty === "MEDIUM" ? "active" : ''}`} onClick={() => handleDifficultyChange("MEDIUM")}>MEDIUM</button>
            <button className={`difficulty-btn ${difficulty === "HARD" ? "active" : ''}`} onClick={() => handleDifficultyChange("HARD")}>HARD</button>
            <button className={`difficulty-btn ${difficulty === "EXPERT" ? "active" : ''}`} onClick={() => handleDifficultyChange("EXPERT")}>EXPERT</button>
          </div>

          <div className="counter">
            Mistakes: {mistakesCounter}/3
          </div>
        </div>
      </div>
    </div>
  )

}

export default App;
