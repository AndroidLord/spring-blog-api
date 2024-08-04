package com.example.blog_application.blog.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponseDTO {

    private Integer id;
    private String title;
    private String content;
    private boolean archived;
    private String owner;

}
