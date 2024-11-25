import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import destroyShipImg from './images/destroy_ship.png';
import pointImg from './images/point.png';
import emptyImg from './images/empty.png';
import enemycloseImg from './images/newenemy.png'
import closedImg from './images/newpanel.png';
import {navigate} from "gatsby";
import {useNavigate} from "react-router-dom";

interface FieldData {
    field1: string[];
    field2: string[];
    playerNames: string[];
    token: string;
}

interface Coordinates {
    row: number;
    column: number;
}

export default function Field() {
    const navigate = useNavigate(); // Initialize useNavigate hook
    const [fields, setFields] = useState<FieldData>({ field1: [], field2: [], playerNames: [], token: "" });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        handleGetField(); // Вызываем функцию для отправки GET запроса
    }, []); // Пустой массив зависимостей для выполнения только при монтировании компонента

    const handleGetField = () => {
        fetch("http://localhost:8080/battleship/convertToJson")
            .then(res => res.json())
            .then((result: FieldData) => {
                console.log("JSON file:", result);

                setFields({
                    field1: result.field1,
                    field2: result.field2,
                    playerNames: result.playerNames,
                    token: result.token
                });
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleSendCoordinates = (row: number, column: number) => {
        const coordinates: Coordinates = {
            row: row,
            column: column
        };

        console.log("Sending JSON data:", JSON.stringify(coordinates));

        fetch("http://localhost:8080/battleship/sendCoordinates", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(coordinates)
        })
            .then(response => {
                if (response.ok) {
                    console.log("Coordinates sent successfully!");
                    handleGetField();
                } else {
                    console.error('Failed to send coordinates:', response.statusText);
                }
            })
            .catch(error => console.error('Error:', error));
    };

    const getImageSrc = (cell: string) => {
        switch (cell) {
            case 'X':
                return destroyShipImg;
            case '*':
                return pointImg;
            case ' ':
                return closedImg;
            default:
                return emptyImg;
        }
    };
    const getenemyImageSrc = (cell: string) => {
        switch (cell) {
            case 'X':
                return destroyShipImg;
            case '*':
                return pointImg;
            case ' ':
                return enemycloseImg;
            default:
                return emptyImg;
        }
    };
    if(fields.token != "FirstPlayerShoots" && fields.token != "SecondPlayerShoots"){
        navigate("/winner");
    }
    return (
        <Box style={{ borderRadius: '10px', backgroundColor: '#3a0ca3', padding: '20px', display: 'flex', justifyContent: 'space-between', width: '100%' }}>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <>
                    <div style={{ flex: 1, marginRight: '20px', opacity: fields.field1.length ? 1 : 0, transition: 'opacity 0.5s' }}>
                        <table style={{borderCollapse: 'collapse'}}>

                            <tbody>
                            {fields.field1.map((row, rowIndex) => (
                                <tr key={rowIndex}>
                                    {row.split('').map((cell, columnIndex) => (
                                        <td key={columnIndex} style={{ padding: 0, border: 'none' }}>
                                            <button
                                                style={{
                                                    padding: 0,
                                                    border: 'none',
                                                    background: 'none',
                                                    cursor: 'pointer'
                                                }}
                                                onClick={() => handleSendCoordinates(rowIndex + 1, columnIndex + 1)}
                                                disabled={fields.token === "SecondPlayerShoots"}
                                                className="button-style"
                                            >
                                                <img src={getImageSrc(cell)} alt={cell} />
                                            </button>
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                    <div style={{ flex: 1, opacity: fields.field2.length ? 1 : 0, transition: 'opacity 0.5s' }}>
                        <table style={{ borderCollapse: 'collapse' }}>

                            <tbody>
                            {fields.field2.map((row, rowIndex) => (
                                <tr key={rowIndex}>
                                    {row.split('').map((cell, columnIndex) => (
                                        <td key={columnIndex} style={{ padding: 0, border: 'none' }}>
                                            <button
                                                style={{
                                                    padding: 0,
                                                    border: 'none',
                                                    background: 'none',
                                                    cursor: 'pointer'
                                                }}
                                                onClick={() => handleSendCoordinates(rowIndex + 1, columnIndex + 1)}
                                                disabled={fields.token === "FirstPlayerShoots"}
                                                className="button-style"
                                            >
                                                <img src={getenemyImageSrc(cell)} alt={cell} />
                                            </button>
                                        </td>
                                    ))}
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </>
            )}
        </Box>
    );
}
