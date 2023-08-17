import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './adminDashboard.css'; 

function AdminDashboard() {
  const [reimbursements, setReimbursements] = useState([]);

  useEffect(() => {
 
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8000/enduser/displayReimbursements');
        if (response.ok) {
          const data = await response.json();
          setReimbursements(data);
        } else {
          console.error('Error fetching reimbursements:', response.statusText);
        }
      } catch (error) {
        console.error('An error occurred:', error);
      }
    };

    fetchData();
  }, []);


  const calculateTotal = (receipts) => {
    return receipts.reduce((total, receipt) => total + receipt.price, 0);
  };

  return (
    <div className="admin-dashboard"> 
      <h2>Admin Dashboard</h2>
      <Link to="/changeRates" className="change-rates-link">Change Rates</Link> 
      <table className="reimbursements-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Receipts</th>
            <th>Distance Driven</th>
            <th>Total</th>
          </tr>
        </thead>
        <tbody>
          {reimbursements.map((reimbursement) => (
            <tr key={reimbursement.id}>
              <td>{reimbursement.id}</td>
              <td>{reimbursement.firstName}</td>
              <td>{reimbursement.lastName}</td>
              <td>{new Date(...reimbursement.startDate).toLocaleDateString()}</td>
              <td>{new Date(...reimbursement.endDate).toLocaleDateString()}</td>
              <td>
                <ul>
                  {reimbursement.receipts.map((receipt) => (
                    <li key={receipt.id}>{receipt.type}: ${receipt.price.toFixed(2)}</li>
                  ))}
                </ul>
              </td>
              <td>{reimbursement.distanceDriven}</td>
              <td>${calculateTotal(reimbursement.receipts).toFixed(2)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminDashboard;
