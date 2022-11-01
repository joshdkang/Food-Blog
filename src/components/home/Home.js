import React, { useState, useContext, useEffect }  from 'react';
import axios from 'axios';
import BlogList from './BlogList';
import PageButtons from '../shared/PageButtons';
import AuthContext from '../../context/AuthContext';

const Home = () => {
  const [blogPosts, setBlogPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [isLastPage, setIsLastPage] = useState(true);
  const [maxPages, setMaxPages] = useState(0);

  const getBlogPosts = async () => {
    const blogPostsResponse = await axios.get(`http://localhost:8090/api/blog/getAllPosts/date/${page}`);
    setBlogPosts(blogPostsResponse.data.content);
    setIsLastPage(blogPostsResponse.data.last);
    setMaxPages(blogPostsResponse.data.totalPages);
  }

  useEffect(() => {
    getBlogPosts();
  }, [page])

  return (
    <div>
      <BlogList blogPosts={blogPosts} setBlogPosts={setBlogPosts} page={page} setPage={setPage}/>
      <PageButtons page={page} setPage={setPage} isLastPage={isLastPage} maxPages={maxPages} />
    </div>
  );
}

export default Home