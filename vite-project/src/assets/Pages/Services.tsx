import React, { useState, useEffect } from 'react';
import Header from '../components/Header.tsx';
import Rating from '../components/Rating.tsx';
import Comments from '../components/Comments.jsx';
import Score from '../components/Score.jsx';
import '../styles/Service.css';
import Box from "@mui/material/Box";
import {Button} from "@mui/material";
import {useNavigate} from "react-router-dom";


const Services: React.FC = () => {
    const navigate = useNavigate();
    const [isVisible, setIsVisible] = useState(false);

    useEffect(() => {
        // Показываем компонент после задержки 1 секунда
        const timeout = setTimeout(() => {
            setIsVisible(true);
        }, 300);

        // Очищаем таймер при размонтировании компонента
        return () => clearTimeout(timeout);
    }, []); // Вызываем этот эффект только при монтировании
    const handleRematch = () => {
        navigate('/generate');
    };
    const handleComment = () => {
        navigate('/newcomment');
    };
    return (
        <div className="main_container">
            <Box className={`box_container ${isVisible ? 'show' : ''}`}>
                <div className={'component'}><Header/></div>
                <div className={'component-rating'}><Rating/></div>
                <div className={'component'}><Score/></div>
                <div className={'component'}><Comments/></div>
                <Button onClick={handleRematch}>
                    New Game
                </Button>
                <Button onClick={handleComment}>
                    New Comment
                </Button>
            </Box>
        </div>
    );
};

export default Services;
