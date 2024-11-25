import React, { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import FormControl from '@mui/material/FormControl';
import NativeSelect from '@mui/material/NativeSelect';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Radio from '@mui/material/Radio';
import destroyShipImg from './images/destroy_ship.png';
import pointImg from './images/point.png';
import closedImg from './images/newpanel.png';
import emptyImg from './images/empty.png';
import enemycloseImg from './images/newenemy.png';
import firstshipImg from './images/firstship.png'
import secondshipImg from './images/secondship.png'
import { useNavigate } from "react-router-dom";
import '../styles/ShipGenerator.css';

interface Details {
    field1: string[];
    field2: string[];
    playerNames: string[];
    token: string;
}

let field: string[] = [];
let name: string;

interface Coordinates {
    row: number;
    column: number;
    shipType: number;
    shipDirection: 'H' | 'V';
}

function ShipGenerator() {
    const navigate = useNavigate();
    const [shipOrientation, setShipOrientation] = useState<'horizontal' | 'vertical'>('horizontal');
    const [shipType, setShipType] = useState<number>(1);
    const [fields, setFields] = useState<Details>({
        field1: [],
        field2: [],
        playerNames: [],
        token: '',
    });
    const [shipCoordinates, setShipCoordinates] = useState<Coordinates>({
        row: 0,
        column: 0,
        shipType: 1,
        shipDirection: 'horizontal'
    });
    const [isFieldLoaded, setIsFieldLoaded] = useState<boolean>(false); // State to track if the field is loaded
    const [windowWidth, setWindowWidth] = useState(window.innerWidth); // State to track window width

    useEffect(() => {
        handleGetField(); // Load field when the component mounts

        const handleResize = () => {
            setWindowWidth(window.innerWidth);
        };

        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []); // Empty dependency array ensures this effect runs only once

    const handleShipOrientationChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        const direction = event.target.value === 'horizontal' ? 'H' : 'V';
        setShipOrientation(event.target.value as 'horizontal' | 'vertical');
        setShipCoordinates(prevState => ({ ...prevState, shipDirection: direction }));
    };

    const handleShipTypeChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        setShipType(event.target.value as number);
        setShipCoordinates(prevState => ({ ...prevState, shipType: event.target.value as number }));
    };

    const handleGetField = async () => {
        try {
            const response = await fetch("http://localhost:8080/battleship/convertToJson");
            const result: Details = await response.json();
            console.log("JSON file:", result);
            setFields(result);
            setIsFieldLoaded(true); // Set field loaded to true after successful retrieval
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleplaceShip = async (shipCoords: Coordinates) => {
        console.log("Sending JSON data:", JSON.stringify(shipCoords));

        try {
            const response = await fetch("http://localhost:8080/battleship/placeShip", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(shipCoords)
            });
            if (response.ok) {
                console.log("Coordinates sent successfully!");
                handleGetField();
            } else {
                console.error('Failed to send coordinates:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handlePlaceShipFromTable = (rowIndex: number, columnIndex: number) => {
        const updatedShipCoordinates: Coordinates = {
            row: rowIndex + 1,
            column: columnIndex + 1,
            shipType: shipType,
            shipDirection: shipOrientation
        };

        handleplaceShip(updatedShipCoordinates);
    };

    const getImageSrc = (cell: string) => {
        let imageSrc = '';

        switch (cell) {
            case 'X':
                imageSrc = destroyShipImg;
                break;
            case '*':
                imageSrc =  pointImg;
                break;
            case ' ':
                if (fields.token === "FirstPlayerPlacesShips") {
                    imageSrc =  closedImg;
                } else {
                    imageSrc =  enemycloseImg;
                }
                break;
            case '0':
                if (fields.token === "FirstPlayerPlacesShips") {
                    imageSrc =  firstshipImg;
                } else {
                    imageSrc =  secondshipImg;
                }
                break;
            default:
                imageSrc = emptyImg;
                break;
        }

        return imageSrc;
    };

    const handleResetField = async () => {
        try {
            const response = await fetch("http://localhost:8080/battleship/resetFields", {
                method: 'POST',
            });
            if (response.ok) {
                console.log("Fields reset successfully!");
                handleGetField(); // Получаем новое поле после сброса
            } else {
                console.error('Failed to reset fields:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleGenerateField = async () => {
        try {
            const response = await fetch("http://localhost:8080/battleship/generateAuto", {
                method: 'POST',
            });
            if (response.ok) {
                console.log("Fields reset successfully!");
                handleGetField(); // Получаем новое поле после сброса
            } else {
                console.error('Failed to reset fields:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleReady = async () => {
        try {
            const response = await fetch("http://localhost:8080/battleship/switchPlayer", {
                method: 'POST',
            });
            if (response.ok) {
                handleGetField();
            } else {
                console.error('Failed to reset fields:', response.statusText);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    if (fields.token === "FirstPlayerPlacesShips") {
        field = fields.field1;
        name = fields.playerNames[0];
    } else if (fields.token === "SecondPlayerPlacesShips") {
        field = fields.field2;
        name = fields.playerNames[1];
    } else {
        navigate('/play');
    }

    return (
        <Box style={{ opacity: isFieldLoaded ? 1 : 0, transition: 'opacity 0.5s ease-in-out' }} id='huy'>
            <Box style={{ marginTop: '20px',  overflow: 'hidden', backgroundColor: '#023e8a', borderRadius: '10px', padding: '20px' }}>
                <Box display="flex" flexDirection="column" alignItems="center">
                    <div>{fields.playerNames[1]}</div>
                    <table style={{ marginRight: '20px', borderCollapse: 'collapse' }}>
                        <tbody>
                        {field.map((row, rowIndex) => (
                            <tr key={rowIndex}>
                                {row.split('').map((cell, columnIndex) => (
                                    <td key={columnIndex} style={{ padding: 0, border: 'none' }}>
                                        <button className="custom-button"
                                                style={{
                                                    padding: 0,
                                                    border: 'none',
                                                    background: 'none',
                                                    cursor: 'pointer'
                                                }}
                                                onClick={() => handlePlaceShipFromTable(rowIndex, columnIndex)}
                                                disabled={fields.token === "SecondPlayerShoots"}
                                        >
                                                <img src={getImageSrc(cell)} alt={cell} />
                                        </button>
                                    </td>
                                ))}
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </Box>
            </Box>
            <Box className="ControlPanelContainer" style={{ marginLeft: '20px', padding: '10px', borderRadius: '10px', backgroundColor: '#023e8a', marginTop: '20px' }}>
                <FormControl component="fieldset">
                    <RadioGroup
                        aria-label="ship-orientation"
                        name="ship-orientation"
                        value={shipOrientation}
                        onChange={handleShipOrientationChange}
                    >
                        <FormControlLabel value="horizontal" control={<Radio />} label="Horizontal" />
                        <FormControlLabel value="vertical" control={<Radio />} label="Vertical" />
                    </RadioGroup>
                </FormControl>
                <FormControl className="ShipTypeSelect" style={{ marginTop: '10px', padding: 0 }}>
                    <NativeSelect
                        value={shipType}
                        onChange={handleShipTypeChange}
                        inputProps={{ id: 'ship-type' }}
                        style={{ padding: 0 }}
                    >
                        <option value={1}>One deck</option>
                        <option value={2}>Two deck</option>
                        <option value={3}>Three deck</option>
                        <option value={4}>Four deck</option>
                    </NativeSelect>
                </FormControl>
                <button onClick={handleResetField} style={{ width: '100%', marginTop: '10px' }}>Reset Field</button>
                <button onClick={handleGenerateField} style={{ width: '100%', marginTop: '10px' }}>Generate Field</button>
                <button onClick={handleReady} style={{ width: '100%', marginTop: '10px' }}>Ready</button>
            </Box>
        </Box>
    );
}

export default ShipGenerator;
