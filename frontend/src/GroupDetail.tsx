import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import Cookies from "js-cookie";

const GroupDetail: React.FC = () => {
  const params = useParams();

  const [group, setGroup] = useState<any>(null);
  const [isMember, setIsMember] = useState<boolean>(false);
  const currentUserId = Cookies.get('userId');

  const getGroupById = (id: number) => {
    fetch(`http://localhost:8080/api/groups/${id}`).then(async (response) =>
      setGroup(await response.json())
    );
  };

  const joinGroup = () => {
    const requestBody = {userId: currentUserId};
    console.log("request body:", requestBody);
    fetch(`http://localhost:8080/api/groups/${params.id}/join`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({userId: currentUserId}),
    }).then(async (response) => {
      if (response.ok) {
        const updatedGroup = await response.json();
        setGroup(updatedGroup);
        setIsMember(true);
      } else {
        console.error('Failed to join group, hopefully you are not seeing this.');
      }
    });
  };

  const leaveGroup = () => {
    const requestBody = {userId: currentUserId};
    console.log('Request Body:', requestBody);

    fetch(`http://localhost:8080/api/groups/${params.id}/leave`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody),
    }).then(async (response) => {
      if (response.ok) {
        const updatedGroup = await response.json();
        setGroup(updatedGroup);
        setIsMember(false);
      } else {
        console.error('Failed to leave group');
      }
    });
  };

  const checkUserMembership = (groupId: number, userId: string) => {
    fetch(`http://localhost:8080/api/groups/${groupId}/isUserInGroup?userId=${userId}`)
      .then(async (response) => {
        if (response.ok) {
          const isInGroup = await response.json();
          setIsMember(isInGroup);
        } else {
          console.error('Failed to check user membership');
        }
      });
  };

  useEffect(() => {
    if (params.id) {
      // get group from backend
      getGroupById(Number(params.id));
      // check if user is a member
      checkUserMembership(Number(params.id), currentUserId);
    }
  }, [params.id]);

  return (
    group && (
      <div className="card">
        <div className="card-body">
          <div className="card-header">{group.groupName} Members</div>
          <ul className="list-group">
            {group.members.map((member: any) => (
              <li
                key={member.id}
                className="list-group-item d-flex justify-content-between align-items-center"
              >
                {member.user.firstName} {member.user.lastName}
                {member.user.status === "Online" && (
                  <span className="badge badge-success badge-pill">Online</span>
                )}
              </li>
            ))}
          </ul>
          {!isMember ? (
            <button className="btn btn-primary mt-3" onClick={joinGroup}>
              Join Group
            </button>
          ) : (
            <button className="btn btn-danger mt-3" onClick={leaveGroup}>
              Remove
            </button>
          )}
        </div>
      </div>
    )
  );
};

export default GroupDetail;
