import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/Winner.css';

type GameData = {
    field1: string[];
    field2: string[];
    token: string;
    playerNames: string[];
};

const GamePage: React.FC = () => {
    const navigate = useNavigate();
    const [gameData, setGameData] = useState<GameData | null>(null);
    const [isDataLoaded, setIsDataLoaded] = useState<boolean>(false);
    const pressureBoxRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        handleGetField();
    }, []);

    useEffect(() => {
        if (!pressureBoxRef.current) return;

        const pressureBox = pressureBoxRef.current;

        const handleMouseMove = (event: MouseEvent) => {
            
            const rect = pressureBox.getBoundingClientRect();
            const x = event.clientX - rect.left;
            const y = event.clientY - rect.top;

            const xPercent = (x / rect.width - 0.5) * 2;
            const yPercent = (y / rect.height - 0.5) * 2;

            pressureBox.style.boxShadow = `inset ${-xPercent * 15}px ${-yPercent * 15}px 20px rgba(0, 0, 0, 0.5), 0 10px 20px rgba(0, 0, 0, 0.2)`;
        };

        const handleMouseLeave = () => {
            pressureBox.style.boxShadow = `inset 0 0 0 rgba(0,0,0,0.25), 0 10px 20px rgba(0,0,0,0.2)`;
        };

        pressureBox.addEventListener('mousemove', handleMouseMove);
        pressureBox.addEventListener('mouseleave', handleMouseLeave);

        return () => {
            pressureBox.removeEventListener('mousemove', handleMouseMove);
            pressureBox.removeEventListener('mouseleave', handleMouseLeave);
        };
    }, []);

    const handleGetField = async () => {
        try {
            const response = await fetch('http://localhost:8080/battleship/convertToJson');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.json();
            console.log('JSON file:', result);
            setGameData(result);
            setIsDataLoaded(true);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleRematch = () => {
        navigate('/generate');
    };

    const handleExit = () => {
        navigate('/');
    };
    const handleStats = () => {
        navigate('/services');
    };

    if (!isDataLoaded) {
        return <div>Loading...</div>;
    }

    let winnerMessage = '';
    if (gameData?.token === 'FirstPlayerWon') {
        winnerMessage = `${gameData?.playerNames[0]} wins!`;
    } else if (gameData?.token === 'SecondPlayerWon') {
        winnerMessage = `${gameData?.playerNames[1]} wins!`;
    }

    return (
        <div ref={pressureBoxRef} className={'pressure-box'}>
            <h1>{winnerMessage}</h1>
            <button onClick={handleExit}>Exit</button>
            <button onClick={handleStats}>Stats</button> {/* Добавленная кнопка */}
        </div>
    );

};

export default GamePage;
