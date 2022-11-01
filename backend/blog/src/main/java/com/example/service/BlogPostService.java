package com.example.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.example.models.User;
import com.example.helper.ExcelHelper;
import com.example.models.BlogPost;
import com.example.repositories.UserRepository;
import com.example.repositories.BlogPostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class BlogPostService {
  @Autowired
	UserRepository userRepository;

  @Autowired
  BlogPostRepository blogPostRepository;

  public List<BlogPost> findAll(){
		return (List<BlogPost>) blogPostRepository.findAll();
	}

  public Page<BlogPost> findAll(Pageable page){
		return blogPostRepository.findAll(page);
	}

  public Page<BlogPost> findPageByTerm(String term, Pageable page){
		return (Page<BlogPost>) blogPostRepository.findAllByTitleContainingOrDescriptionContainingOrContentContainingIgnoreCase(term, term, term, page);
	}

	public BlogPost getPostById(long id) {
		return blogPostRepository.findById(id).get();
	}

  public List<BlogPost> getPostByUser(User user) {
		return blogPostRepository.findAllByUser(user);
	}

  public Page<BlogPost> getPostByUser(User user, Pageable page) {
		return blogPostRepository.findAllByUser(user, page);
	}
	
	public BlogPost saveOrUpdate(BlogPost post) {
		blogPostRepository.save(post);
		return post;
	}

  public BlogPost like(BlogPost blogPost, User user) {
    user.addLikedPost(blogPost);
    blogPost.setLikes(blogPost.getLikes() + 1);
    blogPost.addLikedUser(user);
    userRepository.save(user);
    blogPostRepository.save(blogPost);

    return blogPost;
  }

  public BlogPost unlike(BlogPost blogPost, User user) {
    user.removeLikedPostPost(blogPost);
    blogPost.setLikes(blogPost.getLikes() - 1);
    blogPost.removeLikedPost(user);
    userRepository.save(user);
    blogPostRepository.save(blogPost);

    return blogPost;
  }
	
	public void delete(BlogPost post) {
		blogPostRepository.delete(post);
	}

  public ByteArrayInputStream load() {
    List<BlogPost> posts = blogPostRepository.findAll();
    ByteArrayInputStream in = ExcelHelper.blogPostsToExcel(posts);
    return in;
  }
}
