import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './ChangeRates.css';

function ChangeRates() {
  const [dailyAllowanceAmount, setDailyAllowanceAmount] = useState('');
  const [carMileageAmount, setCarMileageAmount] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const ratesData = {
      newDailyAllowanceAmount: parseFloat(dailyAllowanceAmount),
      newCarMileageAmount: parseFloat(carMileageAmount),
    };

    try {
      const response = await fetch('http://localhost:8000/admin/updateRates', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(ratesData),
      });

      if (response.ok) {
        console.log('Rates updated successfully.');
      } else {
        console.error('Error updating rates:', response.statusText);
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  return (
    <div className="change-rates-container">
      <h2>Change Rates</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Daily Allowance Amount:</label>
          <input
            className="input-field"
            type="number"
            step="0.01"
            value={dailyAllowanceAmount}
            onChange={(e) => setDailyAllowanceAmount(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Car Mileage Amount:</label>
          <input
            className="input-field"
            type="number"
            step="0.01"
            value={carMileageAmount}
            onChange={(e) => setCarMileageAmount(e.target.value)}
          />
        </div>
        <div className="form-buttons">
          <button className="submit-button" type="submit">Submit</button>
          <Link className="back-link" to="/adminDashboard">Back</Link>
        </div>
      </form>
    </div>
  );
}

export default ChangeRates;
