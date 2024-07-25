import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from "js-cookie";
import React, { useState } from 'react';
import './App.css';

const ChangeRole: React.FC = () => {
  const [username, setUsername] = useState('');
  const [newRole, setNewRole] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const adminEmail = Cookies.get('adminEmail') || '';

  const handleUsernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const handleNewRoleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewRole(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const user = {
      username: username,
      newRole: newRole,
      adminEmail: adminEmail,
    };

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

        <label htmlFor="username">Username:</label>
        <input type="text" id="username" value={username} onChange={handleUsernameChange} required />

        <label htmlFor="newRole">New Role:</label>
        <input type="text" id="newRole" value={newRole} onChange={handleNewRoleChange} required />

        {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        <button type="submit">Save</button>
      </form>
    </div>
  );
}

export default ChangeRole;
