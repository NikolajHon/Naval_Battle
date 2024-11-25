import React, { useState } from 'react';
import { Button, Card, CardContent, Dialog, DialogActions, DialogContent, DialogTitle, Input } from "@mui/material";
import { useNavigate } from "react-router-dom";
import '../styles/Login.css';
import man from '../components/images/man.png';
import woman from '../components/images/woman.png';

const LoginForm: React.FC = () => {
    const navigate = useNavigate();
    const [name1, setName1] = useState<string>('');
    const [password1, setPassword1] = useState<string>('');
    const [name2, setName2] = useState<string>('');
    const [password2, setPassword2] = useState<string>('');
    const [player1LoggedIn, setPlayer1LoggedIn] = useState<boolean>(false);
    const [player2LoggedIn, setPlayer2LoggedIn] = useState<boolean>(false);
    const [showUnregisteredDialog, setShowUnregisteredDialog] = useState<boolean>(false);
    const [unregisteredPlayer, setUnregisteredPlayer] = useState<string>('');

    const handleLoginPlayer1 = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/person/get');
            const players = await response.json();

            const player = players.find((p: any) => p.name === name1 && p.password === password1);
            if (player) {
                setPlayer1LoggedIn(true);
            } else {
                setUnregisteredPlayer('Player 1');
                setShowUnregisteredDialog(true);
            }
        } catch (error) {
            console.error('Error logging in:', error);
        }
    };

    const handleLoginPlayer2 = async () => {
        try {

            const response = await fetch('http://localhost:8080/api/person/get');
            const players = await response.json();

            const player = players.find((p: any) => p.name === name2 && p.password === password2);
            if (player) {
                setPlayer2LoggedIn(true);
            } else {
                setUnregisteredPlayer('Player 2');
                setShowUnregisteredDialog(true);
            }
        } catch (error) {
            console.error('Error logging in:', error);
        }
    };

    const handleRegister = async () => {

        navigate("/signup");

    };
    const handlePlay = async () => {
        if (player1LoggedIn && player2LoggedIn) {
            try {
                const data = {
                    player1Name: name1,
                    player2Name: name2,
                    someBoolean: true,
                    someBoolean2: true,
                };

                const response = await fetch('http://localhost:8080/battleship/setPlayers', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                });

                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                // Здесь можно добавить дополнительные действия после успешной отправки запроса, например, переход на страницу игры
                navigate("/generate");
            } catch (error) {
                console.error('Error:', error);
            }
        } else {
            setShowUnregisteredDialog(true);
        }
    };

    return (

        <Card className="w-[350px] fade-in">
            <CardContent>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="name1"
                            type="text"
                            value={name1}
                            onChange={(event) => setName1(event.target.value)}
                            placeholder="Enter player 1 name"
                        />
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="password1"
                            type="password"
                            value={password1}
                            onChange={(event) => setPassword1(event.target.value)}
                            placeholder="Enter player 1 password"
                        />
                    </div>
                    <Button onClick={handleLoginPlayer1}>Login Player 1 <img src={man} alt="man" className={'human'}/>
                    </Button>


                </div>
            </CardContent>
            <CardContent>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="name2"
                            type="text"
                            value={name2}
                            onChange={(event) => setName2(event.target.value)}
                            placeholder="Enter player 2 name"
                        />
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="password2"
                            type="password"
                            value={password2}
                            onChange={(event) => setPassword2(event.target.value)}
                            placeholder="Enter player 2 password"
                        />
                    </div>
                    <Button onClick={handleLoginPlayer2}>Login Player 2 <img src={woman} alt="woman" className={'human2'}/>
                    </Button>
                </div>
            </CardContent>
            <CardContent>
                <Button onClick={handlePlay}>Play</Button>
                <Button onClick={handleRegister}>Register</Button>
            </CardContent>
            {/* Всплывающее окно для незарегистрированного игрока */}
            <Dialog open={showUnregisteredDialog}>
                <DialogTitle>Player Not Logged In</DialogTitle>
                <DialogContent>
                    <p>One or both players are not logged in.</p>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setShowUnregisteredDialog(false)}>Close</Button>
                </DialogActions>
            </Dialog>
        </Card>
    );
};

export default LoginForm;
