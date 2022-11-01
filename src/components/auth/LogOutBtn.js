import React, {useContext, useReducer} from 'react';
import { useHistory } from 'react-router';
import AuthContext from '../../context/AuthContext';

function LogOutBtn() {
  const history = useHistory();
  const {setLoggedIn, getCurrentUser} = useContext(AuthContext);
  const user = getCurrentUser();

  async function logOut() {
    localStorage.removeItem("user");
    localStorage.removeItem("likedPosts");
    setLoggedIn(false);
    history.push('/login');
  }

  return (
    <div className="right menu">
      <div className ="vertically fitted item">Hello, {user.username}</div>
      <button onClick={logOut} className="ui red button">
        Log out
      </button>
    </div>
  )
};

export default LogOutBtn;