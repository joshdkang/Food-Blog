import React, { useContext } from 'react';
import AuthContext from '../../context/AuthContext';
import axios from 'axios'

const DeletePost = ({loading, setLoading, fields, index}) => {
  const {authHeader} = useContext(AuthContext);

  const deletePost = async () => {
    setLoading(true);
    try {
      // await axios.delete('http://localhost:8090/api/blog/post/delete', { data: { postId: fields.value[index].id.toString() }}, { headers: authHeader() });
      await axios.delete('http://localhost:8090/api/blog/post/delete', { data: { postId: fields.value[index].id.toString() },  headers: authHeader() });
      fields.remove(index)
    } catch(error) {
      console.error(error);
    }
    setLoading(false);
  }

  return (
    <button 
      className="ui left floated icon button"
      type="button"
      onClick={() => { deletePost() }}
      disabled={loading}
    >
      <i className="trash alternate outline icon"></i>
    </button>
  );
}

export default DeletePost;