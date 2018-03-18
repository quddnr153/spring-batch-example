package bw.batch.example.blog.repository;

import bw.batch.example.blog.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Byungwook Lee on 2018-03-18
 * quddnr153@gmail.com
 * https://github.com/quddnr153
 */
public interface PostRepository extends JpaRepository<Post, Integer> {
	@Modifying
	@Query("UPDATE Post SET content = ?1 WHERE id = ?2")
	void updatePostContent(String content, long id);
}
