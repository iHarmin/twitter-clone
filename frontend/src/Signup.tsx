import React, {useState} from 'react';
import './App.css';

const Signup: React.FC = () => {
  const [username, setusername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [recoveryAnswer, setRecoveryAnswer] = useState('');
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

  const handleRecoveryAnswerChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRecoveryAnswer(event.target.value);
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

    console.log('Username:', username);
    console.log('Email:', email);
    console.log('Password:', password);
    console.log('Recovery Answer:', recoveryAnswer);
  };

  // Create a user object
  const user = {
    firstName: null,
    lastName: null,
    userName: username,
    email: email,
    password: password,
    recoveryAnswer: recoveryAnswer,
    personalInterests: null,
    status: null
  };

  // Send the user object to the server
  fetch('http://localhost:8080/api/users/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(user),
  })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch((error) => {
      alert("Couldn't sign up. Please try again.");
      console.error('Error:', error);
    });


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
      <label htmlFor="recoveryAnswer">Recovery Question: What is your
        favourite movie?</label>
      <input
        type="text"
        id="recoveryAnswer"
        value={recoveryAnswer}
        onChange={handleRecoveryAnswerChange}
        required
      />
      {errorMessage && <p style={{color: 'red'}}>{errorMessage}</p>}
      <button type="submit">Sign Up</button>
    </form>
  </div>
);
}

export default Signup;
