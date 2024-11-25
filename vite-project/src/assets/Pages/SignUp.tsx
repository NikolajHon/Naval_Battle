import React, { useState } from 'react';
import { Button, Card, CardContent, Input, IconButton, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';

// Регулярное выражение для проверки пароля
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;


const LoginForm: React.FC = () => {
    const navigate = useNavigate();
    const [name, setName] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [loginStatus, setLoginStatus] = useState<string>('');
    const [passwordMismatch, setPasswordMismatch] = useState<boolean>(false);
    const [userExists, setUserExists] = useState<boolean>(false);
    const [registrationSuccess, setRegistrationSuccess] = useState<boolean>(false);
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);

    const handleLogin = async () => {
        // Проверка соответствия пароля требованиям
        if (!password.match(passwordRegex)) {
            setPasswordMismatch(true);
            return;
        }

        if (password !== confirmPassword) {
            setPasswordMismatch(true);
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/person/get`);
            const players = await response.json();

            const existingPlayer = players.find((player: any) => player.name === name);
            if (existingPlayer) {
                setUserExists(true);
            } else {
                // Если пользователя нет, отправляем POST-запрос для регистрации
                const registerResponse = await fetch('http://localhost:8080/api/person', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ name, password }),
                });

                if (registerResponse.ok) {
                    setLoginStatus('success');
                    setRegistrationSuccess(true);
                } else {
                    setLoginStatus('failure');
                }
            }
        } catch (error) {
            console.error('Error logging in:', error);
            setLoginStatus('failure');
        }
    };

    const handleCloseDialog = () => {
        setPasswordMismatch(false);
        setUserExists(false);
        setRegistrationSuccess(false);
    };

    const handleBackToLogin = () => {
        navigate('/');
    };

    const toggleShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const toggleShowConfirmPassword = () => {
        setShowConfirmPassword(!showConfirmPassword);
    };

    return (
        <Card className="w-[350px] fade-in">
            <CardContent>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="name"
                            type="text"
                            value={name}
                            onChange={(event) => setName(event.target.value)}
                            placeholder="Enter your name"
                        />
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="password"
                            type={showPassword ? 'text' : 'password'}
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            placeholder="Enter your password"
                            endAdornment={
                                <IconButton onClick={toggleShowPassword} edge="end">
                                    {showPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
                                </IconButton>
                            }
                        />
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Input
                            id="confirmPassword"
                            type={showConfirmPassword ? 'text' : 'password'}
                            value={confirmPassword}
                            onChange={(event) => setConfirmPassword(event.target.value)}
                            placeholder="Confirm your password"
                            endAdornment={
                                <IconButton onClick={toggleShowConfirmPassword} edge="end">
                                    {showConfirmPassword ? <VisibilityIcon /> : <VisibilityOffIcon />}
                                </IconButton>
                            }
                        />
                    </div>
                </div>
                <Button onClick={handleLogin}>Register</Button>
                <Button onClick={handleBackToLogin}>Back to Login</Button>
                {/* Отображение сообщения о статусе входа */}
                {loginStatus === 'success' && <p>Login successful!</p>}
                {loginStatus === 'failure' && <p>Registration failed!</p>}
                {/* Всплывающее окно для неправильного ввода пароля */}
                <Dialog open={passwordMismatch} onClose={handleCloseDialog}>
                    <DialogTitle>Password Too Weak</DialogTitle>
                    <DialogContent>
                        The entered password is too weak. Please use a password that is at least 8 characters long and contains at least one letter and one digit.
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseDialog}>Close</Button>
                    </DialogActions>
                </Dialog>
                {/* Всплывающее окно для существующего пользователя */}
                <Dialog open={userExists} onClose={handleCloseDialog}>
                    <DialogTitle>User Already Exists</DialogTitle>
                    <DialogContent>
                        A user with the same name already exists. Please choose a different name.
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseDialog}>Close</Button>
                    </DialogActions>
                </Dialog>
                {/* Всплывающее окно для успешной регистрации */}
                <Dialog open={registrationSuccess} onClose={handleCloseDialog}>
                    <DialogTitle>Registration Successful</DialogTitle>
                    <DialogContent>
                        Your account has been successfully registered.
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCloseDialog}>Close</Button>
                    </DialogActions>
                </Dialog>
            </CardContent>
        </Card>
    );
};

export default LoginForm;
