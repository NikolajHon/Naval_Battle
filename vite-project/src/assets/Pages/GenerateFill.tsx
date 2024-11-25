import React from 'react';
import Header from '../components/Header.tsx';
import ShipGenerator from "../components/ShipGenerator.tsx";
import '../styles/GenerateFill.css';

const GenerateFill: React.FC = () => {
    return (


            <div className="container">

                <Header/>
                <ShipGenerator/>

            </div>


    );
};


export default GenerateFill;