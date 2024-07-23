import {
  useEffect,
  useState,
} from 'react';

import { useParams } from 'react-router-dom';

const GroupDetail: React.FC = () => {
  // TODO: This was a super quick/gross layout for a 'group detail' page (also Bootstrap doesn't seem to be working...)
  // TODO: Add requests/add approve requests if user 'canApproveRequests' (find current user in group.members, see if they have the flag)
  const params = useParams();

  const [group, setGroup] = useState(null);

  const getGroupById = (id: number) => {
    fetch(`http://localhost:8080/api/groups/${id}`).then(async (response) =>
      setGroup(await response.json())
    );
  };

  useEffect(() => {
    if (params.id) {
      // get group from backend
      getGroupById(Number(params.id));
    }
  }, [params.id]);

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
                {member.user.status == "Online" && <span className="badge badge-success badge-pill">Online</span>}
              </li>
            ))}
          </ul>
        </div>
      </div>
    )
  );
};

export default GroupDetail;
