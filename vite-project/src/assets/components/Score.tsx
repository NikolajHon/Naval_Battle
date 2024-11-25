import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../styles/Score.css';
import team from './images/team.png'
import trophy from './images/trophy.png'
// Define a type for player
type Player = {
    ident: string;
    player: string;
    points: number;
};

const Score = () => {
    const [players, setPlayers] = useState<Player[]>([]); // Set the type for players state

    useEffect(() => {
        fetchPlayers(); // Fetch players immediately after component is mounted
    }, []); // Empty dependency array means this effect will run only once after initial render

    const fetchPlayers = async () => {
        try {
            const response = await axios.get<Player[]>('http://localhost:8080/api/score/battleship');
            console.log('Received JSON:', response.data); // Log the received JSON
            setPlayers(response.data.slice(0, 5)); // вывод первых пяти игроков
        } catch (error) {
            console.error('Error fetching players:', error);
        }
    };

    return (
        <div className="score-container">
            <h2>Top 5 Players <img src={team} className={'team'}/></h2>
            {/* Remove button since we're fetching players immediately */}
            <div className="table-container">
                <table className={'tt'}>
                    <thead>
                    <tr>
                        <th>Place <img src={trophy} className={'pisun'}/></th>
                        <th>Name</th>
                        <th>Points</th>
                    </tr>
                    </thead>
                    <tbody>
                    {players.map((player, index) => (
                        <tr key={player.ident} className={index < 3 ? 'top-player' : ''}>
                            <td>{index + 1}</td>
                            <td>{player.player}</td>
                            <td>{player.points}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Score;
