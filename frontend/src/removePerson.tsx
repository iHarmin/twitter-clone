import './App.css';
import React, {useContext, useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from "js-cookie";

const RemovePerson: React.FC = () => {
    const [userEmail, setEmail] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const adminEmail = Cookies.get('adminEmail') || '';

    const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const user = {
            userEmail: userEmail,
            adminEmail: adminEmail,
        };

        console.log(user);

        try {
            const serverResponse = await fetch(`http://localhost:8080/api/users/removeUserByAdmin`, {
              method: "POST",
              headers: {
                "Content-Type": 'application/json',
              },
              body: JSON.stringify(user),
            }) 
      
            if (serverResponse.ok) {
              const result = await serverResponse.text();
              console.log('Success:', result);
              alert("User deleted successfully!");
            } else {
              throw new Error('Server response was not OK!');
            }
          } catch (error) {
            console.error(error);
            alert('An error occurred while adding new person.');
          }
    };

    return (
        <div className="container">
          <h1>Remove person/user</h1>
          <form onSubmit={handleSubmit}>
    
            <label htmlFor="email">Existing user's email:</label>
            <input type="email" id="userEmail" value={userEmail} onChange={handleEmailChange} required/>
    
            {errorMessage && <p style={{color: 'red'}}>{errorMessage}</p>}
            <button type="submit">Save</button>
          </form>
        </div>
      );
}

export default RemovePerson;