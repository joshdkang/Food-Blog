import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import EditList from './EditList';
import AuthContext from '../../context/AuthContext';
import PageButtons from '../shared/PageButtons';

const Posts = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const {getCurrentUser} = useContext(AuthContext);
  const [page, setPage] = useState(0);
  const [isLastPage, setIsLastPage] = useState(true);

  const getPosts = async () => {
    const currentUser = getCurrentUser();
    if(!currentUser) return;
    const response = await axios.get(`http://localhost:8090/api/blog/getByAccount/${currentUser.username}/${page}`);
    setPosts(response.data.content);
    setIsLastPage(response.data.last);
  }

  useEffect(() => {
    (async function() {
      await getPosts();
    })();
  }, [page]);

  return (
    <div className="ui center aligned two column grid" >
      <div className="column">
        <EditList posts={posts} setPosts={setPosts} getPost={getPosts} loading={loading} setLoading={setLoading} />
        {posts.length > 1 ? <PageButtons page={page} setPage={setPage} isLastPage={isLastPage} /> : <div />}
      </div>
    </div>
  );
}

export default Posts;