import React, { useContext, useEffect, useState }  from 'react';
import { useHistory } from 'react-router';
import AuthContext from '../../context/AuthContext';
import { Link } from 'react-router-dom';
import axios from 'axios';

const BlogList = ({blogPosts}) => { 
  const history = useHistory();

  const renderPostCards = () => {
    if(!blogPosts) return;

    return blogPosts.map(post => {
      return (
          <div className="ui column" key={post.id}>
            <div className="ui card">
              <a className="ui medium image" href={`/post/${post.id}`}><img src={post.imageUrl} /></a>
              <div className="left aligned content">
                <a className="left aligned header" href={`/post/${post.id}`}>{post.title}</a>
                <div className="left aligned meta">{post.author}</div>
                <div className="left aligned description">{post.description}</div>
              </div>
            </div>
          </div>
      );
    })
  }

  return (
    <div>
      {/* <div className = "ui center aligned two column grid"> */}
      <div className ="ui padded segment" style={{marginBottom: "20px"}}>
      <h1 className="ui header">Browse newest blog posts:</h1>
        <div className = "ui center aligned six column grid">
          {renderPostCards()}
        </div>
      </div>
      {/* </div> */}
    </div>
  );
}

export default BlogList