import './App.css';
import React, {useContext, useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const RemovePerson: React.FC = () => {
    const [userEmail, setEmail] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        const user = {
            userEmail: userEmail,
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
    
}

export default RemovePerson;