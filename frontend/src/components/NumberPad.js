import React from "react";

const NumberPad = ({ onNumberSelect }) => {
    return (
        <div className="number-pad">
            {
                [1, 2, 3, 4, 5, 6, 7, 8, 9].map(number => (
                    <button
                    key={number}
                    className="number-btn"
                    onClick={() => onNumberSelect(number)}>
                        {number}
                    </button>
                ))}
        </div>
    );
};

export default NumberPad;