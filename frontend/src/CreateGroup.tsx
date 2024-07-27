import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Switch from 'react-switch';
import Cookies from "js-cookie";

const CreateGroup: React.FC = () => {
  const [groupName, setGroupName] = useState("");
  const [isPublic, setIsPublic] = useState(true);
  const currentUserId = Cookies.get("userId");

  const navigate = useNavigate();

  const handleGroupNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setGroupName(e.target.value);
  };

  const handlePublicToggleChange = (checked: boolean) => {
    setIsPublic(checked);
    console.log("Is Public:", checked);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("Group Name:", groupName);
    console.log("IsPublic:", isPublic);

    const newGroup = { groupName, public: isPublic };
    console.log("NewGroup:", newGroup)
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

      console.log("Created Group:", group);

      // Automatically join the group
      const joinResponse = await fetch(
        `http://localhost:8080/api/groups/${group.id}/join`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ userId: currentUserId }),
        }
      );

      if (!joinResponse.ok) {
        alert("Error joining group");
      } else {
        console.log("Joined Group:", await joinResponse.json());
        navigate(`/group/${group.id}`);
      }
    }
  };

  return (
    <div className="container mt-4 p-5 search-container">
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
        <div className="mb-3 d-flex align-items-center">
          <label htmlFor="isPublic" className="me-2">Public: </label>
          <Switch
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
