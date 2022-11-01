import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import AuthContext from '../../context/AuthContext';
import AdminDownloadPost from '../admin/AdminDownloadPost';
import LogOutBtn from '../auth/LogOutBtn';

const Header = () => {
  const {getCurrentUser} = useContext(AuthContext);
  const home = '/';
  const user = getCurrentUser();
  
  return (
    <div className="ui secondary pointing menu" style={{ backgroundColor: '#D0EAFF'}}>
      <Link to={home} className="item">CG Blogs</Link>
      <Link to="/home"className="item">Home</Link>
      <Link to="/search"className="item">Search</Link>
      {
        !user && (
        <div className="right menu">
          <Link to="/login" className="item">Log In</Link>
          <Link to="/register" className="item">Register</Link>
        </div>
      )}
      {
        user && (
        <>
          <Link to="/likedPosts" className="item">Liked posts</Link>
          <Link to="/createPost" className="item">Create post</Link>
          <Link to="/editPosts" className="item">Edit posts</Link>
          {
            user?.roles?.includes("ROLE_ADMIN") && (
              <div className="left menu">
                <AdminDownloadPost />
              </div>
            )
          }
          <LogOutBtn />
        </>
      )}
      
    </div>
  );
}

export default Header;