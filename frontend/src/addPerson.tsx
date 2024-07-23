import './App.css';
import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const AddPerson: React.FC = () => {
  const [firstname, setFirstName] = useState('');
  const [lastname, setLastName] = useState('');
  const [userName, setusername] = useState('');
  const [userEmail, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [recoveryAnswer, setRecoveryAnswer] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleusernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setusername(event.target.value);
  };

  const handlefirstnameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFirstName(event.target.value);
  };

  const handlelastnameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setLastName(event.target.value);
  };

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handlerecoveryanswerChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRecoveryAnswer(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

      // Regex pattern to match email containing "@dal.ca"
      const emailPattern = /@dal\.ca$/;

      // Regex pattern to match password requirements
      const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.* ).{8,16}$/ //retrieved from: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a

      if (!emailPattern.test(userEmail)) {
        setErrorMessage('Email must be of domain @dal.ca');
        return;
      }
  
      if (!passwordPattern.test(password)) {
        setErrorMessage('Password must contain at least 8 characters, one upper case letter, one letter, one number and one special character');
        return;
      }
  
      // Clear error message if previous was set.
      setErrorMessage('');

      const user = {
        userName: userName,
        password: password,
        firstname: firstname,
        lastname: lastname,
        userEmail: userEmail,
        recoveryAnswer: recoveryAnswer,
      };
  
      console.log(user);

      try {
        const serverResponse = await fetch(`http://localhost:8080/api/users/addUserByAdmin`, {
          method: "POST",
          headers: {
            "Content-Type": 'application/json',
          },
          body: JSON.stringify(user),
        }) 
  
        if (serverResponse.ok) {
          const result = await serverResponse.text();
          console.log('Success:', result);
          alert("User added successfully!");
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
      <h1>Add new person/user</h1>
      <form onSubmit={handleSubmit}>

        <label htmlFor="firstname">First Name:</label>
        <input type="text" id="firstname" value={firstname} onChange={handlefirstnameChange} required/>

        <label htmlFor="lastname">Last Name:</label>
        <input type="text" id="lastname" value={lastname} onChange={handlelastnameChange} required/>

        <label htmlFor="userName">Username:</label>
        <input type="text" id="userName" value={userName} onChange={handleusernameChange} required/>

        <label htmlFor="email">Email:</label>
        <input type="email" id="userEmail" value={userEmail} onChange={handleEmailChange} required/>

        <label htmlFor="password">Temporary Password:</label>
        <input type="password" id="password" value={password} onChange={handlePasswordChange} required/>

        <label htmlFor="recoveryAnswer">Recovery Answer:</label>
        <input type="text" id="recoveryAnswer" value={recoveryAnswer} onChange={handlerecoveryanswerChange} required/>

        {errorMessage && <p style={{color: 'red'}}>{errorMessage}</p>}
        <button type="submit">Save</button>
      </form>
    </div>
  );
}

export default AddPerson;
