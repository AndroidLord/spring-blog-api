package com.example.blog_application.blog.dto.request;

import com.example.blog_application.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BlogRequestDTO {

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Content is required")
    @NotBlank(message = "Content is required")
    private String content;

    private boolean archived = false;
    private boolean published = false;
}
