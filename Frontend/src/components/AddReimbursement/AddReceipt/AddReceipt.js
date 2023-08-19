import React, { useState, useEffect } from 'react';
import validator from 'validator';

function AddReceipt({ onAddReceipt }) {
  const [type, setType] = useState('');
  const [price, setPrice] = useState('');
  const [types, setTypes] = useState([]);

  useEffect(() => {
    fetchTypes();
  }, []);

  const fetchTypes = async () => {
    try {
      const response = await fetch('http://localhost:8000/enduser/getTypes');
      if (response.ok) {
        const typesData = await response.json();
        setTypes(typesData);
      } else {
        console.error('Error fetching types:', response.statusText);
      }
    } catch (error) {
      console.error('An error occurred:', error);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validator.isFloat(price, { min: 0 })) {
      const newReceipt = { type, price: parseFloat(price) };
      onAddReceipt(newReceipt);
      setType('');
      setPrice('');
    } else {
      alert('Invalid price. Please enter a valid positive number.');
    }
  };

  return (
    <div>
      <h2>Add Receipt</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Type:</label>
          <select value={type} onChange={(e) => setType(e.target.value)}>
            <option value="">Select Type</option>
            {types.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Price:</label>
          <input type="text" value={price} onChange={(e) => setPrice(e.target.value)} />
        </div>
        <button type="submit">Add Receipt</button>
      </form>
    </div>
  );
}

export default AddReceipt;
