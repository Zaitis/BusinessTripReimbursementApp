import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
  return (
    <div className="home-container">
      <h1>Welcome to the Reimbursement App</h1>
      <div className="buttons-container">
        <Link to="/add-reimbursement" className="action-button">
          Add a Reimbursement
        </Link>
        <Link to="/admin-login" className="action-button">
          Log in as Administrator
        </Link>
      </div>
    </div>
  );
}

export default Home;
