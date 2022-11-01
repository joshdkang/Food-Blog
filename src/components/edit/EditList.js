import React, { useContext } from 'react';
import { Form, Field } from "react-final-form";
import arrayMutators from 'final-form-arrays'
import { FieldArray } from "react-final-form-arrays";
import axios from 'axios';
import DeletePost from './DeletePost';
import AuthContext from '../../context/AuthContext';

const EditList = ({posts, setPosts, loading, setLoading}) => {
  const initialPosts = { posts };
  const {authHeader} = useContext(AuthContext);

  const updatePost = async (fields, index) => {
    try {
      const response = await axios.post('http://localhost:8090/api/blog/post/update', fields.posts[index], { headers: authHeader() });
      console.log(response);
    } catch(error) {
      console.log(error);
    }
  }

  const onSubmit = async () => {}

  const renderError = ({ error, touched }) => {
    if (touched && error) {
      return (
        <div className="ui small red message">
          <div className="content" align="left">{error}</div>
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

  const renderList = () => {
    return (
      <Form
      onSubmit={onSubmit}
      initialValues={initialPosts}
      mutators={{
        ...arrayMutators
      }}
      render={({ handleSubmit, dirty, values, pristine, invalid }) => {
        return (
          <div>
            <h2>
              <div className="content">
                Edit blog posts
              </div>
            </h2>
            <FieldArray name="posts">
              {({ fields }) => (
                <div>
                  {fields.map((name, index) => (
                    <div key={name}>
                      <form 
                        className="ui equal width form"
                        onSubmit={handleSubmit}
                        onBlur={() => { dirty && updatePost(values, index) }}
                        style={{marginBottom: "10px"}}
                      >
                        <div className="ui clearing segment">
                          <Field 
                            name={`${name}.title`} 
                            component={renderInput}
                            placeholder="Title"
                          />
                          <Field 
                            name={`${name}.description`} 
                            component={renderInput}
                            placeholder="Description"
                          />
                          <Field 
                            name={`${name}.imageUrl`} 
                            component={renderInput}
                            placeholder="Image Url"
                          />
                          <Field 
                            name={`${name}.content`} 
                            component="textarea"
                            rows={3}
                            placeholder="Text content"
                          />
                          <DeletePost loading={loading} setLoading={setLoading} fields={fields} index={index} />
                        </div>
                      </form>
                    </div>
                  ))}
                </div>
              )}
            </FieldArray>
          </div>
      )}}
    />
    );
  }

  return (
    <div>
      {renderList()}
    </div>
  );
}

export default EditList;