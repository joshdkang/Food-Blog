import React, { useState, useContext, useEffect }  from 'react';
import axios from 'axios';
import SearchBar from './SearchBar';
import SearchTable from './SearchTable';
import PageButtons from '../shared/PageButtons';

const Search = () => {
  const [blogPosts, setBlogPosts] = useState([]);
  const [searchTerm, setSearchTerm] = useState(undefined);
  const [sortBy, setSortBy] = useState("date");
  const [page, setPage] = useState(0);
  const [isLastPage, setIsLastPage] = useState(true);
  const [maxPages, setMaxPages] = useState(0);

  const searchPosts = async () => {
    const response = await axios.get(`http://localhost:8090/api/blog/search/${searchTerm}/${sortBy}/${page}`);
    setBlogPosts(response.data.content);
    setIsLastPage(response.data.last);
    setMaxPages(response.data.totalPages);
  }

  useEffect(() => {
    if(!searchTerm) return;
    searchPosts();
  }, [searchTerm, page])

  return (
    <div className = "ui center aligned two column grid">
      <div className="column">
        <SearchBar blogPosts={blogPosts} setBlogPosts={setBlogPosts} searchTerm={searchTerm} setSearchTerm={setSearchTerm} sortBy={sortBy} setSortBy={setSortBy} page={page} setIsLastPage={setIsLastPage} />
        <SearchTable blogPosts={blogPosts} setBlogPosts={setBlogPosts} searchTerm={searchTerm} />
        {maxPages > 1 ? <PageButtons page={page} setPage={setPage} isLastPage={isLastPage} maxPages={maxPages} /> : <div />}
      </div>
    </div>
  );
}

export default Search