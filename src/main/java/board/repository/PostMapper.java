package board.repository;

import board.vo.common.dto.SearchDto;
import board.vo.post.PostRequest;
import board.vo.post.PostResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     */
    public void save(PostRequest params);

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostResponse findById(Long id);

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    public void update(PostRequest params);

    /**
     * 게시글 삭제
     * @param id - PK
     */
    public void deleteById(Long id);

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    public List<PostResponse> findAll(SearchDto params);

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    public int count(SearchDto dto);


}
