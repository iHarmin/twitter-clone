import {useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useParams} from 'react-router-dom';

function ProfilePage() {

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    userName: '',
    email: '',
    interests: '',
    status: 'Online 🟢' // default status
  });

  const {id} = useParams();
  // TODO: placeholder for current username
  const currentUser = 'currentName';

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const serverResponse = await fetch(`http://localhost:8080/api/users/${id}`);
        const profileData = await serverResponse.json();
        console.log(profileData);
        setFormData(profileData);
      } catch (error) {
        console.error(error);
        alert('An error occurred while fetching your profile data.');
      }
    };

    fetchProfileData();
  }, [id]);

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [interests, setInterests] = useState('');
  const [status, setStatus] = useState('Online 🟢');
  const [isFirstVisit, setIsFirstVisit] = useState(true);
  const [isEditingProfile, setIsEditingProfile] = useState(false);

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

    // Set to false after the user has entered their details
    setIsFirstVisit(false);
    setIsEditingProfile(false);
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
    <div className="container-fluid p-3">
      <div className="row">
        <div className="col">
          <div className="container">
            <div className="row">
              <div className="col">
                <p>{formData.firstName} {formData.lastName}</p>
                <p>{formData.userName}</p>
                <p>{formData.email}</p>
                <p>{formData.interests}</p>
                <p>{formData.status}</p>
              </div>
            </div>
          </div>

          {username === currentUser && (isFirstVisit || isEditingProfile) && (
            <div className="row">
              <div className="col">
                <h3>Please enter your personal information:</h3>
                <form onSubmit={handleDetailsSubmit}>
                  <div className="form-group mb-3">
                    <label> First Name: </label>
                    <input type="text" value={firstName}
                           onChange={e => setFirstName(e.target.value)}
                           className="form-control"/>
                  </div>
                  <div className="form-group mb-3">
                    <label> Last Name: </label>
                    <input type="text" value={lastName}
                           onChange={e => setLastName(e.target.value)}
                           className="form-control"/>
                  </div>
                  <div className="form-group mb-3">
                    <label> Email: </label>
                    <input type="email" value={email}
                           onChange={e => setEmail(e.target.value)}
                           className="form-control"/>
                  </div>
                  <div className="form-group mb-3">
                    <label> Interests: </label>
                    <input type="text" value={interests}
                           onChange={e => setInterests(e.target.value)}
                           className="form-control"/>
                  </div>
                  <input type="submit" value="Submit"
                         className="btn btn-primary"/>
                </form>
              </div>
            </div>
          )}

          {username === currentUser && !isFirstVisit && !isEditingProfile && (
            <button onClick={() => setIsEditingProfile(true)}
                    className="btn btn-primary">Edit Profile</button>
          )}

          {username === currentUser && (
            <form onSubmit={handleStatusChange} className="mt-5">
              <label>
                Status:
                <select value={status} onChange={e => setStatus(e.target.value)}
                        className="form-control mb-3">
                  <option value="Online 🟢">Online</option>
                  <option value="Offline ⚪">Offline</option>
                  <option value="Busy 🔴">Busy</option>
                </select>
              </label>
              <input type="submit" value="Change Status"
                     className="btn btn-primary mt-3"/>
            </form>
          )}
        </div>
      </div>
    </div>
  );
}

export default ProfilePage;
