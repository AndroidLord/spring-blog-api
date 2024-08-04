package com.example.blog_application.blog;

import com.example.blog_application.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// lombok annotations
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// Main annotations
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Blog {

    @Id @GeneratedValue private Integer id;
    private String title;
    private String content;
    private boolean archived;
    private boolean published;
    @ManyToOne @JsonIgnore private User owner;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = true)
    private LocalDateTime lastModifiedDate;

}
