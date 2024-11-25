import {ThemeOptions} from "@mui/material";


export const themeOptions: ThemeOptions = {
    palette: {
        type: 'dark',
        primary: {
            main: '#03045E',
            light: '#90E0EF',
            dark: '#42b5d0',
            contrastText: '#ffffff',
        },
        secondary: {
            main: '#ffffff',
        },
        warning: {
            main: '#fd2a00',
            contrastText: '#00B4D8',
        },
        background: {
            default: '#03045E',
            paper: '#0077B6',
        },
        text: {
            primary: '#ffffff',
            secondary: '#ffffff',
            disabled: '#ffffff',
            hint: '#c10c0c',
        },
        divider: '#ffffff',
        error: {
            main: '#c90f00',
        },
        info: {
            main: '#ffffff',
        },
        success: {
            main: '#ffffff',
        },
    },
};