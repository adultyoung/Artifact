package com.artifact.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "usr")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "username"})

public class User implements UserDetails {

    @Id
    @JsonView(Views.IdName.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String username;
    private String password;
    @JsonView(Views.IdName.class)
    private String picture;
    private String email;
    @JsonView(Views.FullProfile.class)
    private String locale;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;


    @ElementCollection(fetch = FetchType.EAGER)
    @JsonView(Views.FullProfile.class)
    @Column
    private List<String> roles = new ArrayList<>(Collections.singleton("USER"));
    private String activationCode;
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscription> subscriptions = new HashSet<>();
    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<UserSubscription> subscribers = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
