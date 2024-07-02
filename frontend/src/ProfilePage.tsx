import {useContext, useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {useParams} from 'react-router-dom';
import {AuthContext} from "./AuthContext.tsx";
import Cookies from "js-cookie";

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
  const currentUserID: string = Cookies.get('userId');
  console.log("Current User ID: ", currentUserID);
  // const currentUserID: string = '11';

  // const {isLoggedIn} = useContext(AuthContext);

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [interests, setInterests] = useState('');
  const [status, setStatus] = useState('');
  const [isFirstVisit, setIsFirstVisit] = useState(true);
  const [isEditingProfile, setIsEditingProfile] = useState(false);
  const [friends, setFriends] = useState([]);
  const [friendRequests, setFriendRequests] = useState([]);
  const [friendRequestSent, setFriendRequestSent] = useState(false);

  // Fetch the user's profile data
  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const serverResponse = await fetch(`http://localhost:8080/api/users/${profileID}`);
        const profileData = await serverResponse.json();
        console.log("Profile Data", profileData);
        setFormData(profileData);
      } catch (error) {
        console.error(error);
        alert('An error occurred while fetching your profile data.');
      }
    };

    fetchProfileData();
  }, []);

  // Fetch the list of friends and friend requests
  useEffect(() => {
    const fetchFriendsAndRequests = async () => {
      try {
        const friendsResponse = await fetch(`http://localhost:8080/api/friends/${profileID}`);
        const friendsData = await friendsResponse.json();
        setFriends(friendsData.map(friendship => ({
          userID: friendship.user2.id,
          userName: friendship.user2.userName,
          firstName: friendship.user2.firstName,
          lastName: friendship.user2.lastName,
          email: friendship.user2.email,
        })));
        console.log("My friends", friendsData);

        const requestsResponse = await fetch(`http://localhost:8080/api/friends/${profileID}/friendRequests`);
        const requestsData = await requestsResponse.json();
        console.log("Friend Requests", requestsData);

        const filteredRequests = requestsData
          .filter(request => request.user2 && request.user2.id === Number(currentUserID) && Number(profileID) === Number(currentUserID))
          .map(request => {
            return {
              id: request.friendID,
              from: {
                id: request.user1.id,
                userName: request.user1.userName,
                firstName: request.user1.firstName,
                lastName: request.user1.lastName,
                email: request.user1.email,
              },
              to: {
                id: request.user2.id,
                userName: request.user2.userName,
                firstName: request.user2.firstName,
                lastName: request.user2.lastName,
                email: request.user2.email,
              }
            };
          });

        console.log("Filtered Friend Requests", filteredRequests);
        setFriendRequests(filteredRequests);


      } catch (error) {
        console.error(error);
        alert('An error occurred while fetching your friends and friend requests.');
      }
    };

    fetchFriendsAndRequests();
  }, []);

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
        method: "POST",
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

      if (serverResponse.ok) {
        const result = await serverResponse.text();
        console.log('Success:', result);
        alert("Successfully saved your profile changes!");
      } else {
        throw new Error('Server response was not ok.');
      }
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
        body: JSON.stringify({status: status})
      });

      if (serverResponse.ok) {
        const result = await serverResponse.text();
        console.log('Success:', result);
        alert("Successfully changed your status!");
      } else {
        throw new Error('Server response was not ok.');
      }
    } catch (error) {
      console.error(error);
      alert('An error occurred while saving your status changes.');
    }
  }

  const handleRemoveFriend = async (friendUserID) => {
    try {
      const serverResponse = await fetch(`http://localhost:8080/api/friends/${currentUserID}/friends/${friendUserID}`, {
        method: 'DELETE',
      });

      if (serverResponse.ok) {
        alert('Friend removed successfully!');
        // Remove the friend from the friends state
        setFriends(friends.filter(friend => friend.userID !== friendUserID));
      } else {
        throw new Error('Server response was not ok.');
      }
    } catch (error) {
      console.error(error);
      alert('An error occurred while removing the friend.');
    }
  };

  const handleAcceptRequest = async (friendUserID) => {
    try {
      const serverResponse = await fetch(`http://localhost:8080/api/friends/${currentUserID}/friends/${friendUserID}`, {
        method: 'PUT',
      });

      // Find the accepted friend request
      const acceptedRequest = friendRequests.find(request => request.from.id === friendUserID);

      // Add the accepted friend to the friends state
      setFriends([...friends, {
        userID: acceptedRequest.from.id,
        userName: acceptedRequest.from.userName,
        firstName: acceptedRequest.from.firstName,
        lastName: acceptedRequest.from.lastName,
      }]);

      if (serverResponse.ok) {
        alert('Friend request accepted successfully!');
        // Remove the request from the friendRequests state
        setFriendRequests(friendRequests.filter(request => request.from.id !== friendUserID));
      } else {
        throw new Error('Server response was not ok.');
      }
    } catch (error) {
      console.error(error);
      alert('Error: could not accept the friend request.');
    }
  };

  const handleDeclineRequest = async (friendUserID) => {
    try {
      const serverResponse = await fetch(`http://localhost:8080/api/friends/${currentUserID}/friends/${friendUserID}`, {
        method: 'DELETE',
      });

      if (serverResponse.ok) {
        alert('Friend request declined successfully!');
        // Remove the request from the friendRequests state
        setFriendRequests(friendRequests.filter(request => request.from.id !== friendUserID));
      } else {
        throw new Error('Server response was not ok.');
      }
    } catch (error) {
      console.error(error);
      alert('Error: could not decline the friend request.');
    }
  };

  const handleSendRequest = async (friendUserID) => {
    try {
      const serverResponse = await fetch(`http://localhost:8080/api/friends/${currentUserID}/friends/${friendUserID}`, {
        method: 'POST',
      });

      if (serverResponse.ok) {
        alert('Friend request sent successfully!');
        setFriendRequestSent(true);

        setFriendRequests([...friendRequests, {from: {id: friendUserID}}]);
      } else {
        throw new Error('Server response was not ok.');
      }
    } catch (error) {
      console.error(error);
      alert('Error: could not send the friend request.');
    }
  };


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

          {profileID !== currentUserID && (
            <button onClick={() => handleSendRequest(profileID)}
                    className="btn btn-primary" disabled={friendRequestSent}>
              {friendRequestSent ? 'Request Pending' : 'Add Friend'}
            </button>
          )}


          {Cookies.get('userId') === profileID && (
           
            <div className="row">
              <div className="col">
                <h3>Update your personal information:</h3>
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

          <h3>Friend List</h3>
          {friends.map(friend => (
            <div key={friend.userID}
                 className="list-group-item d-flex justify-content-between align-items-center">
              <div className="card">
                <div className="card-body">
                  <p className="mb-0">Username: {friend.userName}</p>
                  <p className="mb-0">Email: {friend.email}</p>
                  <p>Name: {friend.firstName} {friend.lastName}</p>
                  {profileID === currentUserID && (
                    <button
                      onClick={() => handleRemoveFriend(friend.userID)}
                      className="btn btn-danger">Remove Friend
                    </button>
                  )}
                </div>
              </div>
            </div>
          ))}

          {profileID === currentUserID && (
            <div>
              <h3>Friend Requests</h3>
              {/*{friendRequests.filter(request => request.to && request.to.id === currentUserID).map(request => (*/}
              {friendRequests.map(request => (
                <div key={request.from.id}
                     className="list-group-item d-flex justify-content-between align-items-center">
                  <div className="card">
                    <div className="card-body">
                      <p className="mb-0">Username: {request.from.userName}</p>
                      <p className="mb-0">Email: {request.from.email}</p>
                      <p>Name: {request.from.firstName} {request.from.lastName}</p>
                      <button
                        onClick={() => handleAcceptRequest(request.from.id)}
                        className="btn btn-primary me-3">Accept Request
                      </button>
                      <button
                        onClick={() => handleDeclineRequest(request.from.id)}
                        className="btn btn-danger">Decline Request
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default ProfilePage;
