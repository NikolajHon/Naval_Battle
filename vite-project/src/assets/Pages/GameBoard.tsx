import React from 'react';
import Header from '../components/Header.tsx';
import Field from "../components/Field.tsx";
import '../styles/GameBoard.css';

const GameBoard: React.FC = () => {
    return (
        <div className="main_container">
            <div className="header_registration_container">
                <Header />
                <Field />
            </div>
        </div>
    );
};

export default GameBoard;