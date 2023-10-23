package board.repository;

import board.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    public void insert(Member memberVO) throws Exception;
}
