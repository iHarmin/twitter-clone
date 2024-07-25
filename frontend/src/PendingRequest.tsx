import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PendingRequest.css';
import Cookies from 'js-cookie';

interface Twitter2 {
    id: number;
    email: string;
    requestStatus: string;
}

const PendingRequests: React.FC = () => {
    const [pendingRequests, setPendingRequests] = useState<Twitter2[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        axios.get('http://localhost:8080/api/users/pendingRequests')
            .then(response => {
                setPendingRequests(response.data);
            })
            .catch(err => {
                setError('Error fetching pending requests');
                console.error(err);
            });
    }, []);

    const handleApprove = (id: number) => {
        const adminEmail = 'vraj.patel@dal.ca'; // Replace with actual admin email or context value

        axios.post(`http://localhost:8080/api/users/${id}/approve`, { adminEmail })
            .then(response => {
                alert(response.data);
                // Refresh the list of pending requests
                setPendingRequests(pendingRequests.filter(request => request.id !== id));
            })
            .catch(err => {
                setError('Error approving request');
                console.error(err);
            });
    };

    const handleReject = (id: number) => {
        const adminEmail = 'vraj.patel@dal.ca'; // Replace with actual admin email or context value

        axios.post(`http://localhost:8080/api/users/${id}/reject`, { adminEmail })
            .then(response => {
                alert(response.data);
                // Refresh the list of pending requests
                setPendingRequests(pendingRequests.filter(request => request.id !== id));
            })
            .catch(err => {
                setError('Error rejecting request');
                console.error(err);
            });
    };

    // Get the user role from the cookie
    const userRole = Cookies.get('role') || '';

    if (userRole !== 'Admin') {
        return <p>You do not have access to view this page.</p>;
    }

    return (
        <div>
            <h1>Pending Requests</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            {pendingRequests.length === 0 ? (
                <p>No pending requests found.</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {pendingRequests.map(request => (
                            <tr key={request.id}>
                                <td>{request.id}</td>
                                <td>{request.email}</td>
                                <td>{request.requestStatus}</td>
                                <td>
                                    <button onClick={() => handleApprove(request.id)}>Approve</button>
                                    <button onClick={() => handleReject(request.id)}>Reject</button>
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
