import React, { useContext, useEffect, useState }  from 'react';
import { useHistory } from 'react-router';
import AuthContext from '../../context/AuthContext';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { Form, Field } from 'react-final-form';
import { FORM_ERROR } from 'final-form';

const SearchBar = ({blogPosts, setBlogPosts, searchTerm, setSearchTerm, sortBy, setSortBy, page, setIsLastPage}) => {
  

  const search = async (values) => {
    try {
      setSearchTerm(values.search);
    } catch(error) {
      if (error.response)
        return { [FORM_ERROR] : 'Unable to find results. Please use different search terms.' };
    }
  }

  const renderInput = ({ input, meta, placeholder }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
  
    return (
      <div className={className}>
        <input {...input} autoComplete="off" placeholder={placeholder} />
        {renderError(meta)}
      </div>
    )
  };

  const renderError = ({ error, touched }) => {
    if (touched && error) {
      return (
        <div className="ui small red message">
          <div className="content" align="left">{error}</div>
        </div>
      );
    }
  };

  useEffect(() => {
  }, [page])

  return (
    <div style={{marginBottom: "60px"}}>
      <h2 style={{float: "left"}}>
        Search blog posts:
      </h2>
      <Form
          onSubmit={search}
          validate={values => {
            const errors = {};

            if (!values.search) errors.search = 'Enter a search term';
            return errors;
          }}
          render={({ handleSubmit, submitError, hasValidationErrors, submitting, pristine, values }) =>(
            <form 
              onSubmit={handleSubmit}
              className="ui equal width form"
              style={{marginBottom: "60px"}}
            >
              <div className="ui search">
                <Field 
                  name="search"
                  component={renderInput} 
                  placeholder="Enter search term"
                >
                </Field>           
                {submitError && <div className="ui small red message">{submitError}</div>}
              </div>
              <h5 style={{float: "left", marginRight: "20px"}}>Sort by: </h5> 
              <div className="ui left floated basic buttons" style={{marginTop: "20px"}}>
                <div className={`ui button ${sortBy === "date" ? "active" : ""}`} value="date">Date</div>
                <div className={`ui button ${sortBy === "likes" ? "active" : ""}`} value="likes">Likes</div>
              </div>
              <button 
                className="ui right floated button" 
                style={{marginTop: "20px"}}
                type="submit"
                disabled={submitting || pristine || hasValidationErrors}
              >
                Search
              </button> 
            </form>
          )}
        />
    </div>
  );
}

export default SearchBar;