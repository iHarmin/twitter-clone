import React, { useState } from 'react';

const CreateGroup: React.FC = () => {
  const [groupName, setGroupName] = useState('');
  const [isPublic, setIsPublic] = useState(true);

  const handleGroupNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setGroupName(e.target.value);
  };

  const handleToggleChange = () => {
    setIsPublic(!isPublic);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // just for testing.
    console.log('Group Name:', groupName);
    console.log('Is Public:', isPublic);
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
