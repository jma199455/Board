package board.service;

import board.repository.MemberMapper;
import board.vo.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    final MemberMapper memberMapper;

    public void insert(Member member) throws Exception{
         memberMapper.insert(member);
    }


}
