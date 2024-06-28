import React, {useState} from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';

const Feed: React.FC = () => {
  return (
    <div>
        <div className='container'>
            <form>
                <input type="text"></input>
                <input type="button" value="Unleash opinion"></input>
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
