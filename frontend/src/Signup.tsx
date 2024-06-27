import React, { useState } from 'react';
import './App.css';

const Signup: React.FC = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [recoveryQuestion, setRecoveryQuestion] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleFirstNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setLastName(event.target.value);
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

    if (!emailPattern.test(email)) {
      setErrorMessage('Email must be of domain @dal.ca');
      return;
    }

    // Clear error message if previous was set
    setErrorMessage('');

    console.log('First Name:', firstName);
    console.log('Last Name:', lastName);
    console.log('Email:', email);
    console.log('Password:', password);
    console.log('Recovery Question:', recoveryQuestion);

    
  };

  return (
    <div className="container">
      <h1>Twitter 2 Signup</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="firstName">First Name:</label>
        <input
          type="text"
          id="firstName"
          value={firstName}
          onChange={handleFirstNameChange}
          required
        />
        <label htmlFor="lastName">Last Name:</label>
        <input
          type="text"
          id="lastName"
          value={lastName}
          onChange={handleLastNameChange}
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
