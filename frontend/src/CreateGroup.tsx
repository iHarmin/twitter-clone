import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const CreateGroup: React.FC = () => {
  const [groupName, setGroupName] = useState("");
  const [isPublic, setIsPublic] = useState(true);

  const navigate = useNavigate();

  const handleGroupNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setGroupName(e.target.value);
  };

  const handlePublicToggleChange = () => {
    setIsPublic(!isPublic);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("Group Name:", groupName);
    console.log("Is Public:", isPublic);

    const newGroup = { groupName, isPublic };
    const response = await fetch(
      "http://localhost:8080/api/groups/createGroup",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newGroup),
      }
    );

    if (!response.ok) {
      alert("Error creating group");
    } else {
      const group = await response.json();
      navigate(`/group/${group.id}`);
    }
  };

  return (
    <div>
      <h1>Create Group</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="groupName">Group Name:</label>
          <input
            type="text"
            id="groupName"
            value={groupName}
            onChange={handleGroupNameChange}
          />
        </div>
        <div>
          <label htmlFor="isPublic">Public:</label>
          <input
            type="checkbox"
            id="isPublic"
            checked={isPublic}
            onChange={handlePublicToggleChange}
          />
        </div>
        <button type="submit">Create Group</button>
      </form>
    </div>
  );
};

export default CreateGroup;
