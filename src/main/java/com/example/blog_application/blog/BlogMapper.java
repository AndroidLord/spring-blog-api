package com.example.blog_application.blog;

import com.example.blog_application.blog.dto.request.BlogRequestDTO;
import com.example.blog_application.blog.dto.response.BlogResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class BlogMapper {
    public Blog toBlog(BlogRequestDTO request) {
        return Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .archived(request.isArchived())
                .build();
    }

    public BlogResponseDTO toBlogResponseDTO(Blog blog) {
        return BlogResponseDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .owner(blog.getOwner().getUsername())
                .archived(blog.isArchived())
                .build();
    }
}
