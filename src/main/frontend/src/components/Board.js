import React from 'react';
import Cell from './Cell';

const Board = ({
    board,
    initialBoard,
    selectedCell,
    errors,
    onCellSelect
}) => {
    return (
        <div className="sudoku-board">
            {
                board.map((row, rowIndex) => (
                        <div key={rowIndex} className="board-row">
                            {
                                row.map((cell, colIndex) => (
                                    <Cell

                                        key={`${rowIndex}-${colIndex}`}

                                        value={cell}

                                        isInitial={initialBoard[rowIndex][colIndex] !== 0}

                                        isSelected={
                                            selectedCell &&
                                            selectedCell.row === rowIndex &&
                                            selectedCell.col == colIndex
                                        }

                                        isError={errors.has(`${rowIndex}-${colIndex}`)}

                                        onClick={() => onCellSelect(rowIndex, colIndex)}
                                    />
                                    )
                                )
                            }
                        </div>
                    )
                )
            }
        </div>
    );
};

export default Board;