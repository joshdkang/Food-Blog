import React, { useContext, useEffect, useState } from 'react';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import Header from './components/layout/Header';
import Register from './components/auth/Register';
import Login from './components/auth/Login';
import Edit from './components/edit/Edit';
import AuthContext from './context/AuthContext';
import Home from './components/home/Home';
import Search from './components/search/Search';
import Post from './components/post/Post';
import CreatePost from './components/create/CreatePost';
import LikedPosts from './components/liked posts/LikedPosts';

const Router = () => {
  const [status, setStatus] = useState('loading');
  const {loggedIn} = useContext(AuthContext);

  useEffect(() => {
    (async function() {
      try {
        setStatus(loggedIn ? 'loggedIn' : 'loggedOut');
      } catch {
        setStatus('loggedOut');
      }
    })();
  });

  if (status === 'loading') {
    return (
      <div className="ui segment">
        <div className="ui active inverted dimmer">
          <div className="ui text loader"  style={{ marginTop: '80px' }}>Loading</div>
        </div>
      </div>
    )
  }

  return <BrowserRouter>
    <Switch>
      <Route exact path='/'>
        <>
          <Header />
          <Home /> 
        </> 
      </Route>
      <Route exact path='/home'>
        <>
          <Header />
          <Home /> 
        </> 
      </Route>
      <Route exact path='/search'>
        <>
          <Header />
          <Search /> 
        </> 
      </Route>
      <Route exact path='/post/:id'>
        <>
          <Header />
          <Post /> 
        </> 
      </Route>
      <Route exact path='/register'>
        {loggedIn ? <Redirect to='/home' /> : 
          <>
            <Header />
            <Register /> 
          </> 
        }
      </Route> 
      <Route exact path='/login'>
        {loggedIn ? <Redirect to='/home' /> : 
          <>
            <Header />
            <Login /> 
          </> 
        }
      </Route> 
      <Route exact path='/likedPosts'>
        {!loggedIn ? <Redirect to='/login' /> : 
          <>
            <Header />
            <LikedPosts />
          </> 
        }
      </Route>
      <Route exact path='/createPost'>
        {!loggedIn ? <Redirect to='/login' /> : 
          <>
            <Header />
            <CreatePost />
          </> 
        }
      </Route> 
      <Route exact path='/editPosts'>
        {!loggedIn ? <Redirect to='/login' /> : 
          <>
            <Header />
            <Edit />
          </> 
        }
      </Route> 
    </Switch>
  </BrowserRouter>; 
}

export default Router;