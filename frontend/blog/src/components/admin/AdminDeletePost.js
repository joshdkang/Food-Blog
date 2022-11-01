import React, { useContext } from 'react';
import AuthContext from '../../context/AuthContext';
import { useHistory } from 'react-router';
import axios from 'axios'

const AdminDeletePost = ({post}) => {
  const {authHeader} = useContext(AuthContext);
  const history = useHistory();

  const deletePost = async () => {
    try {
      console.log(post);
      await axios.delete('http://localhost:8090/api/blog/admin/post/delete', { data: { postId: post.id },  headers: authHeader() });
      history.push('/home');
    } catch(error) {
      console.error(error);
    }
  }

  return (
    <button 
      className="ui right floated negative ui button"
      type="button"
      onClick={deletePost}
    >
      Admin Delete Post
    </button>
  );
}

export default AdminDeletePost;