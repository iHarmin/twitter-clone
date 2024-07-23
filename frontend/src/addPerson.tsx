import './App.css';
import React, {useContext, useEffect, useState} from 'react';

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
  
  };
}

export default AddPerson;
