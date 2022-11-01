import React, { createContext, useEffect, useState } from 'react';
import axios from 'axios';

const AuthContext = createContext();

const AuthContextProvider = (props) => {
  const [loggedIn, setLoggedIn] = useState(false);

  const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
  };

  const authHeader = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.accessToken) {
      return { Authorization: 'Bearer ' + user.accessToken };
    } else {
      return {};
    }
  }

  const userAccess = async () => {
    const user = getCurrentUser();
    if(!user || (!user.roles.includes('ROLE_USER') && !user.roles.includes('ROLE_MODERATOR') && !user.roles.includes('ROLE_ADMIN'))) return false;

    const response = await axios.post('http://localhost:8090/api/access/user', { data : {}}, { headers: authHeader() });
    return response;
  }

  const moderatorAccess = async () => {
    const user = getCurrentUser();
    if(!user || (!user.roles.includes('ROLE_MODERATOR') && !user.roles.includes('ROLE_ADMIN'))) return false;

    const response = await axios.post('http://localhost:8090/api/access/mod', { data : {}}, { headers: authHeader() });
    return response;
  }

  const adminAccess = async () => {
    const user = getCurrentUser();
    if(!user || !user.roles.includes('ROLE_ADMIN')) return false;

    const response = await axios.post('http://localhost:8090/api/access/admin', { data : {}}, { headers: authHeader() });
    return response;
  }

  useEffect(() => {
    const user = getCurrentUser();
    if(user) setLoggedIn(true);
  }, []);

  return <AuthContext.Provider value={{loggedIn, setLoggedIn, getCurrentUser, authHeader, userAccess, moderatorAccess, adminAccess}}>
    {props.children}
  </AuthContext.Provider>
};

export default AuthContext;
export {AuthContextProvider};

