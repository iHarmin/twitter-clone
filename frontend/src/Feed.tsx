import React, {useState} from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';

const Feed: React.FC = () => {
    const [post, setPost] = useState('Hello');
    const [postData, setPostData] = useState([
        {username: "User 1", body: "Contrerversial opinion"},
        {username: "User 2", body: "Contrerversial opinion"}
    ])

    const handlePostChange = (event: any) => {
        setPost(event.target.value)
    }

    const handlePostSubmit = (event: any) => {
        event.preventDefault();
        let newPost = {userID: {id: 30}, body: post}
        fetch("http://localhost:8080/api/post/createPost", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newPost)
        }).then(data => console.log(data));
        let showPost = {username: "USER", body: post}
        setPostData([...postData, showPost])
    }


    // CITATION: https://stackoverflow.com/questions/66777285/how-to-append-react-component-with-appendchild-method
    const postDisplay = postData.map((post, v) => {
        return (
            <div>
                <div className='container'>
                    <p><i>{post.username}</i></p>
                    <p>{post.body}</p>
                </div>
            </div>
        )
    })
    return (
        <div>
            <div className='container'>
                <form onSubmit={handlePostSubmit}>
                    <input type="text" value={post} onChange={handlePostChange}></input>
                    <input type="submit" value="Unleash opinion"></input>
                </form>
            </div>
            <br></br>
            {postDisplay}
        </div>
    );
}

export default Feed;
