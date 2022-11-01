import React, { useContext, useEffect, useState }  from 'react';

const PageButtons = ({page, setPage, isLastPage, maxPages}) => {
  const goPreviousPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  }

  const jumpPreviousPage = () => {
    if (page > 4) {
      setPage(page - 5);
    } else {
      setPage(0);
    }
  }

  const goFirstPage = () => {
    setPage(0);
  }

  const goNextPage = () => {
    if (!isLastPage) {
      setPage(page + 1);
    }
  }

  const jumpNextPage = () => {
    if (page + 5 < maxPages - 1) {
      setPage(page + 5);
    } else {
      setPage(maxPages - 1);
    }
  }

  const goLastPage = () => {
    setPage(maxPages - 1);
  }

  return (
    <div className="ui small buttons" style={{float: "right", padding: "10px"}}>
      <button className={page > 0 ? "ui button" : "ui disabled button"} onClick={goFirstPage} style={{marginRight: "5px"}}>
        <i className="fast backward left icon"></i>
      </button>
      <button className={page > 0 ? "ui button" : "ui disabled button"} onClick={jumpPreviousPage} style={{marginRight: "5px"}}>
        <i className="angle double left icon"></i>
      </button>
      <button className={page > 0 ? "ui button" : "ui disabled button"} onClick={goPreviousPage} style={{marginRight: "5px"}}>
        <i className="angle left icon"></i>
      </button>
      <button className = "ui button" style={{marginRight: "5px", backgroundColor: 'transparent'}}>{page + 1}</button>
      <button className={!isLastPage ? "ui button" : "ui disabled button"} onClick={goNextPage} style={{marginRight: "5px"}}>
        <i className="angle right icon"></i>
      </button>
      <button className={!isLastPage ? "ui button" : "ui disabled button"} onClick={jumpNextPage} style={{marginRight: "5px"}}>
        <i className="angle double right icon"></i>
      </button>
      <button className={!isLastPage ? "ui button" : "ui disabled button"} onClick={goLastPage} style={{marginRight: "5px"}}>
        <i className="fast forward left icon"></i>
      </button>
    </div>
  )
}

export default PageButtons