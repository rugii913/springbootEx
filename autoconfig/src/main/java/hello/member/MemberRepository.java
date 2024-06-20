package hello.member;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    public final JdbcTemplate template;

    public MemberRepository(JdbcTemplate template) {
        this.template = template;
    }
    
    public void initTable() { // 예제 단순하게 만들기 위해 DB table 생성하는 메서드도 넣어뒀음
        template.execute("create table member(member_id varchar primary key, name varchar)");
    }
    
    public void save(Member member) {
        template.update("insert into member(member_id, name) values(?, ?)", member.getMemberId(), member.getName());
    }

    public Member find(String memberId) {
        return template.queryForObject(
                "select member_id, name from member where member_id=?",
                BeanPropertyRowMapper.newInstance(Member.class),
                memberId
        );
    }

    public List<Member> findAll() {
        return template.query(
                "select member_id, name from member",
                BeanPropertyRowMapper.newInstance(Member.class)
        );
    }
}
