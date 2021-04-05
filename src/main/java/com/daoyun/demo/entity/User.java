package com.daoyun.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  private long id;

  private String token;
//  public long getId() {
//    return id;
//  }
//
//  public void setId(long id) {
//    this.id = id;
//  }
//
//  public String getUsername() {
//    return username;
//  }
//
//  public void setUsername(String username) {
//    this.username = username;
//  }


  private String username;
  private String password;

  @Override
  public String toString() {
    return "user{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password=" + password +
            '}';
  }
}
