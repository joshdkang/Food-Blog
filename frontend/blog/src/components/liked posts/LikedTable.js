import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';
import PageButtons from '../shared/PageButtons';

const LikedTable = ({posts, setPosts, blogPosts}) => {
  const {authHeader, getCurrentUser} = useContext(AuthContext);
  const currentUser = getCurrentUser();

  const unlikePost = async (post) => {
    const res = await axios.post(`http://localhost:8090/api/blog/post/unlike`, {accountId : currentUser.id, blogId : post.id}, { headers: authHeader() })
    localStorage.setItem("likedPosts", JSON.stringify(res.data));
    setPosts(res.data);
  }

  const renderPosts = () => {
    return blogPosts.map(post => {
      return (
        <tr key={post.id}>
          <td><a href={`/post/${post.id}`}><img className="ui small image" src={post.imageUrl}></img></a></td>
          <td><a href={`/post/${post.id}`}>{post.title}</a></td>
          <td>{post.author}</td>
          <td>{post.date}</td>
          <td>{post.description}</td>
          <td>
            <button 
              className="ui left floated icon button" 
              type="button"
              onClick={() => unlikePost(post)}
            >
              <i className="ui x icon"></i>
            </button>
          </td>
        </tr>
      );    
    });
  }

  const renderTable = () => {
    if (posts.length === 0) return (
      <h3 style={{marginTop : "150px"}}>
        You do not have any liked posts yet.
      </h3>
    ) 

    return (
      <div>
        <table className= "ui table">
          <thead>
            <tr>
              <th className="two wide"></th>
              <th className="two wide">Title</th>
              <th className="one wide">Author</th>
              <th className="one wide">Date</th>
              <th className="five wide">Description</th>
              <th className="one wide"></th>
            </tr>
          </thead>
          <tbody>
            {renderPosts()}
          </tbody>
        </table>
      </div>
    );
  }

  return (
    <div>
      {renderTable()}
    </div>
  );
}

export default LikedTable;