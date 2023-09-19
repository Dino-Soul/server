package dinosoul.snacktravelserver.domain.comment.repository;

import dinosoul.snacktravelserver.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySnackId(Long snackId);
}
