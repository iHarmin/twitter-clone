import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

const PendingRequests: React.FC = () => {
  const [pendingRequests, setPendingRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch pending requests from the backend
    const fetchPendingRequests = async () => {
      try {
        const response = await axios.get('/api/requests/pending', { 
          headers: { 'Authorization': `Bearer ${Cookies.get('token')}` }
        });
        setPendingRequests(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching pending requests:', error);
        setError('Failed to load pending requests');
        setLoading(false);
      }
    };

    fetchPendingRequests();
  }, []);

  const handleApprove = async (requestId: string) => {
    try {
      await axios.post(`/api/requests/approve/${requestId}`, {}, { 
        headers: { 'Authorization': `Bearer ${Cookies.get('token')}` }
      });
      // Refresh the list after approval
      setPendingRequests(pendingRequests.filter(request => request.id !== requestId));
    } catch (error) {
      console.error('Error approving request:', error);
      alert('Failed to approve request');
    }
  };

  const handleReject = async (requestId: string) => {
    try {
      await axios.post(`/api/requests/reject/${requestId}`, {}, { 
        headers: { 'Authorization': `Bearer ${Cookies.get('token')}` }
      });
      // Refresh the list after rejection
      setPendingRequests(pendingRequests.filter(request => request.id !== requestId));
    } catch (error) {
      console.error('Error rejecting request:', error);
      alert('Failed to reject request');
    }
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div className="container">
      <h1>Pending Requests</h1>
      {pendingRequests.length === 0 ? (
        <p>No pending requests</p>
      ) : (
        <table className="table">
          <thead>
            <tr>
              <th>#</th>
              <th>User</th>
              <th>Request Type</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {pendingRequests.map((request, index) => (
              <tr key={request.id}>
                <td>{index + 1}</td>
                <td>{request.user}</td>
                <td>{request.type}</td>
                <td>
                  <button className="btn btn-success" onClick={() => handleApprove(request.id)}>Approve</button>
                  <button className="btn btn-danger" onClick={() => handleReject(request.id)}>Reject</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PendingRequests;
