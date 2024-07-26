import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const GroupDetail: React.FC = () => {
  const params = useParams();

  const [group, setGroup] = useState<any>(null);
  const [isMember, setIsMember] = useState<boolean>(false);

  const getGroupById = (id: number) => {
    fetch(`http://localhost:8080/api/groups/${id}`).then(async (response) =>
      setGroup(await response.json())
    );
  };

  const joinGroup = () => {
    const currentUserId = 1;
    fetch(`http://localhost:8080/api/groups/${params.id}/join`, {
      method: 'POST',
      body: JSON.stringify({ userId: currentUserId }),
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

  useEffect(() => {
    if (params.id) {
      // get group from backend
      getGroupById(Number(params.id));
    }
  }, [params.id]);

  useEffect(() => {
    if (group) {
      // Check if the current user is already a member
      const currentUserId = 1;
      setIsMember(group.members.some((member) => member.user.id === currentUserId));
    }
  }, [group]);

  return (
    group && (
      <div className="card">
        <div className="card-body">
          <div className="card-header">{group.groupName} Members</div>
          <ul className="list-group">
            {group.members.map((member) => (
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
          {!isMember && (
            <button className="btn btn-primary mt-3" onClick={joinGroup}>
              Join Group
            </button>
          )}
        </div>
      </div>
    )
  );
};

export default GroupDetail;
