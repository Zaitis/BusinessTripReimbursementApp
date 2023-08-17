import React, { useState } from 'react';
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

  const handleAddReceipt = () => {
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
  
    const reimbursementData = {
      firstName,
      lastName,
      startDate,
      endDate,
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
        toast.success('Reimbursement was been added.', {
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
          type="datetime-local"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label>End Date:</label>
        <input
          type="datetime-local"
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
          <input
            type="text"
            placeholder="Type"
            value={receiptType}
            onChange={(e) => setReceiptType(e.target.value)}
          />
          <input
            type="number"
            step="0.01"
            placeholder="Price"
            value={receiptPrice}
            onChange={(e) => setReceiptPrice(e.target.value)}
          />
          <button type="button" className="action-button" onClick={handleAddReceipt}>
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
