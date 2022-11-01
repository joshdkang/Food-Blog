import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router';
import { Form, Field } from 'react-final-form';
import AuthContext from '../../context/AuthContext';
import validator from 'validator';
import { FORM_ERROR } from 'final-form';

const CreatePost = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const {getCurrentUser, authHeader} = useContext(AuthContext);
  const history = useHistory();

  const createPost = async (values) => {
    const currentUser = getCurrentUser();
    if(!currentUser) return;

    try {
      values.accountId = currentUser.id;
      const response = await axios.post('http://localhost:8090/api/blog/post/create', values, { headers: authHeader() });
      history.push(`/post/${response.data.id}`)
    } catch (error) {
      console.log(error);
    }
    
  }

  const renderError = ({ error, submitError, touched }) => {
    if (touched && (error || submitError)) {
      return (
        <div className="ui small red message">
          <div className="content" align="left">{error || submitError}</div>
        </div>
      );
    }
  };
  
  const renderInput = ({ input, meta, placeholder }) => {
    const className = `field ${meta.error && meta.touched ? 'error' : ''}`;
  
    return (
      <div className={className}>
        <input {...input} autoComplete="off" placeholder={placeholder} />
        {renderError(meta)}
      </div>
    )
  };

  return (
    <div className="ui center aligned two column grid" >
      <div className="column">
        <h2>
          <div className="content">
            Create a blog post
          </div>
        </h2>
        <Form
          onSubmit={createPost}
          validate={values => {
            const errors = {};
            const regex = /^[a-z0-9._]+$/i;
  
            if (!values.title) errors.title = 'Please enter a valid title';
            if (!values.description) errors.description = 'Please enter a valid description';
            if (!values.content) errors.content = 'Please enter valid text content';
            return errors;
          }}
          render={({ handleSubmit, form, submitting, pristine, values, hasValidationErrors }) =>(
            <form 
              onSubmit={handleSubmit}
              className="ui equal width form"
            >
              <div className="ui segment">
                <Field 
                  name="title"
                  component={renderInput}
                  placeholder="Title"
                >
                </Field>
                <Field 
                  name="description"
                  component={renderInput} 
                  placeholder="Description"
                >
                </Field>
                <Field 
                  name="imageUrl" 
                  component={renderInput}
                  placeholder="Image URL"
                >
                </Field>
                <Field 
                  name="content"
                  component="textarea"
                  rows={10}
                  placeholder="Text content"
                >
                </Field>
              </div>
              <button 
                className="ui fluid button" 
                type="submit"
                disabled={submitting || pristine || hasValidationErrors}
              >
                Create Post
              </button> 
            </form>
          )}
        />
      </div>
    </div>
  );
}

export default CreatePost;