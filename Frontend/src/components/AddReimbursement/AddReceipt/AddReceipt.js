import React, { useState } from 'react';

function AddReceipt({ onAddReceipt }) {
  const [type, setType] = useState('');
  const [price, setPrice] = useState('');

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
          <input type="text" value={type} onChange={(e) => setType(e.target.value)} />
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
