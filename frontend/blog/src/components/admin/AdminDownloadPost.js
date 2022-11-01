import React, { useContext } from 'react';
import AuthContext from '../../context/AuthContext';
import { useHistory } from 'react-router';
import axios from 'axios'
const AdminDownloadPost = ({post}) => {
  const user = JSON.parse(localStorage.getItem('user'));

  const downloadPost = async () => {
    axios.get('http://localhost:8090/api/blog/post/download', {
      headers: {"Authorization" : "Bearer " + user.accessToken},
      responseType: 'blob', 
    }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', `${Date.now()}.xlsx`);
        document.body.appendChild(link);
        link.click();
    });
  }

  return (
    <a 
      className="ui button"
      type="button"
      onClick={downloadPost}
    >
      Download Posts
    </a>
  );
}

export default AdminDownloadPost;