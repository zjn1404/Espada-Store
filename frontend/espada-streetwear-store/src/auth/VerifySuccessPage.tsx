import React from 'react';
import { useHistory } from 'react-router-dom';

export const VerifySuccessPage = () => {
    const history = useHistory();

    const handleRedirect = () => {
        history.push('/home'); 
    };

    return (
        <div style={{ textAlign: 'center', marginTop: '3rem', marginBottom: '12rem'}}>
            <h1>Email Verification Successful!</h1>
            <p>Your email has been successfully verified. You can now access your account.</p>
            <button onClick={handleRedirect} style={{ marginTop: '20px', padding: '10px 20px' }}>
                Go to Home
            </button>
        </div>
    );
};
