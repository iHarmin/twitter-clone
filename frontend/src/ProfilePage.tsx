import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function ProfilePage() {

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    userName: '',
    email: '',
    interests: '',
    status: 'Online 🟢' // default status
  });

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [interests, setInterests] = useState('');
  const [status, setStatus] = useState('Online 🟢');

  const handleDetailsSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    setFormData({
      ...formData,
      firstName: firstName,
      lastName: lastName,
      email: email,
      interests: interests,
    });


    try {
      // Send the form data to the server
      const serverResponse = await fetch('http://localhost:8080/api/users/save', {
        method: "POST",
        headers: {
          "Content-Type": 'application/json',
        },
        body: JSON.stringify(formData)
      });

      const result = await serverResponse.json();
      console.log('Success:', result);
      alert('Profile Changes Saved!');
    } catch (error) {
      console.error(error);
      alert('An error occurred while saving your profile changes.');
    }
  };

  const handleStatusChange = async (event: React.FormEvent) => {
    event.preventDefault();
    setFormData({
      ...formData,
      status: status,
    });

    try {
      const serverResponse = await fetch('http://localhost:8080/api/users/save_status', {
        method: "POST",
        headers: {
          "Content-Type": 'application/json',
        },
        body: JSON.stringify(formData)
      });

      const result = await serverResponse.json();
      console.log('Success:', result);
      alert('Status Changes Saved!');
    } catch (error) {
      console.error(error);
      alert('An error occurred while saving your status changes.');
    }
  }

  // Form for entering name, email, and interests
  // Username cannot be changed
  return (
    <>
      <div>
        <h2>My Profile</h2>
        <p>{formData.firstName} {formData.lastName}</p>
        <p>{formData.userName}</p>
        <p>{formData.email}</p>
        <p>{formData.interests}</p>
        <p>{formData.status}</p>
      </div>

      <form onSubmit={handleDetailsSubmit}>
        <label>
          First Name:
          <input type="text" value={firstName}
                 onChange={e => setFirstName(e.target.value)}
                 className="form-control"/>
        </label>
        <label>
          Last Name:
          <input type="text" value={lastName}
                 onChange={e => setLastName(e.target.value)}
                 className="form-control"/>
        </label>
        <label>
          Email:
          <input type="email" value={email}
                 onChange={e => setEmail(e.target.value)}
                 className="form-control"/>
        </label>
        <label>
          Interests:
          <input type="text" value={interests}
                 onChange={e => setInterests(e.target.value)}
                 className="form-control"/>
        </label>
        <input type="submit" value="Submit" className="btn btn-primary"/>
      </form>

      <form onSubmit={handleStatusChange}>
        <label>
          Status:
          <select value={status} onChange={e => setStatus(e.target.value)} className="form-control">
            <option value="Online 🟢">Online</option>
              <option value="Offline ⚪">Offline</option>
            <option value="Busy 🔴">Busy</option>
          </select>
        </label>
        <input type="submit" value="Change Status" className="btn btn-primary"/>
      </form>
    </>
  );
}

export default ProfilePage;
