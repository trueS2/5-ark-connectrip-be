package connectripbe.connectrip_be.comment.repository;

import connectripbe.connectrip_be.comment.entity.AccompanyCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccompanyCommentRepository extends JpaRepository<AccompanyCommentEntity, Long> {
    // AccompanyPostEntity의 ID로 댓글 목록 조회
    Page<AccompanyCommentEntity> findByAccompanyPostEntity_Id(Long postId, Pageable pageable);
}
