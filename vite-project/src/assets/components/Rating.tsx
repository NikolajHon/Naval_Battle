import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Rating from '@mui/material/Rating';
import { styled } from '@mui/material/styles';
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import SentimentDissatisfiedIcon from '@mui/icons-material/SentimentDissatisfied';
import SentimentSatisfiedIcon from '@mui/icons-material/SentimentSatisfied';
import SentimentSatisfiedAltIcon from '@mui/icons-material/SentimentSatisfiedAltOutlined';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';
import Box from '@mui/material/Box';

const StyledRating = styled(Rating)(({ theme }) => ({
    '& .MuiRating-iconEmpty .MuiSvgIcon-root': {
        color: theme.palette.action.disabled,
    },
}));

const customIcons = {
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

function IconContainer(props) {
    const { value, ...other } = props;
    return <span {...other}>{customIcons[value].icon}</span>;
}

const RadioGroupRating = ({ averageRating }) => {
    return (
        <StyledRating
            name="highlight-selected-only"
            value={averageRating} // Use averageRating as value
            IconContainerComponent={IconContainer}
            getLabelText={(value) => customIcons[value].label}
            highlightSelectedOnly
        />
    );
};

const RatingComponent = () => {
    const [averageRating, setAverageRating] = useState(null);

    useEffect(() => {
        fetchAverageRating();
    }, []);

    const fetchAverageRating = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/rating/average/battleship');
            console.log('Received rating:', response.data);
            setAverageRating(response.data);
        } catch (error) {
            console.error('Error fetching average rating:', error);
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            {averageRating !== null ? (
                <Box sx={{  p: 2, borderRadius: 4 }}>
                    <RadioGroupRating averageRating={averageRating} />
                    <p style={{ color: 'white', textAlign: 'center', marginTop: '8px' }}></p>
                </Box>
            ) : (
                <p style={{ color: 'white' }}>Loading...</p>
            )}
        </div>
    );
};

export default RatingComponent;
