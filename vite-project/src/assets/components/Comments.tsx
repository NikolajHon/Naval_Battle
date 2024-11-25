import React, { useState, useEffect } from 'react';
import axios from 'axios';
import team from "./images/team.png";
import comments from './images/comments.png'
import '../styles/Score.css';
import trophy from "./images/trophy.png";

// Определение типа для игрока
type Player = {
    ident: string;
    player: string;
    comment: string;
};

const Comments = () => {
    const [players, setPlayers] = useState<Player[]>([]);

    useEffect(() => {
        fetchPlayers(); // Вызываем fetchPlayers при монтировании компонента
    }, []); // Пустой массив зависимостей гарантирует выполнение эффекта только один раз, при монтировании компонента

    const fetchPlayers = async () => {
        try {
            const response = await axios.get<Player[]>('http://localhost:8080/api/comment/battleship');
            console.log('Received JSON:', response.data); // Логируем полученные данные в консоль
            setPlayers(response.data); // Устанавливаем полученные данные в состояние
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };

    return (
        <div className="score-container">
            <h2>All Comments <img src={comments} className={'team'}/></h2>

            <div className="table-container">
                <table className={'tt'}>
                    <thead>
                    <tr>
                        <th>Place <img src={trophy} className={'pisun'}/></th>
                        <th>Name</th>
                        <th>Comment</th>
                    </tr>
                    </thead>
                    <tbody>
                    {players.map((player, index) => (
                        <tr key={player.ident}>
                            <td>{index + 1}</td>
                            <td>{player.player}</td>
                            <td>{player.comment}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Comments;
