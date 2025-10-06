import React from "react";

const Cell = ({
    value,
    isInitial,
    isSelected,
    isError,
    onClick
}) => {
    

    const getCellClassName = () => {
        let className = 'sudoku-cell';

        if (isInitial) className += ' initial';
        if (isSelected) className += ' selected';
        if (isError) className += ' error';

        return className;
    };

    return (
        <div className={getCellClassName()} onClick={onClick}>
            {
                value !== 0 ? value : ''
            }
        </div>
    );
};


export default Cell;