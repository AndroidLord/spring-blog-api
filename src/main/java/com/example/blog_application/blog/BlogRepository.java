package com.example.blog_application.blog;

import com.example.blog_application.blog.dto.response.BlogResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query("""
            SELECT blog
            FROM Blog blog
            WHERE blog.archived = false
            AND blog.published = true
            """)
    Page<Blog> findAllDisplayableBlogs(Integer id, Pageable pageable);

    @Query("""
            SELECT blog
            FROM Blog blog
            WHERE blog.owner.id = :id 
            AND blog.archived = false
            """)
    Page<Blog> findAllBlogsByOwner(Integer id, Pageable pageable);

    @Query("""
            SELECT blog
            FROM Blog blog
            WHERE blog.owner.id = :id
            AND blog.archived = true
            """)
    Page<Blog> findAllArchivedBlogs(Integer id, Pageable pageable);
}
