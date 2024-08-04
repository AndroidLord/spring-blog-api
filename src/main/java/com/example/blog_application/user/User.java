package com.example.blog_application.user;

import com.example.blog_application.blog.Blog;
import com.example.blog_application.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_user")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

    @Id @GeneratedValue private Integer id;
    private String firstName;
    private String lastName;
    @Column(unique = true) private String email;
    private String password;
    private boolean accountLocked;
    private boolean enabled;
    @CreatedDate @Column(nullable = false,updatable = false) private LocalDateTime createdDate;
    @LastModifiedDate @Column(insertable = false) private LocalDateTime lastModifiedDate;


    @ManyToMany(fetch = FetchType.EAGER) private List<Role> roles;

    @OneToMany(mappedBy = "owner") private List<Blog> blogs;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

}
