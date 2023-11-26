package ru.kograf.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kograf.backend.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
