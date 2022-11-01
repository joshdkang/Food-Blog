import React, { useContext, useEffect, useState }  from 'react';
import { useHistory } from 'react-router';
import AuthContext from '../../context/AuthContext';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { Form, Field } from 'react-final-form';
import { FORM_ERROR } from 'final-form';

const SearchTable = ({blogPosts, searchTerm}) => {
  const renderTable = () => {
    if(!blogPosts || !searchTerm) return;

    if(blogPosts.length === 0 && searchTerm !== undefined) {
      return (
        <h2 style={{marginTop : "100px"}}>
          No search results. Please try another search term.
        </h2>
      );
    }

    return (
    <div>
      <table className= "ui table">
        <thead>
          <tr>
            <th className="two wide"></th>
            <th className="two wide">Title</th>
            <th className="one wide">Author</th>
            <th className="one wide">Date</th>
            <th className="five wide">Description</th>
            <th className="one wide">Likes</th>
          </tr>
        </thead>
        <tbody>
          {renderPosts()}
        </tbody>
      </table>
    </div>
    )
  }

  const renderPosts = () => {
    return blogPosts.map(post => {
      return (
        <tr key={post.id}>
          <td><a href={`/post/${post.id}`}><img className="ui small image" src={post.imageUrl}></img></a></td>
          <td><a href={`/post/${post.id}`}>{post.title}</a></td>
          <td>{post.author}</td>
          <td>{post.date}</td>
          <td>{post.description}</td>
          <td>{post.likes}</td>
        </tr>
      );    
    });
  }

  return (
    <div>
      {renderTable()}
    </div>
  );
}

export default SearchTable;