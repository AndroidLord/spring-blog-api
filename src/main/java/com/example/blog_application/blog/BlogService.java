package com.example.blog_application.blog;

import com.example.blog_application.blog.dto.request.BlogRequestDTO;
import com.example.blog_application.blog.dto.response.BlogResponseDTO;
import com.example.blog_application.common.PageResponse;
import com.example.blog_application.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    public Integer save(BlogRequestDTO request, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        Blog blog = blogMapper.toBlog(request);
        blog.setOwner(user);

        return blogRepository.save(blog).getId();
    }

    public BlogResponseDTO findBlogById(Integer blogId) {
        return blogRepository.findById(blogId)
                .map(blogMapper::toBlogResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No blog found with ID::" + blogId));
    }


    public PageResponse<BlogResponseDTO> getAllBlogs(Integer page, Integer size, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        System.out.println("user: " + user.getFullName());

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Blog> blogs = blogRepository.findAllDisplayableBlogs(user.getId(), pageable);

        List<BlogResponseDTO> blogResponses = blogs.stream()
                .map(blogMapper::toBlogResponseDTO)
                .toList();

        System.out.println("blogResponses: " + blogResponses);

        return new PageResponse<>(
                blogResponses,
                blogs.getNumber(),
                blogs.getSize(),
                blogs.getTotalElements(),
                blogs.getTotalPages(),
                blogs.isFirst(),
                blogs.isLast()
        );
    }

    public PageResponse<BlogResponseDTO> findAllBlogsByOwner(Integer page, Integer size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Blog> blogs = blogRepository.findAllBlogsByOwner(user.getId(), pageable);
        List<BlogResponseDTO> blogResponses = blogs.stream()
                .map(blogMapper::toBlogResponseDTO)
                .toList();
        return new PageResponse<>(
                blogResponses,
                blogs.getNumber(),
                blogs.getSize(),
                blogs.getTotalElements(),
                blogs.getTotalPages(),
                blogs.isFirst(),
                blogs.isLast()
        );
    }

    public PageResponse<BlogResponseDTO> findAllArchivedBlog(Integer page, Integer size, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Blog> blogs = blogRepository.findAllArchivedBlogs(user.getId(), pageable);
        List<BlogResponseDTO> blogResponses = blogs.stream()
                .map(blogMapper::toBlogResponseDTO)
                .toList();
        return new PageResponse<>(
                blogResponses,
                blogs.getNumber(),
                blogs.getSize(),
                blogs.getTotalElements(),
                blogs.getTotalPages(),
                blogs.isFirst(),
                blogs.isLast()
        );
    }

    public Integer update(Integer blogId, BlogRequestDTO request, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new EntityNotFoundException("No blog found with ID::" + blogId));

        if (!blog.getOwner().getId().equals(user.getId())) {
            throw new EntityNotFoundException("No blog found with ID::" + blogId);
        }
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setArchived(request.isArchived());
        return blogRepository.save(blog).getId();
    }

    public Integer delete(Integer blogId, Authentication connectedUser) {

            User user = (User) connectedUser.getPrincipal();

            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new EntityNotFoundException("No blog found with ID::" + blogId));

            if (!blog.getOwner().getId().equals(user.getId())) {
                throw new EntityNotFoundException("No blog found with ID::" + blogId);
            }
            blogRepository.delete(blog);
            return blogId;
    }
}
