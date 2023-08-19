import React, { useState, useEffect } from 'react';
import './AddReimbursement.css'; 
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function AddReimbursement() {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [distanceDriven, setDistanceDriven] = useState('');
  const [receipts, setReceipts] = useState([]);
  const [receiptType, setReceiptType] = useState('');
  const [receiptPrice, setReceiptPrice] = useState('');
  const [availableTypes, setAvailableTypes] = useState([]);

  useEffect(() => {
    fetchTypes();
  }, []);

  const formatDateString = (dateString) => {
    const date = new Date(dateString);
    const formattedDate = date.toISOString().slice(0, 10)+"T00:00";
    return formattedDate;
  };

  const fetchTypes = async () => {
    try {
      const response = await fetch('http://localhost:8000/enduser/getTypes');
      if (response.ok) {
        const typesData = await response.json();
        setAvailableTypes(typesData);
      } else {
        console.error('Error fetching types:', response.statusText);
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  const handleAddReceipt = () => {
    if (!receiptType || !receiptPrice) {
      alert('Please enter both receipt type and price.');
      return;
    }
    
    if(receiptPrice<0) {
      alert('Price should be greater than 0');
    }

    const newReceipt = {
      type: receiptType,
      price: parseFloat(receiptPrice),
    };
    setReceipts([...receipts, newReceipt]);
    setReceiptType('');
    setReceiptPrice('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (!firstName || !lastName || !startDate || !endDate || !distanceDriven || receipts.length === 0) {
      alert('Please fill in all fields and add at least one receipt.');
      return;
    }

    const formattedStartDate = formatDateString(startDate);
    const formattedEndDate = formatDateString(endDate);

    const reimbursementData = {
      firstName,
      lastName,
      startDate: formattedStartDate,
      endDate: formattedEndDate,
      distanceDriven,
      receipts, 
    };

  
    try {
      const response = await fetch('http://localhost:8000/enduser/createReimbursement', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(reimbursementData),
      });
  
      if (response.ok) {
        toast.success('Reimbursement has been added. Total Value for Reibursment: '+await  response.text(), {
          position: toast.POSITION.TOP_CENTER,
        });
        setFirstName('');
        setLastName('');
        setStartDate('');
        setEndDate('');
        setDistanceDriven('');
        setReceipts([]);
        setReceiptType('');
        setReceiptPrice('');
      } else {
        toast.error(await  response.text());
        console.error('Error adding Reimbursement:', response.statusText);
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  return (
    <div className="home-container">
      <h2>Add a Reimbursement</h2>
      <ToastContainer />
      <form className="form-container" onSubmit={handleSubmit}>
        <div className="form-group">
          <label>First Name:</label>
          <input
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
        </div>
        <div className="form-group">
        <label>Last Name:</label>
        <input
          type="text"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label>Start Date:</label>
        <input
          type="date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label>End Date:</label>
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label>Distance Driven:</label>
        <input
          type="number"
          step="0.01"
          value={distanceDriven}
          onChange={(e) => setDistanceDriven(e.target.value)}
        />
      </div>
        <div className="receipts-container">
          <h3>Receipts</h3>
          <ul className="receipts-list">
            {receipts.map((receipt, index) => (
              <li key={index}>
                <span>{receipt.type}: ${receipt.price.toFixed(2)}</span>
              </li>
            ))}
          </ul>
          <div>
            <select
              value={receiptType}
              onChange={(e) => setReceiptType(e.target.value)}
            >
              <option value="">Select Receipt Type</option>
              {availableTypes.map((type) => (
                <option key={type} value={type}>
                  {type}
                </option>
              ))}
            </select>
            <input
              type="number"
              step="0.01"
              placeholder="Price"
              value={receiptPrice}
              onChange={(e) => setReceiptPrice(e.target.value)}
            />
            <button
              type="button"
              className="action-button"
              onClick={handleAddReceipt}
            >
              Add Receipt
            </button>
          </div>
        </div>
        <button type="submit" className="action-button">Submit</button>
      </form>
    </div>
  );
}

export default AddReimbursement;

