import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './AdminLogin.css';

function AdminLogin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const loginData = {
      username: username,
      password: password,
    };

    try {
      const response = await fetch('http://localhost:8000/admin/login', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(loginData),
      });

      if (response.ok) {
        toast.success('Login successful.', {
          position: toast.POSITION.TOP_CENTER,
        });
        navigate('/adminDashboard');
      } else {
        toast.error('Login failed: ' + response.statusText, {
          position: toast.POSITION.TOP_CENTER,
        });
      }
    } catch (error) {
      toast.error('An error occurred: ' + error.message, {
        position: toast.POSITION.TOP_CENTER,
      });
    }
  };

  return (
    <div className="admin-login-container">
      <h2>Admin Login</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Username:</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">Login</button>
      </form>
      <ToastContainer />
    </div>
  );
}

export default AdminLogin;
