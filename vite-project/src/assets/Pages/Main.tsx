import React from 'react';
import Header from '../components/Header.tsx';
import Registration from '../components/Registration.tsx';
import '../styles/Main.css';

const Main: React.FC = () => {
    return (
        <div className="main_container">
            <div className="header_registration_container">
                <Header />
                <Registration />
            </div>
        </div>
    );
};

export default Main;