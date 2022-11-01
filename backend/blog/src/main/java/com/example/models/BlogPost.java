package com.example.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class BlogPost {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String author;
  private String description;
  private String imageUrl;

  @Column(columnDefinition = "TEXT")
  private String content;
  private int likes;

	@CreationTimestamp
	@DateTimeFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private LocalDateTime date;

  @ManyToOne(cascade=CascadeType.MERGE)
  @JoinColumn(foreignKey = @ForeignKey(name = "user_id"),name = "user_id")
  @JsonIgnore
  private User user;

  @ManyToMany(mappedBy = "likedPosts")
  @JsonIgnore
  private List <User> likedUsers;

  public BlogPost() {
    this.likedUsers = new ArrayList<User>();
  }

  public BlogPost(String title, String description, String imageUrl, String content, User user) {
    this.title = title;
    this.author = user.getUsername();
    this.description = description;
    this.imageUrl = imageUrl;
    this.content = content;
    this.likes = 0;
    this.user = user;
    this.likedUsers = new ArrayList<User>();
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getLikes() {
    return this.likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public LocalDateTime getDate() {
    return this.date;
  }

  public User getUser() {
    return this.user;
  }

  public void addLikedUser(User user) {
    this.likedUsers.add(user);
  }

  public void removeLikedPost(User user) {
    this.likedUsers.remove(user);
  }

  public List<User> getLikedUsers() {
    return this.likedUsers;
  }
}
