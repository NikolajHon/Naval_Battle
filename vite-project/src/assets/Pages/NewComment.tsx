import React, { useState } from 'react';
import { styled } from '@mui/material/styles';
import Rating, { IconContainerProps } from '@mui/material/Rating';
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import SentimentDissatisfiedIcon from '@mui/icons-material/SentimentDissatisfied';
import SentimentSatisfiedIcon from '@mui/icons-material/SentimentSatisfied';
import SentimentSatisfiedAltIcon from '@mui/icons-material/SentimentSatisfiedAltOutlined';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';
import axios from 'axios';
import Box from "@mui/material/Box";
import '../styles/NewComment.css';
import {useNavigate} from "react-router-dom";
const StyledRating = styled(Rating)(({ theme }) => ({
    '& .MuiRating-iconEmpty .MuiSvgIcon-root': {
        color: theme.palette.action.disabled,
    },
}));

const customIcons: {
    [index: string]: {
        icon: React.ReactElement;
        label: string;
    };
} = {
    1: {
        icon: <SentimentVeryDissatisfiedIcon color="error" />,
        label: 'Very Dissatisfied',
    },
    2: {
        icon: <SentimentDissatisfiedIcon color="error" />,
        label: 'Dissatisfied',
    },
    3: {
        icon: <SentimentSatisfiedIcon color="warning" />,
        label: 'Neutral',
    },
    4: {
        icon: <SentimentSatisfiedAltIcon color="success" />,
        label: 'Satisfied',
    },
    5: {
        icon: <SentimentVerySatisfiedIcon color="success" />,
        label: 'Very Satisfied',
    },
};

function IconContainer(props: IconContainerProps) {
    const { value, ...other } = props;
    return <span {...other}>{customIcons[value].icon}</span>;
}

const RatingForm: React.FC = () => {
    const navigate = useNavigate();
    const [player, setPlayer] = useState('');
    const [game, setGame] = useState('');
    const [rating, setRating] = useState(2); // Значение по умолчанию
    const [comment, setComment] = useState('');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        try {
            // Отправка запроса для рейтинга
            await axios.post('http://localhost:8080/api/rating', {
                player,
                game,
                rating,
                ratedOn: new Date().toISOString()
            });

            // Отправка запроса для комментария
            await axios.post('http://localhost:8080/api/comment', {
                player,
                game,
                comment,
                commentedOn: new Date().toISOString()
            });

            // Обновление полей формы после успешной отправки
            setPlayer('');
            setGame('');
            setRating(5); // Возвращаем значение рейтинга по умолчанию
            setComment('');
        } catch (error) {
            console.error('Error:', error);
        }
    };
    const handleExit = () => {
        navigate('/services');
    };
    return (

        <Box className={"huy"}>
            <form onSubmit={handleSubmit}>
                <label>
                    <p>Player:</p>
                    <input type="text" value={player} onChange={(e) => setPlayer(e.target.value)}/>
                </label>
                <label>
                    <p>
                        Game:
                    </p>

                    <input type="text" value={game} onChange={(e) => setGame(e.target.value)}/>
                </label>
                <label>
                    <p>
                        Rating:
                    </p>

                    <StyledRating
                        name="highlight-selected-only"
                        value={rating}
                        onChange={(event, newValue) => {
                            setRating(newValue !== null ? newValue : 2); // Если newValue равно null, то возвращаем значение по умолчанию
                        }}
                        IconContainerComponent={IconContainer}
                        getLabelText={(value: number) => customIcons[value].label}
                        highlightSelectedOnly
                    />
                </label>
                <label>
                    <p>
                        Comment:
                    </p>

                    <textarea value={comment} onChange={(e) => setComment(e.target.value)}/>
                </label>
                <button type="submit" className={'button'} onClick={handleExit}>Back</button>
                <button type="submit" className={'button'} onClick={handleSubmit}>Push</button>

            </form>
        </Box>
    );
};

export default RatingForm;
