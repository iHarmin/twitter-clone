import { useState } from 'react';
import './App.css';

const ForgotPassword: React.FC = () => {
  const [email, setEmail] = useState('');
  const [recoveryQuestion, setRecoveryQuestion] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handleRecoveryQuestionChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRecoveryQuestion(event.target.value);
  };

  const handleNewPasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNewPassword(event.target.value);
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    
    if (email && recoveryQuestion && newPassword) {
      
      setMessage(`Password reset successfully for ${email}`);
    } else {
      setMessage('Please fill in all fields');
    }
  };

  return (
    <div className="container">
      <h1>Forgot Password</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={handleEmailChange}
          required
        />
        <label htmlFor="recoveryQuestion">Enter Your Recovery Question:</label>
        <input
          type="text"
          id="recoveryQuestion"
          value={recoveryQuestion}
          onChange={handleRecoveryQuestionChange}
          required
        />
        <label htmlFor="newPassword">New Password:</label>
        <input
          type="password"
          id="newPassword"
          value={newPassword}
          onChange={handleNewPasswordChange}
          required
        />
        <button type="submit">Reset Password</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
}

export default ForgotPassword;
