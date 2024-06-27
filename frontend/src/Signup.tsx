import React, { useState } from 'react';
import './App.css';

const Signup: React.FC = () => {
  const [username, setusername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [recoveryQuestion, setRecoveryQuestion] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleusernameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setusername(event.target.value);
  };

 

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleRecoveryQuestionChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRecoveryQuestion(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    // Regex pattern to match email containing "@dal.ca"
    const emailPattern = /@dal\.ca$/;
    const passwordPattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.* ).{8,16}$/ //retrieved from: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a

    if (!emailPattern.test(email)) {
      setErrorMessage('Email must be of domain @dal.ca');
      return;
    }

    if (!passwordPattern.test(password)) {
      setErrorMessage('Password must contain at least 8 characters, one upper case letter, one letter, one number and one special character');
      return;
    }

    // Clear error message if previous was set
    setErrorMessage('');

    console.log('First Name:', username);
    console.log('Email:', email);
    console.log('Password:', password);
    console.log('Recovery Question:', recoveryQuestion);

    
  };

  return (
    <div className="container">
      <h1>Twitter 2 Signup</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          value={username}
          onChange={handleusernameChange}
          required
        />    
   
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={handleEmailChange}
          required
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={handlePasswordChange}
          required
        />
        <label htmlFor="recoveryQuestion">Recovery Question: What is your favourite movie?</label>
        <input
          type="text"
          id="recoveryQuestion"
          value={recoveryQuestion}
          onChange={handleRecoveryQuestionChange}
          required
        />
        {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        <button type="submit">Sign Up</button>
      </form>
    </div>
  );
}

export default Signup;
