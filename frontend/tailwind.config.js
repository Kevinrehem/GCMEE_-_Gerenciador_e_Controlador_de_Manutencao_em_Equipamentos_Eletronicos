/** @type {import('tailwindcss').Config} */
import animatePlugin from 'tailwindcss-animate'

export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        container: {
            center: true,
            padding: {
                DEFAULT: '1.25rem',
                sm: '1.5rem',
                lg: '2rem',
                xl: '4rem',
            },
        },
        extend: {
            colors: {
                'brand-purple': {
                    DEFAULT: '#7c3aed',
                    600: '#6d28d9',
                    500: '#7c3aed'
                },
                'bg-dark': '#0b0b0f',
                'gray-850': '#121217',
            },
            spacing: {
                '18': '4.5rem',
                '22': '5.5rem'
            }
        },
    },
    plugins: [animatePlugin],
}