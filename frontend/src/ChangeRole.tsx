import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from "js-cookie";
import React, { useState } from 'react';
import './App.css';

const ChangeRole: React.FC = () => {
  const [userEmail, setEmail] = useState('');
  const [newRole, setNewRole] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const adminEmail = Cookies.get('adminEmail') || '';

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
};

  const handleNewRoleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewRole(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const user = {
      userEmail: userEmail,
      newRole: newRole,
      adminEmail: adminEmail,
    };

    console.log(user);

    try {
      const serverResponse = await fetch(`http://localhost:8080/api/users/changeUserRoleByAdmin`, {
        method: "POST",
        headers: {
          "Content-Type": 'application/json',
        },
        body: JSON.stringify(user),
      });

      if (serverResponse.ok) {
        const result = await serverResponse.text();
        console.log('Success:', result);
        alert("User role changed successfully!");
      } else {
        throw new Error('Server response was not OK!');
      }
    } catch (error) {
      console.error(error);
      alert('An error occurred while changing user role.');
    }
  };

  return (
    <div className="container">
      <h1>Change User Role</h1>
      <form onSubmit={handleSubmit}>

        <label htmlFor="email">Existing user's email:</label>
        <input type="email" id="userEmail" value={userEmail} onChange={handleEmailChange} required/>

        <label htmlFor="newRole">New Role (Employee OR Student):</label>
        <input type="text" id="newRole" value={newRole} onChange={handleNewRoleChange} required />

        {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        <button type="submit">Save</button>
      </form>
    </div>
  );
}

export default ChangeRole;
