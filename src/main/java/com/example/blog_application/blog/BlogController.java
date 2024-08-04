package com.example.blog_application.blog;

import com.example.blog_application.blog.dto.request.BlogRequestDTO;
import com.example.blog_application.blog.dto.response.BlogResponseDTO;
import com.example.blog_application.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("blog")
@RequiredArgsConstructor
@Tag(name = "Blog", description = "Blog API")
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<Integer> createBlog(
            @Valid @RequestBody BlogRequestDTO request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(blogService.save(request, connectedUser));
    }

    @GetMapping("{blog-id}")
    public ResponseEntity<BlogResponseDTO> getBlog(
            @PathVariable("blog-id") Integer blog_id
    ) {
        try {
            BlogResponseDTO blog = blogService.findBlogById(blog_id);
            return ResponseEntity.ok(blog);
        } catch (RuntimeException e) {
            // Return a 404 Not Found status code with a user-friendly message
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<PageResponse<BlogResponseDTO>> getAllBlog(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(blogService.getAllBlogs(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BlogResponseDTO>> findAllBlogByOwnwer(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(blogService.findAllBlogsByOwner(page, size, connectedUser));
    }

    @GetMapping("/archived")
    public ResponseEntity<PageResponse<BlogResponseDTO>> findAllArchivedBlog(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(blogService.findAllArchivedBlog(page, size, connectedUser));
    }

    @PutMapping("{blog-id}")
    public ResponseEntity<Integer> updateBlog(
            @PathVariable("blog-id") Integer blog_id,
            @Valid @RequestBody BlogRequestDTO request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(blogService.update(blog_id, request, connectedUser));
    }

    @DeleteMapping("{blog-id}")
    public ResponseEntity<Integer> deleteBlog(
            @PathVariable("blog-id") Integer blog_id,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(blogService.delete(blog_id, connectedUser));
    }

}
