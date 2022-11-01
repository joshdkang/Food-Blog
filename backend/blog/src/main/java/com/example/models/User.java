package com.example.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String profileImage;
  private String description;
  private String email;
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  
  @OneToMany(cascade=CascadeType.ALL, mappedBy="user", orphanRemoval=true)
  private List <BlogPost> blogPosts;

  @ManyToMany
  @JoinTable(
    name = "post_like", 
    joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "post_id"))
  private List <BlogPost> likedPosts;

  public User() {
    this.blogPosts = new ArrayList<BlogPost>();
    this.likedPosts = new ArrayList<BlogPost>();
  }

  public User(String username, String email, String password) {
      super();
      this.username = username;
      this.email = email;
      this.password = password;
      this.blogPosts = new ArrayList<BlogPost>();
      this.likedPosts = new ArrayList<BlogPost>();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String image) {
    this.profileImage = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void addPost(BlogPost post) {
    this.blogPosts.add(post);
  }

  public void removePost(BlogPost post) {
    this.blogPosts.remove(post);
  }

  public List<BlogPost> getBlogPosts() {
    return this.blogPosts;
  }

  public void addLikedPost(BlogPost post) {
    this.likedPosts.add(post);
  }

  public void removeLikedPostPost(BlogPost post) {
    this.likedPosts.remove(post);
  }

  public List<BlogPost> getLikedPosts() {
    return this.likedPosts;
  }

  public Long getId() {
      return id;
  }

  public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}