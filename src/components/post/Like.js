import React, { useState, useContext, useEffect }  from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';
import AdminDeletePost from '../admin/AdminDeletePost';

const LikePost = ({like, setLike, blogPost, setBlogPost}) => {
  const {authHeader, getCurrentUser} = useContext(AuthContext);
  let posts = JSON.parse(localStorage.getItem("likedPosts") || "[]");
  const currentUser = getCurrentUser();

  const likeClick = async () => { 
    let res;
    let newPost = Object.assign({}, blogPost);

    if (like) {
      res = await axios.post(`http://localhost:8090/api/blog/post/unlike`, {accountId : currentUser.id, blogId : blogPost.id}, { headers: authHeader() })
      setLike(undefined);
      posts = posts.filter(post => post.id === blogPost);
      newPost.likes--;
    }
    if (!like) {
      res = await axios.post(`http://localhost:8090/api/blog/post/like`, {accountId : currentUser.id, blogId : blogPost.id}, { headers: authHeader() })
      setLike(true);
      posts.push(blogPost);
      newPost.likes++;
    }
    localStorage.setItem("likedPosts", JSON.stringify(res.data));
    setBlogPost(newPost);
  }

  return (
    <button 
      className="ui left floated icon button" 
      type="button"
      onClick={likeClick}
    >
      <i className={like ? "heart icon" : "heart outline icon"}></i> {blogPost.likes} {blogPost.likes === 1 ? "like" : "likes"} 
    </button>
  );
}

export default LikePost