import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Импорт необходимых компонентов из react-router-dom
import Main from './assets/Pages/Main';
import GenerateFill from "./assets/Pages/GenerateFill.tsx";
import GameBoard from "./assets/Pages/GameBoard";
import Services from "./assets/Pages/Services.tsx";
import NewComment from "./assets/Pages/NewComment.tsx";
import Login from "./assets/Pages/Login.tsx"
import SignUp from "./assets/Pages/SignUp.tsx";
import Winner from  "./assets/Pages/Winner.tsx";
import './App.css';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/generate" element={<GenerateFill />} />
                <Route path="/play" element={<GameBoard />} />
                <Route path="/services" element={<Services />} />
                <Route path="/" element={<Login />} />
                <Route path="/newcomment" element={<NewComment />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/winner" element={<Winner />} />
            </Routes>
        </Router>
    );
}

export default App;
