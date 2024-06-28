import React, {useState} from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';

const Feed: React.FC = () => {
    const [post, setPost] = useState('Hello');

    const handlePostChange = (event: any) => {
        setPost(event.target.value)
    }
    const handlePostSubmit = (event: any) => {
        alert("Posted" + post)
    }

    return (
        <div>
            <div className='container'>
                <form onSubmit={handlePostSubmit}>
                    <input type="text" value={post} onChange={handlePostChange}></input>
                    <input type="submit" value="Unleash opinion"></input>
                </form>
            </div>
            <br></br>
            <div className='container'>
                <h1>Lorem Ipsum</h1>
                <p><i>Username</i></p>
                <p>Contrerversial opinion</p>
            </div>
            <br></br>
            <div className='container'>
                <h1>Lorem Ipsum</h1>
                <p><i>Username</i></p>
                <p>Contrerversial opinion</p>
            </div>
        </div>
    );
}

export default Feed;
