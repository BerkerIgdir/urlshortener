package com.urlshortener.pojo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class User implements UserDetails, Serializable {

    @Id
    private ObjectId id;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    private List <URLInfo> urlInfoList;
    private boolean enabled = false;

    @Indexed(unique=true)
    private String username;
    private String password;
    @Indexed
    private String fullName;
    private Set <Role> authorities = new HashSet <> ();


    @Override
    public Collection <? extends GrantedAuthority> getAuthorities () {
        return authorities;
    }

    @Override
    public String getPassword () {
        return password;
    }

    @Override
    public String getUsername () {
        return username;
    }

    @Override
    public boolean isAccountNonExpired () {
        return isEnabled ();
    }

    @Override
    public boolean isAccountNonLocked () {
        return isEnabled ();
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return isEnabled ();
    }

    @Override
    public boolean isEnabled () {
        return enabled;
    }

    public ObjectId getId () {
        return id;
    }
}
