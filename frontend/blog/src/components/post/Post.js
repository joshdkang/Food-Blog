import React, { useState, useContext, useEffect }  from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';
import AdminDeletePost from '../admin/AdminDeletePost';
import Like from './Like';

const Post = () => {
  const {id} = useParams();
  const [blogPost, setBlogPost] = useState({});
  const [like, setLike] = useState(undefined);
  const [access, setAccess] = useState(false);
  const {loggedIn, moderatorAccess} = useContext(AuthContext);

  const getBlogPost = async () => {
    const res = await axios.get(`http://localhost:8090/api/blog/getPost/${id}`);    
    setBlogPost(res.data);
  }

  useEffect(async () => {
    let posts = JSON.parse(localStorage.getItem("likedPosts") || "[]");

    getBlogPost();
    const result = await moderatorAccess();
    setAccess(result);

    if(posts) {
      const query = posts.find(post => post.id.toString() === id);
      if(query) setLike(true);
      else setLike(undefined);
    };
  }, []);

  const renderPost = () => {
    if(!blogPost) return;

    return (
      <div className="ui segment">
        <h1 className = "ui header">
          {blogPost.title}
          <div className = "sub header">by: {blogPost.author}</div>
        </h1>
        <div className="ui large image"><img src={blogPost.imageUrl} style={{float: "center", marginBottom: "20px"}} /></div>
        <div style={{float: "center"}}>{blogPost.content}</div>
      </div>
    );
  }

  return (
    <div className = "ui center aligned two column grid">
      <div className="column">
        {renderPost()}
        { access.status === 200 ?<AdminDeletePost post={blogPost} />: <div></div> }
        {loggedIn ? <Like like={like} setLike={setLike} blogPost={blogPost} setBlogPost={setBlogPost} /> : <></>}
      </div>  
    </div>
  );
}

export default Post