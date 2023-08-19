import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './ChangeRates.css';

const Type = {
  TAXI: 'TAXI',
  HOTEL: 'HOTEL',
  PLANE_TICKET: 'PLANE_TICKET',
  TRAIN: 'TRAIN',
  FOOD: 'FOOD',
  RELAX: 'RELAX',
  TICKET: 'TICKET',
};

function ChangeRates() {
  const [dailyAllowanceAmount, setDailyAllowanceAmount] = useState('');
  const [carMileageAmount, setCarMileageAmount] = useState('');
  const [carMileageLimit, setCarMileageLimit] = useState('');
  const [receiptLimits, setReceiptLimits] = useState({});
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    async function fetchRates() {
      try {
        const response = await fetch('http://localhost:8000/admin/getRates');
        if (response.ok) {
          const data = await response.json();
          setDailyAllowanceAmount(data.dailyAllowanceAmount);
          setCarMileageAmount(data.carMileageAmount);
          setCarMileageLimit(data.carMileageLimit);
          setReceiptLimits(data.receiptLimits);
          setIsLoading(false);
        } else {
          console.error('Error fetching rates:', response.statusText);
        }
      } catch (error) {
        console.error('An error occurred:', error);
      }
    }

    fetchRates();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const headers = new Headers();
    headers.append('Content-Type', 'application/json');

    const ratesData = {
      newDailyAllowanceAmount: parseFloat(dailyAllowanceAmount),
      newCarMileageAmount: parseFloat(carMileageAmount),
      newCarMileageLimit: parseFloat(carMileageLimit),
      receiptLimits: receiptLimits,
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

  const handleReceiptLimitChange = (type, value) => {
    setReceiptLimits((prevLimits) => ({ ...prevLimits, [type]: parseFloat(value) }));
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

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
        <div className="form-group">
          <label>Car Mileage Limit:</label>
          <input
            className="input-field"
            type="number"
            step="0.01"
            value={carMileageLimit}
            onChange={(e) => setCarMileageLimit(e.target.value)}
          />
        </div>
        <div className="receipt-limits-form">
          {Object.values(Type).map((type) => (
            <div key={type} className="form-group">
              <label>{type} Limit:</label>
              <input
                className="input-field"
                type="number"
                step="0.01"
                value={receiptLimits[type] || ''}
                onChange={(e) => handleReceiptLimitChange(type, e.target.value)}
              />
            </div>
          ))}
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
