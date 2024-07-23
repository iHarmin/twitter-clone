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
}

export default AddPerson;
