import {useState} from 'react';
import Cookies from "js-cookie";
import {Link} from 'react-router-dom';

const Search = () => {
    const [searchTerm, setSearchTerm] = useState('');
    const [peopleResults, setPeopleResults] = useState([]);
    const [groupsResults, setGroupsResults] = useState([]);
    const [isSearchCLicked, setIsSearchCLicked] = useState(false);
    const [friendResults, setFriendResults] = useState([]);
    const currentUserID: string = Cookies.get('userId');

    const fetchPeople = async (searchTerm: string) => {
      try {
        const response = await fetch(`http://localhost:8080/api/users/search?searchTerm=${searchTerm}`);
        const data = await response.json();
        setPeopleResults(data);
        console.log("People:", peopleResults);
      } catch (error) {
        console.error('Error fetching people:', error);
      }
    };

    // Filter locally for friends
    const fetchFriends = async (searchTerm: string) => {
      try {
        const friendsResponse = await fetch(`http://localhost:8080/api/friends/${currentUserID}`);
        const friendsData = await friendsResponse.json();
        const mappedFriendsData = friendsData.map(friendship => ({
          id: friendship.user2.id,
          userName: friendship.user2.userName,
          firstName: friendship.user2.firstName,
          lastName: friendship.user2.lastName,
          email: friendship.user2.email,
          personalInterests: friendship.user2.personalInterests,
        }));

        const filteredFriendsData = searchTerm
          ? mappedFriendsData.filter(friend =>
            friend.userName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            friend.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
            friend.personalInterests.toLowerCase().includes(searchTerm.toLowerCase())
          )
          : mappedFriendsData;

        setFriendResults(filteredFriendsData);
        console.log("Friends:", friendResults);
      } catch
        (error) {
        console.error('Error fetching friends:', error);
      }
    }

    const fetchGroups = async (searchTerm: string) => {
      try {
        const response = await fetch(`http://localhost:8080/api/groups/search?searchTerm=${searchTerm}`);
        const data = await response.json();
        const mappedData = data.map(group => ({
          ...group,
          status: group.public ? "Public" : "Private"
        }));
        setGroupsResults(mappedData);
        console.log("Groups:", groupsResults);
      } catch (error) {
        console.error('Error fetching groups:', error);
      }
    };

    const handleSearchChange = (e) => {
      const searchTerm = e.target.value;
      setSearchTerm(searchTerm);
    };

    const handleSearchClick = async (e) => {
      e.preventDefault();
      setIsSearchCLicked(true);
      await fetchPeople(searchTerm);
      await fetchFriends(searchTerm);
      await fetchGroups(searchTerm);
    }

    return (
      <div className="container mt-4 p-5 search-container">
        <h1>Search</h1>
        <form className="form-inline" onSubmit={handleSearchClick}>
          <input className="form-control" type="search"
                 placeholder="Search by username, email, interests, or group"
                 value={searchTerm}
                 onChange={handleSearchChange}/>
          <button className="btn btn-outline-primary mb-4" type="submit">Search
          </button>
        </form>

        {isSearchCLicked && (
          <div className="row">
            <div className="col-md-6 mb-4">
              <h2>People</h2>
              <div>
                {peopleResults.length > 0 ? (
                  <ul className="list-group">
                    {peopleResults.map(user => (
                      <li key={user.id} className="list-group-item p-3">
                        <Link to={`/profile/${user.id}`}
                              className="text-decoration-none">
                          <div>
                            <strong>Username:</strong> {user.userName}
                          </div>
                          <div>
                            <strong>Email:</strong> {user.email}
                          </div>
                          <div>
                            <strong>Interests:</strong> {user.personalInterests}
                          </div>
                        </Link>
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p>No people found matching search criteria.</p>
                )}
              </div>
            </div>

            <div className="col-md-6 mb-4">
              <h2>My Friends</h2>
              <div>
                {friendResults.length > 0 ? (
                  <ul className="list-group">
                    {friendResults.map(friend => (
                      <li key={friend.id} className="list-group-item p-3">
                        <Link to={`/profile/${friend.id}`}
                              className="text-decoration-none">
                          <div>
                            <strong>Username:</strong> {friend.userName}
                          </div>
                          <div>
                            <strong>Email:</strong> {friend.email}
                          </div>
                          <div>
                            <strong>Interests:</strong> {friend.personalInterests}
                          </div>
                        </Link>
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p>No friends found matching the search criteria.</p>
                )}
              </div>
            </div>

            <div className="col-md-6">
              <h2>Groups</h2>
              <div className="p-3 mb-2">
                {groupsResults.length > 0 ? (
                  <ul className="list-group">
                    {groupsResults.map(group => (
                      <li key={group.id} className="list-group-item p-3">
                        <Link to={`/group/${group.id}`}
                              className="text-decoration-none">
                          <div>
                            <strong>Name:</strong> {group.groupName}
                          </div>
                          <div>
                            <strong>Status:</strong> {group.status}
                          </div>
                        </Link>
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p>No groups found matching the search criteria.</p>
                )}
              </div>
            </div>

            <div className="col-md-6">
              <h2>My Groups</h2>
              <div className="p-3 mb-2">
                {groupsResults.length > 0 ? (
                  <ul className="list-group">
                    {groupsResults.map(group => (
                      <li key={group.id} className="list-group-item p-3">
                        <Link to={`/group/${group.id}`}
                              className="text-decoration-none">
                          <div>
                            <strong>Name:</strong> {group.groupName}
                          </div>
                          <div>
                            <strong>Status:</strong> {group.status}
                          </div>
                        </Link>
                      </li>
                    ))}
                  </ul>
                ) : (
                  <p>No groups found matching the search criteria.</p>
                )}
              </div>
            </div>

          </div>
        )}
      </div>
    );
  }
;
export default Search;
