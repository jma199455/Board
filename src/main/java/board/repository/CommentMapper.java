package board.repository;

import board.vo.comment.CommentRequest;
import board.vo.comment.CommentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 댓글 저장
     * @param params - 댓글 정보
     */
    public void save(CommentRequest params);

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    public CommentResponse findById(Long id);

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     */
    public void update(CommentRequest params);

    /**
     * 댓글 삭제
     * @param id - PK
     */
    public void deleteById(Long id);

    /**
     * 댓글 리스트 조회
     * @param postId - 게시글 번호 (FK)
     * @return 댓글 리스트
     */
    public List<CommentResponse> findAll(Long postId);

    /**
     * 댓글 수 카운팅
     * @param postId - 게시글 번호 (FK)
     * @return 댓글 수
     */
    public int count(Long postId);



}
