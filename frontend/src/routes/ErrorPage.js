import React from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@mui/material';

export default function ErrorPage() {
  return (
    <div id="error-page">
      <h1>Oops!</h1>
      <p>
        <i>nic tu nie ma</i>
        <Button>
          <Link to="/app">wróć</Link>
        </Button>
      </p>
    </div>
  );
}