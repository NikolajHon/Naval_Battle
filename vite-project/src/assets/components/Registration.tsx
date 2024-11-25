import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import { Button } from "@mui/material";
import '../styles/Registration.css';

interface RegistrationProps {
    onSubmit: (name1: string, name2: string, autoPlace1: boolean, autoPlace2: boolean) => void;
}

const Registration: React.FC<RegistrationProps> = ({ onSubmit }) => {
    const navigate = useNavigate(); // Initialize useNavigate hook
    const [name1, setName1] = useState('');
    const [name2, setName2] = useState('');


    const handleName1Change = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName1(event.target.value);
    };

    const handleName2Change = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName2(event.target.value);
    };


    const handlePostRequest = async () => {
        navigate('/generate');
        const data = {
            player1Name: name1,
            player2Name: name2,
            someBoolean: true,
            someBoolean2: true,
        };

        try {
            const response = await fetch('http://localhost:8080/battleship/setPlayers', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error(`Error: ${response.statusText}`);
            }

            const result = await response.json();
            console.log('Success:', result);
            onSubmit(name1, name2, true, true); // Всегда отправляем true
            navigate('/generate');

        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div className="registration-container" style={{backgroundColor: '#023e8a'}}>
            <div className="input-container">
                <label htmlFor="name1">Player 1 Name:</label>
                <input
                    id="name1"
                    className="Input"
                    type="text"
                    placeholder="Enter player 1 name"
                    value={name1}
                    onChange={handleName1Change}
                />
            </div>
            <div className="input-container">
                <label htmlFor="name2">Player 2 Name:</label>
                <input
                    id="name2"
                    className="Input"
                    type="text"
                    placeholder="Enter player 2 name"
                    value={name2}
                    onChange={handleName2Change}
                />
            </div>
            <Button variant="contained" onClick={handlePostRequest}>
                Start Game (Send POST Request)
            </Button>
        </div>


    );
};

export default Registration;
