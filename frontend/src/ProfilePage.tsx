import {useContext, useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useParams} from 'react-router-dom';
import {AuthContext} from "./AuthContext.tsx";
import { useCookies } from "react-cookie";

const ProfilePage: React.FC = () => {

  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    userName: '',
    email: '',
    interests: '',
    status: '' // default status
  });

  const {profileID} = useParams();
  // TODO: placeholder for current username, will use cookie to obtain
  const currentUserID : string = '30';
  const {isLoggedIn} = useContext(AuthContext);
  const [cookies] = useCookies(['user']);
  // TODO: placeholders for friend adding
  // const loggedInUser = "profileUser";
  // const profileUser = "profileUser";

  const handleAddFriend = async () => {
    // try {
    //   const userID = cookies.user;
    //   const serverResponse = await fetch(`http://localhost:8080/api/users/${userID}/friends/${friendID}`, {
    //     method: 'POST',
    //     headers: {
    //       'Content-Type': 'application/json',
    //     },
    //     body: JSON.stringify({ friendUsername: username }), // Send the username of the friend to be added
    //   });
    //
    //   if (serverResponse.ok) {
    //     alert('Friend request sent!');
    //   } else {
    //     alert('An error occurred while sending the friend request.');
    //   }
    // } catch (error) {
    //   console.error(error);
    //   alert('An error occurred while sending the friend request.');
    // }
  };


  // Fetch the user's profile data
  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const serverResponse = await fetch(`http://localhost:8080/api/users/${profileID}`);
        const profileData = await serverResponse.json();
        console.log(profileData);
        setFormData(profileData);
      } catch (error) {
        console.error(error);
        alert('An error occurred while fetching your profile data.');
      }
    };

    fetchProfileData();
  }, []);

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [interests, setInterests] = useState('');
  const [status, setStatus] = useState('');
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
      const serverResponse = await fetch(`http://localhost:8080/api/users/${currentUserID}/information`, {
        method: "PUT",
        headers: {
          "Content-Type": 'application/json',
        },
        body: JSON.stringify({
          firstName: firstName,
          lastName: lastName,
          email: email,
          interests: interests
        })
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
      const serverResponse = await fetch(`http://localhost:8080/api/users/${currentUserID}/status`, {
        method: "POST",
        headers: {
          "Content-Type": 'application/json',
        },
        body: JSON.stringify(status)
      });

      console.log(serverResponse);
      console.log(status);
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

          {isLoggedIn && loggedInUser !== profileUser && (
            <button onClick={handleAddFriend}>Add Friend</button>
          )}

          {profileID === currentUserID && (isFirstVisit || isEditingProfile) && (
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

          {profileID === currentUserID && !isFirstVisit && !isEditingProfile && (
            <button onClick={() => setIsEditingProfile(true)}
                    className="btn btn-primary">Edit Profile</button>
          )}

          {profileID === currentUserID && (
            <form onSubmit={handleStatusChange} className="mt-5">
              <label>
                Status:
                <select value={status} onChange={e => setStatus(e.target.value)}
                        className="form-control mb-3">
                  <option value="" disabled>Click to select your status</option>
                  <option value="Online">Online 🟢</option>
                  <option value="Offline">Offline ⚪</option>
                  <option value="Busy">Busy 🔴</option>
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
