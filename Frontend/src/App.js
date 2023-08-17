import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Używamy Routes
import Home from './components/Home/Home';
import AddReimbursement from './components/AddReimbursement/AddReimbursement';
import AdminLogin from './components/AdminLogin/AdminLogin';
import AdminDashboard from './components/adminDashboard/adminDashboard';
import ChangeRates from './components/ChangeRates/ChangeRates';

function App() {
  return (
    <Router>
      <div>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/add-reimbursement" element={<AddReimbursement />} />
          <Route path="/admin-login" element={<AdminLogin />} />
          <Route path="/adminDashboard" element={<AdminDashboard />} />
          <Route path="/changeRates" element={<ChangeRates />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;