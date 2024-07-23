import React, { useState } from 'react';

import { useNavigate } from 'react-router-dom';

const CreateGroup: React.FC = () => {
  const [groupName, setGroupName] = useState("");
  const [isPublic, setIsPublic] = useState(true);

  const navigate = useNavigate();

  const handleGroupNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setGroupName(e.target.value);
  };

  const handleToggleChange = () => {
    setIsPublic(!isPublic);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // just for testing.
    console.log("Group Name:", groupName);
    console.log("Is Public:", isPublic);

    // send fetch request to backend, expect a Group response
    const newGroup = { groupName, isPublic }; 
    // TODO: Probably make a nice TypeScript model to match the backend model instead of winging it with vanilla JS
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
      // response was error, pop an alert maybe?
    } else {
      console.log("we won"); // TODO: Probably modify this stuff
      const group = await response.json();
      // navigate to group detail after successful creation (add group.id to page)
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
            onChange={handleToggleChange}
          />
        </div>
        <button type="submit">Create Group</button>
      </form>
    </div>
  );
};

export default CreateGroup;
