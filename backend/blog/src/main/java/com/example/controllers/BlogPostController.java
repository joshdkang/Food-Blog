package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.example.models.User;
import com.example.payload.response.MessageResponse;
import com.example.models.BlogPost;
import com.example.repositories.BlogPostRepository;
import com.example.repositories.UserRepository;
import com.example.service.UserService;
import com.example.service.BlogPostService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.security.jwt.JwtUtils;
import com.example.security.jwt.AuthTokenFilter;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping("/api/blog")
public class BlogPostController {
  @Autowired
	private JwtUtils jwtUtils;

  @Autowired
	UserService userService;

  @Autowired
	UserRepository userRepository;
	
	@Autowired
	BlogPostService blogService;

  @Autowired
	BlogPostRepository blogPostRepository;

  @GetMapping("/search/{term}/{sortBy}/{page}")
  @ResponseBody
  public ResponseEntity<Page<BlogPost>> searchBlogPosts(@PathVariable(value="term") String term, @PathVariable(value="sortBy") String sortBy, @PathVariable(value="page") Integer page) {
    Pageable reqPage = PageRequest.of(page, 5, Sort.by(sortBy).descending());
    Page<BlogPost> blogPostPage = blogService.findPageByTerm(term, reqPage);

    try {
      return new ResponseEntity<>(blogPostPage, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(blogPostPage, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/getAllPosts/{sortBy}/{page}")
  @ResponseBody
  public ResponseEntity<Page<BlogPost>> getAllPosts(@PathVariable(value="sortBy") String sortBy, @PathVariable(value="page") Integer page) {
    Pageable reqPage = PageRequest.of(page, 2, Sort.by(sortBy).descending());
    Page<BlogPost> blogPage = blogService.findAll(reqPage);
    try {
      return new ResponseEntity<>(blogPage, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(blogPage, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/getByAccount/{username}/{page}")
  @ResponseBody
  public ResponseEntity<Page<BlogPost>> getAccountPosts(@PathVariable(value="username") String username, @PathVariable(value="page") Integer page) {
    Pageable reqPage = PageRequest.of(page, 5, Sort.by("date").descending());
    User account = userService.getAccountByUsername(username);
    Page<BlogPost> posts = blogService.getPostByUser(account, reqPage);
    try {
      return new ResponseEntity<>(posts, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(posts, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/getPost/{id}")
  @ResponseBody
  public ResponseEntity<BlogPost> getAccountPosts(@PathVariable(value="id") Long id) {
    BlogPost post = new BlogPost();
    try {
      post = blogService.getPostById(id);
      return new ResponseEntity<>(post, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }
  }
  
  @PostMapping("/post/create")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<BlogPost> createPost(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> map) {
    BlogPost post = new BlogPost();
    try{
      String jwt = header.substring(7, header.length());
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      User user = userService.getAccountById(Long.parseLong(map.get("accountId")));

      if (!username.equals(user.getUsername())) {
        throw new Exception("Invalid request");
      }

      post = new BlogPost(map.get("title"), map.get("description"), map.get("imageUrl"), map.get("content"), user);
      blogService.saveOrUpdate(post);
      return new ResponseEntity<>(post, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }
	}

  @PostMapping("/post/update")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<BlogPost> updatePost(@RequestHeader("Authorization") String header, @RequestBody BlogPost post) {
    BlogPost postTarget = blogService.getPostById(post.getId());
    try {
      String jwt = header.substring(7, header.length());
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      User user = postTarget.getUser();

      if (!username.equals(user.getUsername())) {
        throw new Exception("Invalid request");
      }

      postTarget.setTitle(post.getTitle());
      postTarget.setDescription(post.getDescription());
      postTarget.setImageUrl(post.getImageUrl());
      postTarget.setContent(post.getContent());
      blogService.saveOrUpdate(postTarget);
      return new ResponseEntity<>(postTarget, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(postTarget, HttpStatus.BAD_REQUEST);
    } 
	}

  @DeleteMapping("/post/delete")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<BlogPost> deletePost(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> map) {
    BlogPost post = blogService.getPostById(Long.parseLong(map.get("postId")));
    try {
      String jwt = header.substring(7, header.length());
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      User user = post.getUser();

      if (!username.equals(user.getUsername())) {
        throw new Exception("Invalid request");
      }

      blogService.delete(post);
      return new ResponseEntity<>(post, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }
  }
  
  @DeleteMapping("/admin/post/delete")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<BlogPost> adminDeletePost(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> map) {
    BlogPost post = blogService.getPostById(Long.parseLong(map.get("postId")));
    try {
      blogService.delete(post);
      return new ResponseEntity<>(post, HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }
  } 
  
  @PostMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> moderatorAccess(@RequestHeader("Authorization") String header) {
		return ResponseEntity.ok(new MessageResponse("mod access"));
	}

  @PostMapping("/post/likedPosts")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<List<BlogPost>> getLikedPosts(@RequestHeader("Authorization") String header, @RequestBody Map<String, Long> map) {
    String jwt = header.substring(7, header.length());
    String username = jwtUtils.getUserNameFromJwtToken(jwt);
    User user = userService.getAccountById(map.get("accountId"));

    try{
      if (!username.equals(user.getUsername())) {
        throw new Exception("Invalid request");
      }

      return new ResponseEntity<>(user.getLikedPosts(), HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(user.getLikedPosts(), HttpStatus.BAD_REQUEST);
    }
	}

  @PostMapping("/post/like")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<List<BlogPost>> addLikedPost(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> map) {
    List<BlogPost> temp = new ArrayList<BlogPost>();
    try{
      String jwt = header.substring(7, header.length());
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      User user = userService.getAccountById(Long.parseLong(map.get("accountId")));
      BlogPost blogPost = blogService.getPostById(Long.parseLong(map.get("blogId")));

      if (!username.equals(user.getUsername()) || user.getLikedPosts().contains(blogPost)) {
        throw new Exception("Invalid request");
      }

      blogService.like(blogPost, user);

      return new ResponseEntity<>(user.getLikedPosts(), HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(temp, HttpStatus.BAD_REQUEST);
    }
	}

  @PostMapping("/post/unlike")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@ResponseBody
	public ResponseEntity<List<BlogPost>> removeLikedPost(@RequestHeader("Authorization") String header, @RequestBody Map<String, String> map) {
    List<BlogPost> temp = new ArrayList<BlogPost>();
    try{
      String jwt = header.substring(7, header.length());
      String username = jwtUtils.getUserNameFromJwtToken(jwt);
      User user = userService.getAccountById(Long.parseLong(map.get("accountId")));
      BlogPost blogPost = blogService.getPostById(Long.parseLong(map.get("blogId")));

      if (!username.equals(user.getUsername()) || !user.getLikedPosts().contains(blogPost)) {
        throw new Exception("Invalid request");
      }

      blogService.unlike(blogPost, user);

      return new ResponseEntity<>(user.getLikedPosts(), HttpStatus.OK);
    } catch(Exception e) {
      return new ResponseEntity<>(temp, HttpStatus.BAD_REQUEST);
    }
	}

  @GetMapping("/post/download")
  @PreAuthorize("hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<Resource> getFile() {
    String filename = "blogPosts.xlsx";
    InputStreamResource file = new InputStreamResource(blogService.load());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }
}
