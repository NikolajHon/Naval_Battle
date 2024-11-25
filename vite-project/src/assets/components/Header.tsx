import React from 'react';
import { AppBar, Toolbar, Typography, Link, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom'; // предположим, что вы используете React Router для маршрутизации
import '../styles/Header.css'; // Импорт файла стилей

const Header: React.FC = () => {
    const navigate = useNavigate(); // получаем функцию navigate из React Router

    const handleServicesClick = () => {
        navigate('/services'); // перенаправляем на /services при клике на кнопку
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar className="AppBar" sx={{ backgroundColor: '#03045E', display: 'flex' }}>
                <Toolbar className="Toolbar" sx={{ flexGrow: '1', display: 'flex', justifyContent: 'space-between' }}>
                    <Typography variant="h6" component="div" className="Typography">
                        Мужской бой
                    </Typography>
                    <Box className="LinksContainer">
                        <Link href="#" color="inherit" className="Link" onClick={handleServicesClick}>Services</Link>
                    </Box>
                </Toolbar>
            </AppBar>
        </Box>
    );
};

export default Header;
