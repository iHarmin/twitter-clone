import React, {useEffect, useState} from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';

const Feed: React.FC = () => {
    const [post, setPost] = useState('Hello');
    const [postData, setPostData] = useState([
        {userID: {userName: "USER"}, body: "Contrerversial opinion"},
        {userID: {userName: "USER"}, body: "Contrerversial opinion"}
    ])

    const handlePostChange = (event: any) => {
        setPost(event.target.value)
    }

    useEffect(() => {
        fetch("http://localhost:8080/api/post/getPosts", {
            method: 'GET'
        }).then(data => data.json().then(data => {
            setPostData(data)
            console.log(data)
        }))
    }, [])

    const handlePostSubmit = (event: any) => {
        event.preventDefault();
        let newPost = {userID: {id: Cookies.get('userId')}, body: post}
        console.log(newPost);
        fetch("http://localhost:8080/api/post/createPost", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newPost)
        }).then(data => console.log(data));
        let showPost = {userID: {userName: Cookies.get('username')}, body: post}
        setPostData([...postData, showPost])
    }


    // CITATION: https://stackoverflow.com/questions/66777285/how-to-append-react-component-with-appendchild-method
    const postDisplay = postData.map((post, v) => {
        return (
            <div>
                <div className='container'>
                    <p><i>{post.userID.userName}</i></p>
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
