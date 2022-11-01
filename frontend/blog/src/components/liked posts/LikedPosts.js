import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import AuthContext from '../../context/AuthContext';
import PageButtons from '../shared/PageButtons';
import LikedTable from './LikedTable';

const LikedPosts = () => {
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const [maxPages, setMaxPages] = useState(0);
  const [isLastPage, setIsLastPage] = useState(true);
  const {getCurrentUser, authHeader} = useContext(AuthContext);

  const getPosts = async () => {
    const currentUser = getCurrentUser();
    if(!currentUser) return;
    const response = await axios.post(`http://localhost:8090/api/blog/post/likedPosts`, {accountId : currentUser.id}, { headers: authHeader() })
    if (response.data) localStorage.setItem("likedPosts", JSON.stringify(response.data));

    setPosts(response.data);
    if(response.data?.length > 0) {
      setMaxPages(Math.ceil(response.data.length / 10));
    }
  }

  useEffect(() => {
    (async function() {
      await getPosts();
    })();

    if(page === maxPages - 1) setIsLastPage(true);
    else setIsLastPage(false);
  }, [page, maxPages]);

  return (
    <div className="ui center aligned two column grid" >
      <div className="column">
        <h2>
          <div className="content">
            Liked posts
          </div>
        </h2>
        <LikedTable posts={posts} setPosts={setPosts} blogPosts={posts.slice(page * 10, (page * 10) + 10)} />
        {posts.length > 0 ? <PageButtons page={page} setPage={setPage} isLastPage={isLastPage} maxPages={maxPages} /> : <></>}
      </div>
    </div>
  );
}

export default LikedPosts